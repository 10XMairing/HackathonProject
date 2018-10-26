package com.andevelopers.tenx.hackathonproject;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andevelopers.tenx.hackathonproject.Adapters.CustomMenuAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ActivityMenuSubs extends AppCompatActivity {


    RecyclerView recyclerView;
    CustomMenuAdapter adapter;
    FirebaseFirestore db =FirebaseFirestore.getInstance();

    //temp
    CollectionReference refSub = db.collection("students").document(ActivityHome.userID).collection("userSubs");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_discussion_forum);


        recyclerView = findViewById(R.id.recycler_diss_forum);
        adapter = new CustomMenuAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.fab_add_post);
        fab.setVisibility(View.GONE);


        refSub.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                for(DocumentSnapshot snap : list){
                    String teacherID = snap.getId();
                    adapter.addFeedAndNotify(teacherID);

                }
            }
        });





    }
}
