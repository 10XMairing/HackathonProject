package com.andevelopers.tenx.hackathonproject.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.andevelopers.tenx.hackathonproject.ActivityHome;
import com.andevelopers.tenx.hackathonproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CustomSubsAdapter extends RecyclerView.Adapter<CustomSubsAdapter.ViewHolder> {

    private Context mCtx;
    private List<String> mList;
    FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    CollectionReference refSubs;

    public CustomSubsAdapter(Context mCtx, List<String> mList) {
        this.mCtx = mCtx;
        this.mList = mList;
        refSubs = mDb.collection("students").document(ActivityHome.userID).collection("userSubs");

    }

    public CustomSubsAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mList = new ArrayList<>();

        refSubs = mDb.collection("students").document(ActivityHome.userID).collection("userSubs");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mCtx).inflate(R.layout.vh_student_subs, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String currentTeacherID = mList.get(i);
        viewHolder.tvName.setText(currentTeacherID);

        //check tog state
        SharedPreferences prefs = mCtx.getSharedPreferences("state", MODE_PRIVATE);
        Boolean restoredText = prefs.getBoolean("togs", false);



        viewHolder.togBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //subscribe to teacher
                    Map<String, Object> data = new HashMap<>();
                    data.put("subbed", true);
                    refSubs.document(currentTeacherID).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                String display = "Subbed to "+currentTeacherID;
                                Toast.makeText(mCtx, display, Toast.LENGTH_SHORT).show();

                                //save this state
                                SharedPreferences.Editor editor = ((ActivityHome) mCtx).getSharedPreferences("state", MODE_PRIVATE).edit();
                                editor.putBoolean("togs", true);
                                editor.apply();

                            }
                        }
                    });
                }else {
                    //unsub
                    refSubs.document(currentTeacherID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String display = "UnSubbed to "+currentTeacherID;
                            notifyDataSetChanged();
                            Toast.makeText(mCtx, display, Toast.LENGTH_SHORT).show();
                            //save this state
                            SharedPreferences.Editor editor = ((ActivityHome) mCtx).getSharedPreferences("state", MODE_PRIVATE).edit();
                            editor.putBoolean("togs", false);
                            editor.apply();


                        }
                    });
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private ToggleButton togBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_teacher_name);
            togBtn = itemView.findViewById(R.id.tog_sub);

        }
    }


    //add single feed func

    public void addSubAndNotify(String profID){
        if (mList == null){
            mList = new ArrayList<>();
        }
        mList.add(profID);
        notifyDataSetChanged();
    }

}
