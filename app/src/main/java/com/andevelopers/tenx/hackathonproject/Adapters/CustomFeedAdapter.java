package com.andevelopers.tenx.hackathonproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andevelopers.tenx.hackathonproject.R;
import com.andevelopers.tenx.hackathonproject.Utils.Feed;

import java.util.ArrayList;
import java.util.List;

public class CustomFeedAdapter extends RecyclerView.Adapter<CustomFeedAdapter.ViewHolder> {

    private Context mCtx;
    private List<Feed> mList;

    public CustomFeedAdapter(Context mCtx, List<Feed> mList) {
        this.mCtx = mCtx;
        this.mList = mList;
    }

    public CustomFeedAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(mCtx).inflate(R.layout.vh_student_feed, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Feed currentFeed = mList.get(i);
        viewHolder.tvHeader.setText(currentFeed.getHeader());
        viewHolder.tvText.setText(currentFeed.getText());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvHeader, tvText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeader = itemView.findViewById(R.id.tv_header_feeds);
            tvText = itemView.findViewById(R.id.tv_feed_display);
        }
    }


    //add single feed func

    public void addFeedAndNotify(Feed feed){
        if (mList == null){
            mList = new ArrayList<>();
        }
        mList.add(feed);
        notifyDataSetChanged();
    }

}
