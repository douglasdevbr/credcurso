package com.pixelnx.sam.jobportal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
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
import com.pixelnx.sam.jobportal.Activity.ForgotPasswordActivity;
import com.pixelnx.sam.jobportal.Activity.RecruiterDashboardActivity;
import com.pixelnx.sam.jobportal.Activity.SeekerDashboardActivity;
import com.pixelnx.sam.jobportal.DTO.UserRecruiterDTO;
import com.pixelnx.sam.jobportal.DTO.UserSeekerDTO;
import com.pixelnx.sam.jobportal.network.NetworkManager;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomButton;
import com.pixelnx.sam.jobportal.utils.CustomEdittext;
import com.pixelnx.sam.jobportal.utils.CustomTextview;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup radioGroup;
    private RadioButton RBSeeker, RBRecruiter;
    private CustomEdittext etEmail, etPassword;
    private CustomButton loginBtn;
    private CustomTextview tvCreatnewaccount, tvForgotpass, tvGuestLogin;
    private Context mContext;
    private SharedPrefrence prefrence;
    private TelephonyManager telephonyManager;
    SharedPreferences userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        prefrence = SharedPrefrence.getInstance(mContext);

        userDetails = LoginActivity.this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Log.e("tokensss", userDetails.getString(Consts.TOKAN, ""));
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Log.e("LOGIN_ID", "my id: " + telephonyManager.getDeviceId());
        init();
    }

    private void init() {

        etEmail = (CustomEdittext) findViewById(R.id.etEmail);
        etPassword = (CustomEdittext) findViewById(R.id.etPassword);

        String first = "Ainda nao possui conta? ";
        String next = "<font color='#7062E9'><b>CADASTRE-SE</b></font>";
        String guest = "<font color='#7062E9'><u><b>VAGAS DISPONIVEIS</b></u></font>";

        tvForgotpass = (CustomTextview) findViewById(R.id.tvForgotpass);
        tvCreatnewaccount = (CustomTextview) findViewById(R.id.tvCreatnewaccount);
        tvGuestLogin = (CustomTextview) findViewById(R.id.tvGuestLogin);
        tvCreatnewaccount.setText(Html.fromHtml(first + next));
        tvGuestLogin.setText(Html.fromHtml(guest));

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RBSeeker = (RadioButton) findViewById(R.id.RBSeeker);
        RBRecruiter = (RadioButton) findViewById(R.id.RBRecruiter);
        loginBtn = (CustomButton) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        tvForgotpass.setOnClickListener(this);
        tvCreatnewaccount.setOnClickListener(this);
        tvGuestLogin.setOnClickListener(this);
        RBSeeker.setOnClickListener(this);
        RBRecruiter.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                submitForm();
                break;
            case R.id.tvForgotpass:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;
            case R.id.tvCreatnewaccount:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.tvGuestLogin:
                Intent in = new Intent(mContext, SeekerDashboardActivity.class);
                in.putExtra(Consts.TAG_GUEST, 2);
                startActivity(in);
                finish();
                break;
            case R.id.RBSeeker:
                tvGuestLogin.setVisibility(View.VISIBLE);
                break;
            case R.id.RBRecruiter:
                tvGuestLogin.setVisibility(View.GONE);
                break;
        }
    }

    public void submitForm() {
        if (!ValidateEmail()) {
            return;
        } else if (!validatePassword()) {
            return;
        } else if (!SelectType()) {
            return;
        } else {
            login();
        }
    }


    public boolean ValidateEmail() {
        if (!ProjectUtils.IsEmailValidation(etEmail.getText().toString().trim())) {
            etEmail.setError("Por favor use  um email válido");
            etEmail.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validatePassword() {
        if (etPassword.getText().toString().trim().equalsIgnoreCase("")) {
            etPassword.setError("Insira sua senha");
            etPassword.requestFocus();
            return false;
        } else {
            if (!ProjectUtils.IsPasswordValidation(etPassword.getText().toString().trim())) {
                etPassword.setError("Insira uma senha de 6 digitos");
                etPassword.requestFocus();
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean SelectType() {

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Selecione um tipo", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public void login() {
        if (RBSeeker.isChecked()) {
            if (NetworkManager.isConnectToInternet(mContext)) {
                seekerlogin();
            } else {
                ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_connection));
            }

        }
        if (RBRecruiter.isChecked()) {
            if (NetworkManager.isConnectToInternet(mContext)) {
                recruiterlogin();
            } else {
                ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_connection));
            }
        }
    }

    public void seekerlogin() {
        ProjectUtils.showProgressDialog(mContext, false, "Aguarde...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.LOGIN_API_SEEKER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtils.pauseProgressDialog();
                        Log.e("Seeker_login", response.toString());
                        try {
                            UserSeekerDTO userSeekerDTO = new UserSeekerDTO();
                            userSeekerDTO = new Gson().fromJson(response, UserSeekerDTO.class);
                            prefrence.setUserDTO(userSeekerDTO, Consts.SEEKER_DTO);
                            if (userSeekerDTO.isStatus()) {
                                prefrence.setBooleanValue(Consts.IS_REGISTER_SEEKER, true);
                                prefrence.setBooleanValue(Consts.IS_REGISTER_RECRUITER, false);
                                ProjectUtils.showToast(mContext, userSeekerDTO.getMessage());
                                Intent in = new Intent(mContext, SeekerDashboardActivity.class);
                                in.putExtra(Consts.TAG_GUEST, 1);
                                startActivity(in);
                                finish();
                            } else {
                                prefrence.setBooleanValue(Consts.IS_REGISTER_SEEKER, false);
                                prefrence.setBooleanValue(Consts.IS_REGISTER_RECRUITER, false);
                                ProjectUtils.showToast(mContext, userSeekerDTO.getMessage());
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
                        Log.d("LOGIN_ERROR_SEEKER", "Login Error:" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Consts.VALUE, ProjectUtils.getEditTextValue(etEmail));
                params.put(Consts.PASSWORD, ProjectUtils.getEditTextValue(etPassword));
                params.put(Consts.DEVICE_ID, "" + telephonyManager.getDeviceId());
                params.put(Consts.DEVICE_TOKEN, userDetails.getString(Consts.TOKAN, ""));
                params.put(Consts.DEVICE_TYPE, "Android");
                Log.e("seeker_login", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void recruiterlogin() {
        ProjectUtils.showProgressDialog(mContext, false, "Please wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.LOGIN_API_RECRUITER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("recruiter_login", response.toString());
                            ProjectUtils.pauseProgressDialog();
                            UserRecruiterDTO userRecruiterDTO = new UserRecruiterDTO();
                            userRecruiterDTO = new Gson().fromJson(response, UserRecruiterDTO.class);
                            prefrence.setRecruiterDTO(userRecruiterDTO, Consts.RECRUITER_DTO);
                            if (userRecruiterDTO.isStatus()) {
                                prefrence.setBooleanValue(Consts.IS_REGISTER_RECRUITER, true);
                                prefrence.setBooleanValue(Consts.IS_REGISTER_SEEKER, false);
                                ProjectUtils.showToast(mContext, userRecruiterDTO.getMessage());
                                startActivity(new Intent(mContext, RecruiterDashboardActivity.class));
                                finish();
                            } else {
                                ProjectUtils.showToast(mContext, userRecruiterDTO.getMessage());
                                prefrence.setBooleanValue(Consts.IS_REGISTER_RECRUITER, false);
                                prefrence.setBooleanValue(Consts.IS_REGISTER_SEEKER, false);
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
                        Log.d("LOGIN_ERROR_REC", "Login Error:" + error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Consts.VALUE_RECRUITER, ProjectUtils.getEditTextValue(etEmail));
                params.put(Consts.PASSWORD, ProjectUtils.getEditTextValue(etPassword));
                params.put(Consts.DEVICE_ID, "" + telephonyManager.getDeviceId());
                params.put(Consts.DEVICE_TOKEN, userDetails.getString(Consts.TOKAN, ""));
                params.put(Consts.DEVICE_TYPE, "Android");
                Log.e("recuter_login", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        clickClose();
    }

    public void clickClose() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.logo)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.msg_dialog))
                .setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}








