package com.andevelopers.tenx.hackathonproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andevelopers.tenx.hackathonproject.Adapters.CustomPagerAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentStudentHome extends Fragment {

    ViewPager pager;
    CustomPagerAdapter adapter;
    TabLayout tabLayout;
    CircleImageView profileIcon;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_student, container, false);

        profileIcon = v.findViewById(R.id.civ_student_profile_image);
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityStudenProfile.class);
                startActivity(intent);
            }
        });
        //test rishab


        //setting up the pager
        pager = v.findViewById(R.id.pager_student_home);
        adapter = new CustomPagerAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        tabLayout = v.findViewById(R.id.home_tablayout);
        tabLayout.setupWithViewPager(pager);

        return v;
    }


    public void goBack(){
        Intent i = new Intent(getActivity(), ActivityLogin.class);
        startActivity(i);
    }
}
