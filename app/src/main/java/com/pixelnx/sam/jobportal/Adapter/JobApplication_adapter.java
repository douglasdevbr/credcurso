package com.pixelnx.sam.jobportal.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.Activity.JobsView;
import com.pixelnx.sam.jobportal.Activity.Recruiter_PostJob_Activity;
import com.pixelnx.sam.jobportal.DTO.CommonDTO;
import com.pixelnx.sam.jobportal.DTO.JobApplicationDTO;
import com.pixelnx.sam.jobportal.Fragment.JobApplication;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomTextview;
import com.pixelnx.sam.jobportal.utils.CustomTextviewBold;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by shubham on 28/8/17.
 */

public class JobApplication_adapter extends RecyclerView.Adapter<JobApplication_adapter.JobAppHolder> {

    private ArrayList<JobApplicationDTO.Data> jobApplicationDTOList = new ArrayList<>();
    private Context mContext;
    private CommonDTO commonDTO;
    SharedPrefrence sharedPrefrence;
    int lastPosition = -1;
    private JobApplication jobApplication;

    public JobApplication_adapter(ArrayList<JobApplicationDTO.Data> jobApplicationDTOList, Context mContext, JobApplication jobApplication) {
        this.jobApplicationDTOList = jobApplicationDTOList;
        this.mContext = mContext;
        this.jobApplication = jobApplication;


    }

    @Override
    public JobAppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_jobapplication, parent, false);
        JobAppHolder jobAppHolder = new JobAppHolder(view);

        return jobAppHolder;

    }

    @Override
    public void onBindViewHolder(final JobAppHolder holder, final int position) {
        sharedPrefrence = SharedPrefrence.getInstance(mContext);
        holder.tvCount.setText(jobApplicationDTOList.get(position).getNo_applied_by());
        holder.tvSpecialn.setText(jobApplicationDTOList.get(position).getSpecialization());
        holder.tvJobDescription.setText("" + jobApplicationDTOList.get(position).getJob_discription());


        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!jobApplicationDTOList.get(position).getNo_applied_by().equalsIgnoreCase("0")) {
                    Intent in = new Intent(mContext, JobsView.class);
                    in.putExtra(Consts.RECRUITER_ID, sharedPrefrence.getRecruiterDTO(Consts.RECRUITER_DTO).data.getId());
                    in.putExtra(Consts.JOB_ID, jobApplicationDTOList.get(position).getId());
                    in.putExtra(Consts.ET_NAME, holder.tvSpecialn.getText().toString().trim());
                    mContext.startActivity(in);
                } else {

                    ProjectUtils.showSweetDialog(mContext, "Applied seeker not found.", "", "Ok", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();


                        }
                    }, SweetAlertDialog.WARNING_TYPE);
                }
            }
        });
        holder.tvDeleteJOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastPosition = position;
                ProjectUtils.showAlertDialog(mContext, "Delete Job", "Are you sure want to delete your Job", "Delete", "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteJOB(position);
                        dialogInterface.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
            }
        });
        holder.tveditJOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mContext, Recruiter_PostJob_Activity.class);
                in.putExtra(Consts.JOB_ID, jobApplicationDTOList.get(position).getId());
                in.putExtra(Consts.FLAG, 2);
                mContext.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobApplicationDTOList.size();
    }

    public class JobAppHolder extends RecyclerView.ViewHolder {
        CustomTextview tvCount, tvDeleteJOB, tveditJOB, tvJobDescription;
        CustomTextviewBold tvSpecialn;
        LinearLayout llMain, llNext;

        public JobAppHolder(View itemView) {
            super(itemView);
            tvSpecialn = (CustomTextviewBold) itemView.findViewById(R.id.tvSpecialn);
            llNext = (LinearLayout) itemView.findViewById(R.id.llNext);
            tvCount = (CustomTextview) itemView.findViewById(R.id.tvCount);
            llMain = (LinearLayout) itemView.findViewById(R.id.llMain);
            tvDeleteJOB = (CustomTextview) itemView.findViewById(R.id.tvDeleteJOB);
            tveditJOB = (CustomTextview) itemView.findViewById(R.id.tveditJOB);
            tvJobDescription = (CustomTextview) itemView.findViewById(R.id.tvJobDescription);

        }
    }

    public void deleteJOB(final int position) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.DELETE_JOB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("STATUS JOBS", response.toString());
                            commonDTO = new Gson().fromJson(response, CommonDTO.class);
                            if (commonDTO.isStatus()) {
                                ProjectUtils.showToast(mContext, commonDTO.getMessage());
                                jobApplicationDTOList.remove(lastPosition);
                                if (jobApplicationDTOList.size() == 0) {
                                    jobApplication.llIcon.setVisibility(View.VISIBLE);
                                }else {
                                    jobApplication.llIcon.setVisibility(View.GONE);
                                }
                                notifyDataSetChanged();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Consts.RECRUITER_ID, sharedPrefrence.getRecruiterDTO(Consts.RECRUITER_DTO).data.getId());
                params.put(Consts.JOB_ID, jobApplicationDTOList.get(position).getId());

                Log.e("value", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);


    }


}
