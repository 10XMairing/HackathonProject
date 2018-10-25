package com.andevelopers.tenx.hackathonproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {

    Button btnStudent, btnTeacher;

    // TODO the first number in the students/teachers id stored in database will be the following numbers
    private static final int KEY_STUDENT = 1;
    private static final int KEY_TEACHER = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnStudent = findViewById(R.id.btn_student_logn);
        btnTeacher = findViewById(R.id.btn_teacher_login);


        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Student Login", ActivityLogin.this, KEY_STUDENT);


            }
        });
        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showDialog("Teacher Login", ActivityLogin.this, KEY_TEACHER);





            }
        });



    }

    public View showDialog(String header, Context context, final int key){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_login_input, null);
        builder.setCancelable(true);

        //increase the width if dialog
        Activity activity = (Activity) context;
        Rect displayRectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        dialogView.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
        dialogView.setMinimumHeight((int)(displayRectangle.height() * 0.7f));


        //setting the header
        TextView tvHeader = dialogView.findViewById(R.id.tv_login_header);
        tvHeader.setText(header);

        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.show();


        //setting up the login btn
        Button btnLogin = dialogView.findViewById(R.id.btn_login);
        final EditText tvName = dialogView.findViewById(R.id.et_name);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tvName.getText().toString();
                if(!name.equals("")){
                    if(key == 1){
                        dialog.dismiss();
                        loginAsStudent(name);
                    }else if(key == 2){
                        dialog.dismiss();
                        loginAsTeacher(name);
                    }
                }else {
                    Toast.makeText(ActivityLogin.this, "Enter a valid name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return dialogView;

    }

    public void loginAsStudent(String name){
        Intent intent = new Intent();
        intent.putExtra(ActivityHome.LOGIN_KEY, 1);
        //TODO check for users in database -> add a new one if name doesnt exits and create a new id -> else login with the same id
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    public void loginAsTeacher(String name){
        Intent intent = new Intent();
        intent.putExtra(ActivityHome.LOGIN_KEY, 2);
        //TODO check for users in database -> add a new one if name doesnt exits and create a new id -> else login with the same id
        setResult(Activity.RESULT_OK, intent);
        finish();
    }




}
