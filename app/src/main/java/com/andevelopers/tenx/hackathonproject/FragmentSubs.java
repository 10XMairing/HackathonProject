package com.andevelopers.tenx.hackathonproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andevelopers.tenx.hackathonproject.Adapters.CustomSubsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FragmentSubs extends Fragment{
    RecyclerView recyclerView;
    TabLayout tabLayout;
    CustomSubsAdapter adapter;
    FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    CollectionReference refTeachers = mDb.collection("teachers");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_subs, container, false);
        recyclerView = v.findViewById(R.id.recycler_subs);
        tabLayout = v.findViewById(R.id.home_tablayout);

        adapter = new CustomSubsAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        refTeachers.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<DocumentSnapshot> mList = task.getResult().getDocuments();
                    for(DocumentSnapshot snap : mList){
                        String id = snap.getId();
                        adapter.addSubAndNotify(id);
                    }


                }
            }
        });



        return v;
    }
}
