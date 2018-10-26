package com.andevelopers.tenx.hackathonproject;

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
import android.widget.ImageView;

import com.andevelopers.tenx.hackathonproject.Adapters.CustomForumAdapter;
import com.andevelopers.tenx.hackathonproject.Utils.Feed;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class FragmentForum extends Fragment{

    RecyclerView recyclerView;
    FloatingActionButton fabAddPost;
    ImageView ivTeachersMenu;
    CustomForumAdapter adapter;
    FirebaseFirestore db =FirebaseFirestore.getInstance();

    //temp
    CollectionReference forum = db.collection("teachers").document("adarsh").collection("forum");
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





        return v;
    }
}
