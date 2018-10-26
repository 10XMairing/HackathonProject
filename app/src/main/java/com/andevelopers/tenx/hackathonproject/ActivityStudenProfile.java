package com.andevelopers.tenx.hackathonproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityStudenProfile extends AppCompatActivity {


    TextView tvName, tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studen_profile);
        tvName = findViewById(R.id.tv_username);
        tvLogout = findViewById(R.id.tv_logout);

        tvName.setText(ActivityHome.userID);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityStudenProfile.this, ActivityHome.class);
                startActivity(i);
            }
        });




    }
}
