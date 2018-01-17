package com.pixelnx.sam.jobportal.Fragment;

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

public class Seeker_Reg_Fragment extends Fragment implements View.OnClickListener {
    private CustomTextview tvAlreadyRegister;
    private CustomEdittext etEmail, etName, etMobile, etPassword, etCPassword;
    CustomButton SignupBtn;
    private boolean isHide = false;
    private SharedPrefrence prefrence;

    private static String TAG = Seeker_Reg_Fragment.class.getSimpleName();
    private ImageView text_visible1, text_visible2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seeker_signup, container, false);

        prefrence = SharedPrefrence.getInstance(getActivity());

        etEmail = (CustomEdittext) view.findViewById(R.id.etEmail);
        etName = (CustomEdittext) view.findViewById(R.id.etName);
        etMobile = (CustomEdittext) view.findViewById(R.id.etMobile);
        etPassword = (CustomEdittext) view.findViewById(R.id.etPassword);
        etCPassword = (CustomEdittext) view.findViewById(R.id.etCPassword);

        String first = "ALREADY HAVE AN ACCOUNT ? ";
        String next = "<font color='#7062E9'><b> LOGIN <b></font>";
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
                getActivity().finish();
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
        if (!ProjectUtils.IsEditTextValidation(etName)) {
            etName.setError(getResources().getString(R.string.val_name));
            etName.requestFocus();
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
                registerSeeker();
            } else {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_connection));
            }
        }

    }

    public void registerSeeker() {
        ProjectUtils.showProgressDialog(getActivity(), false, "Please wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.SIGNUP_API_SEEKER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtils.pauseProgressDialog();
                        Log.e(TAG, "response: " + response);

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
                        Log.e("error_seeker", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Consts.EMAIL, ProjectUtils.getEditTextValue(etEmail));
                params.put(Consts.FULL_NAME, ProjectUtils.getEditTextValue(etName));
                params.put(Consts.MOBILE_NO, ProjectUtils.getEditTextValue(etMobile));
                params.put(Consts.PASSWORD, ProjectUtils.getEditTextValue(etPassword));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
// {"code":500,"status":false,"message":"SQLSTATE[230
// 00]: Integrity constraint violation: 1062 Duplicate entry '8109973009' for key 'seeker_mobile_no_unique' (SQL: insert into `seeker` (`full_name`, `email`, `mobile_no`, `password`, `token`, `updated_at`, `created_at`) values (varun, varun@gm.com, 8109973009, $2y$10$47c\/ur1c1GrDy6Mb3J3P3O48ow97BHd8v3xx2Rwz4tT2IDt5Bfagy, C0HELe0pcb7vVCILMWMwxeQQswNyElBmbIiBvT2h, 2017-08-17 07:12:09, 2017-08-17 07:12:09))"}