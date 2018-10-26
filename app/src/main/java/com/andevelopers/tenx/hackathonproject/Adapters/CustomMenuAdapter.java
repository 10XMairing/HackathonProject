package com.andevelopers.tenx.hackathonproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andevelopers.tenx.hackathonproject.R;
import com.andevelopers.tenx.hackathonproject.Utils.Feed;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomMenuAdapter extends RecyclerView.Adapter<CustomMenuAdapter.ViewHolder> {

    private Context mCtx;
    private List<String> mList;

    public CustomMenuAdapter(Context mCtx, List<String> mList) {
        this.mCtx = mCtx;
        this.mList = mList;
    }

    public CustomMenuAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mCtx).inflate(R.layout.vh_menu_listitem, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String currentName = mList.get(i);
        viewHolder.tvName.setText(currentName);
        viewHolder.btnChangeForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, "this will change forum", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView  tvName;
        private CircleImageView imgProfile;
        private Button btnChangeForum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_user_name);
            imgProfile = itemView.findViewById(R.id.civ_user_image);
            btnChangeForum = itemView.findViewById(R.id.btn_view);
        }
    }


    //add single feed func

    public void addFeedAndNotify(String str){
        if (mList == null){
            mList = new ArrayList<>();
        }
        if(!mList.contains(str)){
            mList.add(str);
        }
        notifyDataSetChanged();

    }

    public void clearList(){
        mList.clear();
    }

}
