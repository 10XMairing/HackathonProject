package com.andevelopers.tenx.hackathonproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andevelopers.tenx.hackathonproject.Adapters.CustomForumAdapter;
import com.andevelopers.tenx.hackathonproject.Utils.Feed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FragmentTeachersForum extends Fragment{

    RecyclerView recyclerView;
    FloatingActionButton fabAddPost;
    ImageView ivTeachersMenu;
    CustomForumAdapter adapter;
    FirebaseFirestore db =FirebaseFirestore.getInstance();

    //temp
    CollectionReference forum = db.collection("teachers").document(ActivityHome.userID).collection("forum");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discussion_forum, container, false);

        recyclerView = v.findViewById(R.id.recycler_diss_forum);
        adapter = new CustomForumAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        forum.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                for(DocumentSnapshot snap : list){
                    String name = snap.getString("name");
                    String text = snap.getString("text");
                    adapter.addFeedAndNotify(new Feed(name, text));

                }


            }
        });



        //fab
        fabAddPost = v.findViewById(R.id.fab_add_post);
        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Add post button", Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(getActivity(), ActivityForumAddPost.class);
                startActivity(intent);*/
                createDialog(getActivity());


            }
        });




        ivTeachersMenu = v.findViewById(R.id.iv_menu_teachers);

        ivTeachersMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container_home, new FragmentMenu());
                ft.addToBackStack("MENU");
                ft.commit();

            }
        });





        return v;
    }


    public void createDialog(final Context mCtx){
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        View v = getLayoutInflater().inflate(R.layout.fragment_post_update, null, false);
        builder.setView(v);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        dialog.show();
        final EditText etText;
        TextView tvHeader;
        Button btnPost;
        etText = v.findViewById(R.id.et_update_text);
        tvHeader = v.findViewById(R.id.tv_header_post);
        btnPost = v.findViewById(R.id.btn_post_update);

        tvHeader.setText("Forum Post");
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = etText.getText().toString();

                if(!text.equals("")){
                    Map<String, Object> data = new HashMap<>();
                    data.put("text", text);
                    data.put("time", System.currentTimeMillis());
                    data.put("name", ActivityHome.userID);
                    CollectionReference colRef = db.collection("teachers").document("adarsh").collection("forum");
                    colRef.document().set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(mCtx, "Done", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(mCtx, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });





    }


}
