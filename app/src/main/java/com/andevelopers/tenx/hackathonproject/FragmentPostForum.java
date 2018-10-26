package com.andevelopers.tenx.hackathonproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FragmentPostForum extends Fragment{

    EditText etText;
    TextView tvHeader;
    Button btnPost;
    FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    CollectionReference colRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_update, container, false);
        tvHeader = v.findViewById(R.id.tv_header_post);
        tvHeader.setText("FORUM POST");

        //set up etText
        etText = v.findViewById(R.id.et_update_text);
        //test

        colRef = mDb.collection("teachers").document("adarsh").collection("forum");

        btnPost = v.findViewById(R.id.btn_post_update);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etText.getText().toString();

                if(!text.equals("")){
                    Map<String, Object> data = new HashMap<>();
                    data.put("text", text);
                    data.put("time", System.currentTimeMillis());
                    data.put("name", ActivityHome.userID);
                    colRef.document().set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                FragmentManager fragmentManager = getFragmentManager();
                                assert fragmentManager != null;
                                FragmentTransaction ftrans = fragmentManager.beginTransaction();
                                Fragment frag =  new FragmentForum();
                                ftrans.replace(R.id.container_home, frag);
                                ftrans.addToBackStack(null);
                                ftrans.commit();
                            }else {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return v;
    }
}
