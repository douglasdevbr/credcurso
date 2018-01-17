package com.pixelnx.sam.jobportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pixelnx.sam.jobportal.Activity.SingleJob;
import com.pixelnx.sam.jobportal.DTO.JobsViewDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomTextview;
import com.pixelnx.sam.jobportal.utils.CustomTextviewBold;

import java.util.ArrayList;

/**
 * Created by shubham on 29/8/17.
 */

public class JobViewAdapter extends RecyclerView.Adapter<JobViewAdapter.JobViewHolder> {

    public JobsViewDTO.Data profile;
    private String jobid;
    private ArrayList<JobsViewDTO.Data> dataList = new ArrayList<>();
    private Context mContext;

    public JobViewAdapter(ArrayList<JobsViewDTO.Data> dataList, Context mContext, String jobid) {
        this.dataList = dataList;
        this.mContext = mContext;
        this.jobid = jobid;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rui_b, parent, false);
        JobViewHolder jobViewHolder = new JobViewHolder(view);

        return jobViewHolder;
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, final int position) {
        holder.tvName.setText(dataList.get(position).getFull_name());
        holder.tvRole.setText(dataList.get(position).getRole_type());
        holder.tvMobile.setText(dataList.get(position).getMobile_no());

        //   holder.tvSkills.setText(dataArrayList.get(position).getSkills_required());
        holder.cardClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mContext, SingleJob.class);
                intent.putExtra(Consts.SEEKER_ID, dataList.get(position).getSeeker_id());
                intent.putExtra(Consts.JOB_ID, jobid);
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {
        public CustomTextview tvRole, tvMobile;
        public CardView cardClick;
        public CustomTextviewBold tvName;
        public LinearLayout llNext;

        public JobViewHolder(View itemView) {
            super(itemView);
            cardClick = (CardView) itemView.findViewById(R.id.cardClick);
            tvMobile = (CustomTextview) itemView.findViewById(R.id.tvMobile);
            tvRole = (CustomTextview) itemView.findViewById(R.id.tvRole);
            tvName = (CustomTextviewBold) itemView.findViewById(R.id.tvName);
            llNext = (LinearLayout) itemView.findViewById(R.id.llNext);


        }
    }
}
