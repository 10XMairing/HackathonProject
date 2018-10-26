package com.andevelopers.tenx.hackathonproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentFeeds extends Fragment{


    FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    CollectionReference refTeachers = mDb.collection("teachers");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_feeds, container, false);

        //sub to teachers and display feeds





        return v;
    }
}
