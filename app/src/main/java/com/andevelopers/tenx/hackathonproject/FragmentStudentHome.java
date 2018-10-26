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

public class FragmentStudentHome extends Fragment {

    Button btnLogout;
    ViewPager pager;
    CustomPagerAdapter adapter;
    TabLayout tabLayout;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_student, container, false);





        btnLogout = v.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


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
