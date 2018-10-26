package com.andevelopers.tenx.hackathonproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andevelopers.tenx.hackathonproject.Adapters.CustomFeedAdapter;
import com.andevelopers.tenx.hackathonproject.Utils.Feed;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentFeeds extends Fragment{


    FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private List<Feed> mList;
    String userID = ActivityHome.userID;
    //temp ref
    CollectionReference refSubs = mDb.collection("students").document(userID).collection("userSubs");
    List<String> subList;



    //adapters
    CustomFeedAdapter adapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_feeds, container, false);

        subList = new ArrayList<>();

        //set up the adapter
        adapter = new CustomFeedAdapter(getActivity());
        recyclerView = v.findViewById(R.id.recycler_feeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        refSubs.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                Log.d("test", "retrieve sub success");
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot snap: list){
                    Log.d("test", "subbed teacher "+snap.getId());
                    subList.add(snap.getId());
                }

                // subbing to the teachers
                adapter.clearList();
                for(final String teacherID : subList){

                    Log.d("test", "access teacher database  ->"+teacherID);
                    CollectionReference ref = mDb.collection("teachers").document(teacherID).collection("updates");

                    ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                            Log.d("test", "getting updates from teacher ID"+teacherID);
                            List<DocumentSnapshot> mList = queryDocumentSnapshots.getDocuments();
                            Log.d("test", "list updates size -> "+mList.size());


                            for(DocumentSnapshot snap : mList){
                                String text = snap.getString("text");
                                Log.d("test", text);

                                adapter.addFeedAndNotify(new Feed(teacherID, text, System.currentTimeMillis()));
                            }
                        }
                    });

                }

                Log.d("test", "sublist size --> "+subList.size());
            }
        });


        Log.d("test", "sublist size --> "+subList.size());


        //subbing to the subbed teachers









        return v;
    }
}
