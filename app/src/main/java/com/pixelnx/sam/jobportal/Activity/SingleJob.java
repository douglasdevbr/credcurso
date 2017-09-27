package com.pixelnx.sam.jobportal.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.DTO.SingleJobDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomButton;
import com.pixelnx.sam.jobportal.utils.CustomTextHeader;
import com.pixelnx.sam.jobportal.utils.CustomTextview;
import com.pixelnx.sam.jobportal.utils.CustomTextviewBold;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.util.HashMap;
import java.util.Map;

public class SingleJob extends AppCompatActivity implements View.OnClickListener {
    private CustomTextviewBold tvName;
    private CustomTextview tvExperience, tvLocation, tvSkills, tvJobApplied, tvJobtype, tvDesignation, tvQualification, tvlastDOAPP, tvJobDescription, tvSpecialization;
    // public JobsViewDTO.Data.Profile profile;
    private ImageView ivBack;
    private CustomTextHeader tvHeader;
    private SingleJobDTO.Data data;
    private CustomButton btnCallNow;
    private SingleJobDTO singleJobDTO;
    private String seeker_id, jobid;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rui_c);
        mContext = SingleJob.this;

        seeker_id = getIntent().getExtras().getString(Consts.SEEKER_ID);
        jobid = getIntent().getExtras().getString(Consts.JOB_ID);
        init();

    }

    private void init() {
        tvHeader = (CustomTextHeader) findViewById(R.id.tvHeader);

        tvName = (CustomTextviewBold) findViewById(R.id.tvName);
        tvExperience = (CustomTextview) findViewById(R.id.tvExperience);
        tvLocation = (CustomTextview) findViewById(R.id.tvLocation);
        tvJobApplied = (CustomTextview) findViewById(R.id.tvJobApplied);
        tvSkills = (CustomTextview) findViewById(R.id.tvSkills);
        tvJobtype = (CustomTextview) findViewById(R.id.tvJobtype);
        tvDesignation = (CustomTextview) findViewById(R.id.tvDesignation);
        tvQualification = (CustomTextview) findViewById(R.id.tvQualification);
        tvlastDOAPP = (CustomTextview) findViewById(R.id.tvlastDOAPP);
        tvSpecialization = (CustomTextview) findViewById(R.id.tvSpecialization);
        tvJobDescription = (CustomTextview) findViewById(R.id.tvJobDescription);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        btnCallNow = (CustomButton) findViewById(R.id.btnCallNow);

//        tvHeader.setText(data.getFull_name());
        //    tvName.setText(data.getFull_name());
        //    tvExperience.setText(profile.getExperience_in_year()+" "+profile.getExperience_in_months());
        //     tvJobtype.setText(profile.getSeeker_job_type());
        //     tvLocation.setText(profile.getSeeker_prefered_location());
        ////     tvSkills.setText(profile.getSeeker_specialization());
        //     tvDesignation.setText(profile.getSeeker_role_type());

        //  tvJobApplied.setText(profile.getSeek());

        //       tvQualification.setText(profile.getSeeker_qulification());
        //      tvSpecialization.setText(profile.getSeeker_specialization());

        btnCallNow.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        viewProfile();
    }

    private void viewProfile() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.SEEKER_PROFILE_DETAIL_ON_JOB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("Single Job View", response.toString());
                            singleJobDTO = new Gson().fromJson(response, SingleJobDTO.class);
                            if (singleJobDTO.isStatus()) {
                                data = singleJobDTO.getData();
                                showDetails(data);

                            } else {
                                ProjectUtils.showToast(mContext, singleJobDTO.getMessage());
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
                params.put(Consts.SEEKER_ID, seeker_id);
                params.put(Consts.JOB_ID, jobid);
                Log.e("value", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    private void showDetails(SingleJobDTO.Data data) {
        SingleJobDTO.Data.Profile profile = data.getProfile();

        tvHeader.setText("Seeker Detail");
        tvName.setText(data.getFull_name());
        tvExperience.setText(profile.getExperience_in_year()+" "+profile.getExperience_in_months());
        tvJobtype.setText(profile.getSeeker_job_type());
        tvLocation.setText(profile.getSeeker_prefered_location());
        tvSkills.setText(profile.getSeeker_specialization());
        tvDesignation.setText(profile.getSeeker_role_type());
        //  tvJobApplied.setText(profile.getSeeker_apply_date());
        tvQualification.setText(profile.getSeeker_qulification());
        tvSpecialization.setText(profile.getSeeker_specialization());

        ProjectUtils.dateFormate(tvJobApplied, profile.getSeeker_apply_date());
    }

    @Override
    public void onBackPressed() {
        //    super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnCallNow:
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + data.getMobile_no() + ""));
                    startActivity(callIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
