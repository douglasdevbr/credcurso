package com.pixelnx.sam.jobportal.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.DTO.SingleJobDTO;
import com.pixelnx.sam.jobportal.LoginActivity;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomButton;
import com.pixelnx.sam.jobportal.utils.CustomTextHeader;
import com.pixelnx.sam.jobportal.utils.CustomTextview;
import com.pixelnx.sam.jobportal.utils.CustomTextviewBold;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SingleJob extends AppCompatActivity implements View.OnClickListener {
    private CustomTextviewBold tvName, tvResume;
    private CustomTextview tvExperience, tvLocation, tvSkills, tvJobApplied, tvJobtype, tvDesignation, tvQualification, tvlastDOAPP, tvJobDescription, tvSpecialization;
    // public JobsViewDTO.Data.Profile profile;
    private ImageView ivBack;
    private CustomTextHeader tvHeader;
    private SingleJobDTO.Data data;
    private CustomButton btnCallNow;
    private SingleJobDTO singleJobDTO;
    private String seeker_id, jobid;
    private Context mContext;
    private ProgressDialog pDialog;
    ImageView my_image;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    static String storagestate = Environment.getExternalStorageState();
    private static File imageFilepath;
    String extensionResume = "";


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
        tvResume = (CustomTextviewBold) findViewById(R.id.tvResume);
        tvJobDescription = (CustomTextview) findViewById(R.id.tvJobDescription);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        btnCallNow = (CustomButton) findViewById(R.id.btnCallNow);


        btnCallNow.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvResume.setOnClickListener(this);

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
        if (profile.getWork_experience().equalsIgnoreCase("Fresher")) {
            tvExperience.setText(profile.getWork_experience());
            tvSpecialization.setText("N/A");
            tvDesignation.setText("N/A");
            tvSkills.setText("N/A");

        } else {
            String year = profile.getExperience_in_year().replace(" year", "");
            String month = profile.getExperience_in_months().replace(" month", "");
            tvExperience.setText(year + "." + month + " Year");
            tvDesignation.setText(profile.getSeeker_role_type());
            tvSpecialization.setText(profile.getSeeker_specialization());
            tvSkills.setText(profile.getSeeker_specialization());


        }

        if (singleJobDTO.getData().getProfile().getResume().equals("")) {
            tvResume.setVisibility(View.GONE);
        }

        tvJobtype.setText(profile.getSeeker_job_type());
        tvLocation.setText(profile.getSeeker_prefered_location());

        //  tvJobApplied.setText(profile.getSeeker_apply_date());
        tvQualification.setText(profile.getSeeker_qulification());


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
            case R.id.tvResume:
                ProjectUtils.showAlertDialog(mContext, "Please Confirm?", getResources().getString(R.string.download_dialog), "View", "Download", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in = new Intent(mContext, PdfView.class);
                        in.putExtra(Consts.SINGLE_JOB_DTO, singleJobDTO);
                        startActivity(in);
                        dialogInterface.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String filenameArray[] = singleJobDTO.getData().getProfile().getResume().split("\\.");
                        extensionResume = filenameArray[filenameArray.length - 1];
                        new DownloadFileFromURL().execute(singleJobDTO.getData().getProfile().getResume());
                        dialogInterface.dismiss();
                    }
                });

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

    /**
     * Background Async Task to download file
     */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);

        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();
                File folder = null;


                String root = Environment.getExternalStorageDirectory().toString();
                folder = new File(root, Consts.JOB_PORTAL);

                if (!folder.exists()) {
                    folder.mkdir();
                }

                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());
//getting filepath
                imageFilepath = new File(folder.getPath() + File.separator + singleJobDTO.getData().getProfile().getId()
                        + timestamp + "." + extensionResume);


                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                OutputStream output = new FileOutputStream(imageFilepath);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }


            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));


            super.onProgressUpdate(progress);
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);

            ProjectUtils.showSweetDialog(mContext, "Download Successfully", "", "Ok", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();


                }
            }, SweetAlertDialog.SUCCESS_TYPE);


        }

    }

    /**
     * Showing Dialog
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(false);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }


}

