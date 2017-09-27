package com.pixelnx.sam.jobportal.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.DTO.CommonDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.network.NetworkManager;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomButton;
import com.pixelnx.sam.jobportal.utils.CustomEdittext;
import com.pixelnx.sam.jobportal.utils.CustomTextview;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup radioGroup;
    private RadioButton RBSeeker, RBRecruiter;
    private CustomEdittext etEmail, etPassword;
    private CustomButton btnSubmit;
    private CustomTextview tvCreatnewaccount, tvForgotpass;
    private Context mContext;
    private SharedPrefrence prefrence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mContext = ForgotPasswordActivity.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        init();
    }

    private void init() {

        etEmail = (CustomEdittext) findViewById(R.id.etEmail);


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RBSeeker = (RadioButton) findViewById(R.id.RBSeeker);
        RBRecruiter = (RadioButton) findViewById(R.id.RBRecruiter);
        btnSubmit = (CustomButton) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                submitForm();
                break;

        }
    }

    public void submitForm() {
        if (!ValidateEmail()) {
            return;
        }  else if (!SelectType()) {
            return;
        } else {
            if (NetworkManager.isConnectToInternet(mContext)) {
                updatepass();

            } else {
                ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_connection));
            }
        }
    }


    public boolean ValidateEmail() {
        if (!ProjectUtils.IsEmailValidation(etEmail.getText().toString().trim())) {
            etEmail.setError(getResources().getString(R.string.val_email));
            etEmail.requestFocus();
            return false;
        }
        return true;
    }


    public boolean SelectType() {

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please select Type", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void updatepass() {
        if (RBSeeker.isChecked()) {
            forgotpass();
        }
        if (RBRecruiter.isChecked()) {
            forgotpass();
        }
    }

    public void forgotpass() {
        ProjectUtils.showProgressDialog(mContext, false, "Please wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.FORGOT_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtils.pauseProgressDialog();
                        Log.e("forgot_pass_response", response.toString());
                        try {
                            CommonDTO commonDTO = new CommonDTO();
                            commonDTO = new Gson().fromJson(response, CommonDTO.class);

                            if (commonDTO.isStatus()) {
                                ProjectUtils.showToast(ForgotPasswordActivity.this, commonDTO.getMessage());
                            } else {
                                ProjectUtils.showToast(ForgotPasswordActivity.this, commonDTO.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ProjectUtils.pauseProgressDialog();
                        Log.d("FORGOT_PASS_ERROR", "Error:" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if (RBRecruiter.isChecked()) {
                    params.put(Consts.ROLE, "1");

                } else if (RBSeeker.isChecked()) {
                    params.put(Consts.ROLE, "0");

                }

                params.put(Consts.EMAIL, ProjectUtils.getEditTextValue(etEmail));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }




}
