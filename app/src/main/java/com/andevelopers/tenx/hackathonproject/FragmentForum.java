package com.andevelopers.tenx.hackathonproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.andevelopers.tenx.hackathonproject.Adapters.CustomMenuAdapter;
import com.andevelopers.tenx.hackathonproject.Utils.Feed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentForum extends Fragment{

    RecyclerView recyclerView;
    FloatingActionButton fabAddPost;
    ImageView ivTeachersMenu;
    CustomForumAdapter adapter;
    TextView tvHeaderProf;
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    public static final int REQUEST_CODE_MENU = 100;
    String teacherID;

    //temp
    CollectionReference forum;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discussion_forum, container, false);

        recyclerView = v.findViewById(R.id.recycler_diss_forum);
        adapter = new CustomForumAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        //display teacher name
        tvHeaderProf = v.findViewById(R.id.tv_teacher_id);


        //get subs
        CollectionReference refSub = db.collection("students").document(ActivityHome.userID).collection("userSubs");
        refSub.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                if(list.size() != 0){
                    teacherID= list.get(0).getId();
                    displayForumTeacher(teacherID);
                    tvHeaderProf.setText(teacherID);
                }
            }
        });





        //fab
        fabAddPost = v.findViewById(R.id.fab_add_post);
        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Add post button", Toast.LENGTH_SHORT).show();
                createDialog(getActivity());
            }
        });




        ivTeachersMenu = v.findViewById(R.id.iv_menu_teachers);

        ivTeachersMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(getActivity(), ActivityMenuSubs.class);
               startActivityForResult(i, REQUEST_CODE_MENU);


            }
        });





        return v;
    }

    public void displayForumTeacher(String teacherID){
        adapter.clearList();
        forum = db.collection("teachers").document(teacherID).collection("forum");
        forum.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                for(DocumentSnapshot snap : list){
                    String strID = snap.getId();
                    String name = snap.getString("name");
                    String text = snap.getString("text");
                    adapter.addFeedAndNotify(new Feed(strID, name, text));
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_MENU){
            if(resultCode == Activity.RESULT_OK){
                teacherID = data.getStringExtra(CustomMenuAdapter.USER_RESULT);
                displayForumTeacher(teacherID);
                tvHeaderProf.setText(teacherID);
            }
        }



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
                    CollectionReference colRef = db.collection("teachers").document(teacherID).collection("forum");
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
