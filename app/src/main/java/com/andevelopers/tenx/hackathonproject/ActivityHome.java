package com.andevelopers.tenx.hackathonproject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ActivityHome extends AppCompatActivity {

    private static int id;
    public static String userID;
    public static final String KEY_LOGIN = "log_key";
    public static final String KEY_NAME = "name_key";
    public static final String KEY_USERID = "userid";
    public static final String TAG = "ActivityHome";
    public static final String TAG_BACKSTACK = "teacher_fragments";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //recieve data
        id = getIntent().getIntExtra(KEY_LOGIN, -1);
        userID = getIntent().getStringExtra(KEY_NAME);


        Log.d(TAG, "user id : "+id);
        if(id == -1){
            goLogin();
        }else {
            showFragment(id);
        }

        //setting up the fragments

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }else {
            goLogin();
        }
    }



    public void goLogin(){
        Intent intent = new Intent(ActivityHome.this, ActivityLogin.class);
        startActivity(intent);
    }



    public void showFragment(int fragKey){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction  ftrans = fm.beginTransaction();
        //id here will change, it should only check the first number fo the id
        Fragment frag;

        if(fragKey == 1){
            frag =  new FragmentStudentHome();
        }else {
            frag = new FragmentTeacherHome();
        }
        Bundle args = new Bundle();
        args.putString(KEY_USERID, userID);
        frag.setArguments(args);
        ftrans.replace(R.id.container_home, frag);
        ftrans.addToBackStack(null);
        ftrans.commit();
    }
}
