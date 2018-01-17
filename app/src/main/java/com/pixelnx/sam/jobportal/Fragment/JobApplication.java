package com.pixelnx.sam.jobportal.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.Activity.RecruiterDashboardActivity;
import com.pixelnx.sam.jobportal.Activity.Recruiter_PostJob_Activity;
import com.pixelnx.sam.jobportal.Adapter.JobApplication_adapter;
import com.pixelnx.sam.jobportal.DTO.JobApplicationDTO;
import com.pixelnx.sam.jobportal.DTO.UserRecruiterDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.network.NetworkManager;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobApplication extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<JobApplicationDTO.Data> jobApplicationDTOList = new ArrayList<>();
    private JobApplicationDTO jobApplicationDTO;
    private JobApplication_adapter jobApplication_adapter;
    private UserRecruiterDTO.Data data;
    public LinearLayout llIcon;
    // private RecruiterProfileDTO recruiterProfileDTO;
    View view;
    private RecyclerView rvJobAppliction;
    private ImageView fab_image;
    RecruiterDashboardActivity recruiterDashboardActivity;
    private SharedPrefrence sharedPrefrence;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_job_application, container, false);
        sharedPrefrence = SharedPrefrence.getInstance(getActivity());
        recruiterDashboardActivity.tvJobs.setVisibility(View.VISIBLE);
        recruiterDashboardActivity.tvJobs.setText(getResources().getString(R.string.hed_posted_job));
        recruiterDashboardActivity.search_icon.setVisibility(View.INVISIBLE);
        recruiterDashboardActivity.filter_icon.setVisibility(View.INVISIBLE);
        init(view);
        return view;
    }

    private void init(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        llIcon = (LinearLayout) view.findViewById(R.id.llIcon);
        rvJobAppliction = (RecyclerView) view.findViewById(R.id.rvJobAppliction);
        fab_image = (ImageView) view.findViewById(R.id.fab_image);


        fab_image.setOnClickListener(this);


    }

    public void showPostedJobs() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.GET_RECRUITER_JOBS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);
                        try {
                            Log.e("REQUITER JOBS", response.toString());
                            jobApplicationDTO = new Gson().fromJson(response, JobApplicationDTO.class);
                            if (jobApplicationDTO.isStatus()) {
                                jobApplicationDTOList = jobApplicationDTO.getData();
                                LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                linearLayoutManagerVertical.supportsPredictiveItemAnimations();
                                rvJobAppliction.setLayoutManager(linearLayoutManagerVertical);
                                if (jobApplicationDTOList.size() != 0) {
                                    rvJobAppliction.setVisibility(View.VISIBLE);
                                    llIcon.setVisibility(View.GONE);
                                    jobApplication_adapter = new JobApplication_adapter(jobApplicationDTOList, getActivity(), JobApplication.this);
                                    rvJobAppliction.setAdapter(jobApplication_adapter);
                                } else {
                                    rvJobAppliction.setVisibility(View.GONE);
                                    llIcon.setVisibility(View.VISIBLE);
                                }


                            } else {
                                rvJobAppliction.setVisibility(View.GONE);
                                llIcon.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Consts.RECRUITER_ID, sharedPrefrence.getRecruiterDTO(Consts.RECRUITER_DTO).data.getId());
                Log.e("value", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onResume() {
        super.onResume();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        Log.e("Runnable", "FIRST");
                                        if (NetworkManager.isConnectToInternet(getActivity())) {
                                            swipeRefreshLayout.setRefreshing(true);
                                            showPostedJobs();

                                        } else {
                                            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_connection));
                                        }
                                    }
                                }
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_image:
                Intent in = new Intent(getActivity(), Recruiter_PostJob_Activity.class);
                in.putExtra(Consts.FLAG, 1);
                startActivity(in);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        recruiterDashboardActivity = (RecruiterDashboardActivity) activity;


    }

    @Override
    public void onRefresh() {
        showPostedJobs();
    }
}
