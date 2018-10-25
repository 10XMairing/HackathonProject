package com.andevelopers.tenx.hackathonproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ActivityHome extends AppCompatActivity {

    private static int id;
    public static final String LOGIN_KEY = "log_key";
    public static final String TAG = "ActivityHome";
    public static final int REQUEST_CODE = 122;
    SharedPreferences sPref;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        //test
        sPref = getPreferences(Context.MODE_PRIVATE);

        id = sPref.getInt(LOGIN_KEY, -1);
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
        goLogin();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "OnActivityResult called");
        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                int returnInt = data.getIntExtra(LOGIN_KEY, -1);
                editor = sPref.edit();
                editor.putInt(LOGIN_KEY, returnInt);
                editor.apply();
                Log.d(TAG, "recieved key : --> "+returnInt);
                showFragment(returnInt);

            }
        }

    }

    public void goLogin(){
        Intent intent = new Intent(ActivityHome.this, ActivityLogin.class);
        sPref.edit().clear().apply();
        id = -1;
        startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE);

    }



    public void showFragment(int fragKey){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction  ftrans = fm.beginTransaction();
        //id here will change, it should only check the first number fo the id
        if(fragKey == 1){
            ftrans.replace(R.id.container_home, new FragmentStudentHome());
        }else if (fragKey == 2){
            ftrans.replace(R.id.container_home, new FragmentTeacherHome());
        }
        ftrans.addToBackStack(null);
        ftrans.commit();
    }
}
