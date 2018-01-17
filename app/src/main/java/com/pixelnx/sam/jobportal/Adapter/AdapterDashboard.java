package com.pixelnx.sam.jobportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pixelnx.sam.jobportal.Activity.FullDetailsActivity;
import com.pixelnx.sam.jobportal.DTO.ActiveJobDTO;
import com.pixelnx.sam.jobportal.Fragment.SeekerHomeFragment;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomButton;
import com.pixelnx.sam.jobportal.utils.CustomTextview;
import com.pixelnx.sam.jobportal.utils.CustomTextviewBold;

import java.util.ArrayList;

/**
 * Created by shubham on 10/8/17.
 */

public class AdapterDashboard extends RecyclerView.Adapter<AdapterDashboard.Mainholder> {


    private ArrayList<ActiveJobDTO.Data> dataArrayList;
    private Context mContext;
    private SeekerHomeFragment SeekerHomeFragment;

    public AdapterDashboard(ArrayList<ActiveJobDTO.Data> dataArrayList, Context mContext, SeekerHomeFragment seekerHomeFragment) {
        this.dataArrayList = dataArrayList;
        this.mContext = mContext;
        SeekerHomeFragment = seekerHomeFragment;
    }

    @Override
    public Mainholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dashboard, parent, false);
        Mainholder mainHolder = new Mainholder(view);

        return mainHolder;
    }

    @Override
    public void onBindViewHolder(Mainholder holder, final int position) {

        holder.tvdeisgnation.setText(dataArrayList.get(position).getJob_by_roles());
        holder.tvOrganisation.setText(dataArrayList.get(position).getPosted_recruiter().getOrganisation_name());
        holder.tvExperience.setText(dataArrayList.get(position).getExperience());
        holder.tvLocation.setText(dataArrayList.get(position).getJob_location());
        if (dataArrayList.get(position).getSkills_required().equals("")) {
            holder.tvSkills.setText("N/A");

        } else {
            holder.tvSkills.setText(dataArrayList.get(position).getSkills_required());
        }
        holder.ViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FullDetailsActivity.class);
                intent.putExtra(Consts.ACTIVE_JOBS_DTO, dataArrayList.get(position));
                mContext.startActivity(intent);
            }
        });

        if (dataArrayList.get(position).is_applied()) {
            holder.llApplied.setVisibility(View.VISIBLE);
        } else {
            holder.llApplied.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class Mainholder extends RecyclerView.ViewHolder {
        private CustomTextviewBold tvdeisgnation;
        private CustomTextview tvOrganisation, tvExperience, tvLocation, tvSkills;
        private CustomButton ViewBtn;
        private LinearLayout llApplied;


        public Mainholder(View itemView) {
            super(itemView);
            tvdeisgnation = (CustomTextviewBold) itemView.findViewById(R.id.tvdeisgnation);
            tvOrganisation = (CustomTextview) itemView.findViewById(R.id.tvOrganisation);
            tvExperience = (CustomTextview) itemView.findViewById(R.id.tvExperience);
            tvLocation = (CustomTextview) itemView.findViewById(R.id.tvLocation);
            tvSkills = (CustomTextview) itemView.findViewById(R.id.tvSkills);
            ViewBtn = (CustomButton) itemView.findViewById(R.id.ViewBtn);
            llApplied = (LinearLayout) itemView.findViewById(R.id.llApplied);

        }
    }
}