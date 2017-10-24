package com.pixelnx.sam.jobportal.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.DTO.ActiveJobDTO;
import com.pixelnx.sam.jobportal.DTO.CommonDTO;
import com.pixelnx.sam.jobportal.DTO.UserSeekerDTO;
import com.pixelnx.sam.jobportal.LoginActivity;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.network.NetworkManager;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomButton;
import com.pixelnx.sam.jobportal.utils.CustomTextview;
import com.pixelnx.sam.jobportal.utils.CustomTextviewBold;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FullDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPrefrence prefrence;
    private Context mContext;
    private CustomTextviewBold tvSpecialn;
    private CustomTextview tvOrganisation, tvExperience, tvLocation, tvSkills, tvJobposted, tvJobtype, tvDesignation, tvQualification, tvSpecialization, tvlastDOA, tvJobDescription, tvHiringProcess;
    private CustomButton btnApply;
    private ActiveJobDTO.Data data;
    private UserSeekerDTO userSeekerDTO;
    private ImageView ivBack;
    private ArrayList<String> process = new ArrayList<>();
    private ListView lvProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details);
        mContext = FullDetailsActivity.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        userSeekerDTO = prefrence.getUserDTO(Consts.SEEKER_DTO);
        if (getIntent().hasExtra(Consts.ACTIVE_JOBS_DTO)) {
            data = (ActiveJobDTO.Data) getIntent().getSerializableExtra(Consts.ACTIVE_JOBS_DTO);
        }
        init();

    }

    public void init() {
        tvSpecialn = (CustomTextviewBold) findViewById(R.id.tvSpecialn);
        tvOrganisation = (CustomTextview) findViewById(R.id.tvOrganisation);
        tvExperience = (CustomTextview) findViewById(R.id.tvExperience);
        tvLocation = (CustomTextview) findViewById(R.id.tvLocation);
        tvSkills = (CustomTextview) findViewById(R.id.tvSkills);
        tvJobposted = (CustomTextview) findViewById(R.id.tvJobposted);
        tvJobtype = (CustomTextview) findViewById(R.id.tvJobtype);
        tvDesignation = (CustomTextview) findViewById(R.id.tvDesignation);
        tvQualification = (CustomTextview) findViewById(R.id.tvQualification);
        tvSpecialization = (CustomTextview) findViewById(R.id.tvSpecialization);
        tvlastDOA = (CustomTextview) findViewById(R.id.tvlastDOA);
        tvJobDescription = (CustomTextview) findViewById(R.id.tvJobDescription);
        //  tvHiringProcess = (CustomTextview) findViewById(R.id.tvHiringProcess);
        lvProcess = (ListView) findViewById(R.id.lvProcess);

        ivBack = (ImageView) findViewById(R.id.ivBack);
        btnApply = (CustomButton) findViewById(R.id.btnApply);
        ivBack.setOnClickListener(this);
        btnApply.setOnClickListener(this);
        if (data.is_applied()) {
            btnApply.setVisibility(View.GONE);
        } else {
            btnApply.setVisibility(View.VISIBLE);
        }
        String yourString = data.getJob_discription().replaceAll("\\s+", " ");
        tvSpecialn.setText(data.getSpecialization());
        tvOrganisation.setText(data.getPosted_recruiter().getOrganisation_name());
        tvExperience.setText(data.getExperience());
        tvLocation.setText(data.getJob_location());
        tvSkills.setText(data.getSkills_required());
        ProjectUtils.dateFormate(tvJobposted, data.getCreated_at());
        tvJobtype.setText(data.getJob_type());
        tvDesignation.setText(data.getJob_by_roles());
        tvQualification.setText(data.getQualification());
        tvSpecialization.setText(data.getSpecialization());
        ProjectUtils.dateFormateOne(tvlastDOA, data.getLast_date());
        tvJobDescription.setText(yourString);
        process = data.getProcess();

        ArrayAdapter arrayAdapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, data.getProcess());
        lvProcess.setAdapter(arrayAdapter);
        //      tvHiringProcess.setText(process.get(0).toString() + "" + process.get(1).toString() + "" + process.get(2).toString()+""+process.get(3).toString()+""+process.get(4).toString());


    }

    public void applyOnJob() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.APPLY_JOB_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("APPLY_JOB", response.toString());
                        try {
                            CommonDTO commonDTO = new CommonDTO();
                            commonDTO = new Gson().fromJson(response, CommonDTO.class);

                            if (commonDTO.isStatus()) {
                                ProjectUtils.showToast(FullDetailsActivity.this, commonDTO.getMessage());
                                btnApply.setVisibility(View.GONE);
                            } else {

                                ProjectUtils.showToast(FullDetailsActivity.this, commonDTO.getMessage());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ProjectUtils.showToast(mContext, error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Consts.SEEKER_ID, userSeekerDTO.getData().getId());
                params.put(Consts.JOB_ID, data.getId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(FullDetailsActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnApply:
                if (prefrence.getBooleanValue(Consts.IS_REGISTER_SEEKER)) {
                    if (userSeekerDTO.data.getProfile_update().equals("1")) {
                        if (NetworkManager.isConnectToInternet(mContext)) {
                            applyOnJob();
                        } else {
                            ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_connection));
                        }
                    } else {
                        ProjectUtils.showToast(mContext, getResources().getString(R.string.apply_job));
                    }
                } else {
                    ProjectUtils.showAlertDialog(mContext, "", getResources().getString(R.string.guest_login), "Ok", "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(FullDetailsActivity.this, LoginActivity.class));
                            finish();
                            dialogInterface.dismiss();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });


                }

                break;
            case R.id.ivBack:
                finish();
                break;
        }
    }
}
