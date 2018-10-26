package com.andevelopers.tenx.hackathonproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {

    Button btnStudent, btnTeacher;

    // TODO the first number in the students/teachers id stored in database will be the following numbers
    private static final int KEY_STUDENT = 1;
    private static final int KEY_TEACHER = 2;



    private static final String TAG = "LoginActivity";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference teachersRef = db.collection("teachers");
    CollectionReference studentsRef = db.collection("students");

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
                String name = tvName.getText().toString().toLowerCase();
                if(!name.equals("")){
                    if(key == 1){
                        dialog.dismiss();
                        checkAndLogIn(name, KEY_STUDENT);
                    }else if(key == 2){
                        dialog.dismiss();
                        checkAndLogIn(name, KEY_TEACHER);
                    }
                }else {
                    Toast.makeText(ActivityLogin.this, "Enter a valid name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return dialogView;

    }

    public void checkAndLogIn(final String name, final int logKey){
        final CollectionReference ref;
        if(logKey == KEY_STUDENT){
            ref = studentsRef;

        }else {
            ref = teachersRef;
        }

        //TODO check for users in database -> add a new one if name doesnt exits and create a new id -> else login with the same id
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if(task.isSuccessful()){
                QuerySnapshot query = task.getResult();
                int flag = -1;
                for(QueryDocumentSnapshot doc : query){
                    String userID = doc.getId();
                    if(userID.equals(name)){
                        flag = 1;
                        break;
                    }
                }

                if(flag != 1){
                    //user doesnot exist , create new document
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    Log.d(TAG, "user not found");
                    ref.document(name).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                               goNext(name, logKey);
                                Toast.makeText(ActivityLogin.this, "Created new user / Logged in", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(ActivityLogin.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    //user exists --> simply go to new activity
                    Toast.makeText(ActivityLogin.this, "logging in as existing user", Toast.LENGTH_SHORT).show();
                    goNext(name, logKey);

                }
            }

            }
        });

    }

    public void goNext(String name, int key){
        Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
        intent.putExtra(ActivityHome.KEY_LOGIN, key);
        intent.putExtra(ActivityHome.KEY_NAME, name);
        startActivity(intent);
        finish();
    }

}
