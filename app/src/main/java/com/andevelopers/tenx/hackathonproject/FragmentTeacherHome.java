package com.andevelopers.tenx.hackathonproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentTeacherHome extends Fragment {
    TextView tvName, tvLogout, tvUpdate, tvMyPost;
    DocumentReference docRef;
    FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_teacher, container, false);
        final String nameKey = getActivity().getIntent().getStringExtra(ActivityHome.KEY_NAME);

        //setting up the docref --> this is the main document to edit
        docRef = mDb.collection("teachers").document(nameKey);


        //setting up the tvUpdate
        tvUpdate = v.findViewById(R.id.tv_add_post);
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction ftrans = fragmentManager.beginTransaction();
                FragmentPostUpdate frag =  new FragmentPostUpdate();
                Bundle args = new Bundle();
                args.putString("key", nameKey);
                frag.setArguments(args);
                ftrans.replace(R.id.container_home, frag);
                ftrans.addToBackStack(ActivityHome.TAG_BACKSTACK);
                ftrans.commit();
            }
        });


        //setting uo the name textview
        tvName = v.findViewById(R.id.tv_name_teacher);
        tvName.setText(nameKey);

        //setting up the my post button
        tvMyPost = v.findViewById(R.id.tv_my_posts);
        tvMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction ftrans = fragmentManager.beginTransaction();
                Fragment frag =  new FragmentMyPosts();
                ftrans.replace(R.id.container_home, frag);
                ftrans.addToBackStack(ActivityHome.TAG_BACKSTACK);
                ftrans.commit();
            }
        });

        //setting up the logout button
        tvLogout = v.findViewById(R.id.tv_logout);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });





        return v;
    }

    public void goBack(){
        Intent i = new Intent(getActivity(), ActivityLogin.class);
        startActivity(i);
    }
}
