package com.pixelnx.sam.jobportal.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.DTO.CommonDTO;
import com.pixelnx.sam.jobportal.LoginActivity;
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


public class Recruiter_Reg_Fragment extends Fragment implements View.OnClickListener {
    private CustomEdittext etEmail, etOrgName, etMobile, etPassword, etCPassword;
    private CustomTextview tvAlreadyRegister;
    CustomButton SignupBtn;
    private SharedPrefrence prefrence;
    private static String TAG = Recruiter_Reg_Fragment.class.getSimpleName();
    private boolean isHide = false;
    private ImageView text_visible1, text_visible2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recruiter_signup, container, false);

        prefrence = SharedPrefrence.getInstance(getActivity());
        etOrgName = (CustomEdittext) view.findViewById(R.id.etOrgName);
        etEmail = (CustomEdittext) view.findViewById(R.id.etEmail);
        etMobile = (CustomEdittext)  view.findViewById(R.id.etMobile);
        etPassword =(CustomEdittext)  view.findViewById(R.id.etPassword);
        etCPassword = (CustomEdittext)  view.findViewById(R.id.etCPassword);

        String first = "JA POSSUI UMA CONTA ? ";
        String next = "<font color='#7062E9'><b>LOGIN</b></font>";
        tvAlreadyRegister = (CustomTextview) view.findViewById(R.id.tvAlreadyRegister);
        tvAlreadyRegister.setText(Html.fromHtml(first + next));
        tvAlreadyRegister.setOnClickListener(this);
        SignupBtn = (CustomButton) view.findViewById(R.id.SignupBtn);
        SignupBtn.setOnClickListener(this);

        text_visible1 = (ImageView) view.findViewById(R.id.text_visible1);
        text_visible1.setOnClickListener(this);
        text_visible2 = (ImageView) view.findViewById(R.id.text_visible2);
        text_visible2.setOnClickListener(this);
        return view;
    }

    @Override


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SignupBtn:
                submitForm();
                break;
            case R.id.tvAlreadyRegister:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.text_visible1:
                if (isHide) {
                    text_visible1.setImageResource(R.drawable.eye);
                    etPassword.setTransformationMethod(null);
                    etPassword.setSelection(etPassword.getText().length());
                    isHide = false;
                } else {
                    text_visible1.setImageResource(R.drawable.eye_close);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassword.setSelection(etPassword.getText().length());
                    isHide = true;
                }


                break;
            case R.id.text_visible2:
                if (isHide) {
                    text_visible2.setImageResource(R.drawable.eye);
                    etCPassword.setTransformationMethod(null);
                    etCPassword.setSelection(etCPassword.getText().length());
                    isHide = false;
                } else {
                    text_visible2.setImageResource(R.drawable.eye_close);
                    etCPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etCPassword.setSelection(etCPassword.getText().length());
                    isHide = true;
                }


                break;
        }
    }

    public void submitForm() {
        if (!ValidateEmail()) {
            return;
        } else if (!ValidateName()) {
            return;
        } else if (!validateMobile()) {
            return;
        } else if (!validatePassword()) {
            return;
        } else {
            if (NetworkManager.isConnectToInternet(getActivity())) {
                checkpass();
            } else {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_connection));
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

    public boolean ValidateName() {
        if (!ProjectUtils.IsEditTextValidation(etOrgName)) {
            etOrgName.setError(getResources().getString(R.string.val_name));
            etOrgName.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validateMobile() {
        if (!ProjectUtils.IsMobleValidation(etMobile.getText().toString().trim())) {
            etMobile.setError(getResources().getString(R.string.val_phone));
            etMobile.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validatePassword() {
        if (etPassword.getText().toString().trim().equalsIgnoreCase("")) {
            etPassword.setError(getResources().getString(R.string.val_pass));
            etPassword.requestFocus();
            return false;
        } else {
            if (!ProjectUtils.IsPasswordValidation(etPassword.getText().toString().trim())) {
                etPassword.setError(getResources().getString(R.string.val_pass_digit));
                etPassword.requestFocus();
                return false;
            } else {
                return true;
            }
        }
    }

    public void checkpass() {

        if (etPassword.getText().toString().trim().equals("")) {
            etPassword.setError(getString(R.string.pwd_required_validation));
        } else if (etCPassword.getText().toString().trim().equals("")) {
            etCPassword.setError(getString(R.string.confirm_pwd_required_validation));
        } else if (!etPassword.getText().toString().trim().equals(etCPassword.getText().toString().trim())) {
            etCPassword.setError(getString(R.string.confirm_pwd_validation));
        } else {
            etCPassword.setError(null);//removes error
            etCPassword.clearFocus();    //clear focus from edittext
            if (NetworkManager.isConnectToInternet(getActivity())) {
                registerRecruiter();

            } else {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_connection));
            }
        }

    }

    public void registerRecruiter() {
        ProjectUtils.showProgressDialog(getActivity(), false, "Please wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.SIGNUP_API_RECRUITER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtils.pauseProgressDialog();
                        Log.e(TAG, "response:" + response);

                        CommonDTO commonDTO = new CommonDTO();
                        commonDTO = new Gson().fromJson(response, CommonDTO.class);
                        if (commonDTO.isStatus()) {
                            ProjectUtils.showToast(getActivity(), commonDTO.getMessage());
                            getActivity().finish();

                        } else {
                            ProjectUtils.showToast(getActivity(), commonDTO.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ProjectUtils.pauseProgressDialog();
                        Log.e("error_requter", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Consts.ORGANISATION_NAME, ProjectUtils.getEditTextValue(etOrgName));
                params.put(Consts.RECRUITER_EMAIL, ProjectUtils.getEditTextValue(etEmail));
                params.put(Consts.PASSWORD, ProjectUtils.getEditTextValue(etPassword));
                params.put(Consts.RECRUITER_MOBILE_NO, ProjectUtils.getEditTextValue(etMobile));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
