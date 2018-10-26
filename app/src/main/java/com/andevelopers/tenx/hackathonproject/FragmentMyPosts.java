package com.andevelopers.tenx.hackathonproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andevelopers.tenx.hackathonproject.Adapters.CustomMyPostsAdapter;
import com.andevelopers.tenx.hackathonproject.Utils.Feed;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FragmentMyPosts extends Fragment{
    RecyclerView recyclerView;
    CustomMyPostsAdapter adapter;
    private String userid = ActivityHome.userID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference refUpdates = db.collection("teachers").document(userid).collection("updates");





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myposts, container, false);
        recyclerView = v.findViewById(R.id.recycler_myposts);
        adapter = new CustomMyPostsAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        refUpdates.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                adapter.clearList();
                for(DocumentSnapshot snap : list){
                    String text = snap.getString("text");
                    adapter.addFeedAndNotify(new Feed(userid, text));
                }
            }
        });


        return v;
    }
}
