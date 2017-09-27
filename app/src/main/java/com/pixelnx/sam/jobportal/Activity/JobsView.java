package com.pixelnx.sam.jobportal.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.pixelnx.sam.jobportal.Adapter.JobViewAdapter;
import com.pixelnx.sam.jobportal.DTO.JobsViewDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomTextHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobsView extends AppCompatActivity {
    private RecyclerView rvJobsView;
    JobsViewDTO jobsViewDTO;
    private ArrayList<JobsViewDTO.Data> dataList = new ArrayList<>();
    private Context mContext;
    private JobViewAdapter jobViewAdapter;
    private CustomTextHeader tvJobs;
    private String jobid, recid, actionBarText;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_view);
        mContext = JobsView.this;
        jobid = getIntent().getExtras().getString(Consts.JOB_ID);
        recid = getIntent().getExtras().getString(Consts.RECRUITER_ID);
        actionBarText = getIntent().getExtras().getString(Consts.ET_NAME);
        init();
        tvJobs.setText("" + actionBarText);
    }

    private void init() {
        rvJobsView = (RecyclerView) findViewById(R.id.rvJobsView);
        tvJobs = (CustomTextHeader) findViewById(R.id.tvJobs);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        viewJobs();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void viewJobs() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.JOB_APPLICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("Job Application", response.toString());
                            jobsViewDTO = new Gson().fromJson(response, JobsViewDTO.class);
                            if (jobsViewDTO.isStatus()) {
                                dataList = jobsViewDTO.getData();
                                LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                                linearLayoutManagerVertical.supportsPredictiveItemAnimations();
                                rvJobsView.setLayoutManager(linearLayoutManagerVertical);

                                jobViewAdapter = new JobViewAdapter(dataList, mContext,jobid);
                                rvJobsView.setAdapter(jobViewAdapter);
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
                params.put(Consts.RECRUITER_ID, recid);
                params.put(Consts.JOB_ID, jobid);
                Log.e("value", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
   finish();
    }
}
