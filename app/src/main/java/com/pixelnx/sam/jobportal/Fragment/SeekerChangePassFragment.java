package com.pixelnx.sam.jobportal.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.Activity.SeekerDashboardActivity;
import com.pixelnx.sam.jobportal.DTO.CommonDTO;
import com.pixelnx.sam.jobportal.DTO.UserSeekerDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.network.NetworkManager;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomButton;
import com.pixelnx.sam.jobportal.utils.CustomEdittext;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.util.HashMap;
import java.util.Map;


public class SeekerChangePassFragment extends Fragment implements View.OnClickListener {
    private CustomEdittext etOldPassword, etNewPassword, etConfirmNewPassword;
    CustomButton UpdateBtn;
    private SharedPrefrence prefrence;
    UserSeekerDTO userSeekerDTO;
    View view;
    private static String TAG = SeekerChangePassFragment.class.getSimpleName();
SeekerDashboardActivity seekerDashboardActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_seeker_change_pass, container, false);
        prefrence = SharedPrefrence.getInstance(getActivity());

        seekerDashboardActivity.tvJobs.setVisibility(View.VISIBLE);
        seekerDashboardActivity.tvJobs.setText(getResources().getString(R.string.change_pass));
        seekerDashboardActivity.search_icon.setVisibility(View.INVISIBLE);
        seekerDashboardActivity.filter_icon.setVisibility(View.INVISIBLE);

        userSeekerDTO = prefrence.getUserDTO(Consts.SEEKER_DTO);

        etOldPassword = (CustomEdittext) view.findViewById(R.id.etOldPassword);
        etNewPassword = (CustomEdittext) view.findViewById(R.id.etNewPassword);
        etConfirmNewPassword = (CustomEdittext) view.findViewById(R.id.etConfirmNewPassword);
        UpdateBtn = (CustomButton) view.findViewById(R.id.UpdateBtn);
        UpdateBtn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UpdateBtn:
                Submit();
                break;
        }
    }


    private void Submit() {
        if (!passwordValidation()) {
            return;
        } else if (!checkpass()) {
            return;
        } else {
            if (NetworkManager.isConnectToInternet(getActivity())) {
                updatePassword();
            } else {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_connection));
            }

        }
    }


    public boolean passwordValidation() {
        if (!ProjectUtils.IsPasswordValidation(etOldPassword.getText().toString().trim())) {
            etOldPassword.setError(getResources().getString(R.string.val_pass_c));
            etOldPassword.requestFocus();
            return false;
        } else if (!ProjectUtils.IsPasswordValidation(etNewPassword.getText().toString().trim())) {
            etNewPassword.setError(getResources().getString(R.string.val_pass_c));
            etNewPassword.requestFocus();
            return false;
        } else
            return true;

    }

    private boolean checkpass() {
        if (etNewPassword.getText().toString().trim().equals("")) {
            etNewPassword.setError(getResources().getString(R.string.val_new_pas));
            return false;
        } else if (etConfirmNewPassword.getText().toString().trim().equals("")) {
            etConfirmNewPassword.setError(getResources().getString(R.string.val_c_pas));
            return false;
        } else if (!etNewPassword.getText().toString().trim().equals(etConfirmNewPassword.getText().toString().trim())) {
            etConfirmNewPassword.setError(getResources().getString(R.string.val_n_c_pas));
            return false;
        }
        return true;
    }



    public void updatePassword() {
        ProjectUtils.showProgressDialog(getActivity(), false, "Please wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.SEEKER_CHANGE_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtils.pauseProgressDialog();
                        Log.e(TAG, "response: " + response);

                        CommonDTO commonDTO = new CommonDTO();
                        commonDTO = new Gson().fromJson(response, CommonDTO.class);
                        if (commonDTO.isStatus()) {
                            ProjectUtils.showToast(getActivity(), commonDTO.getMessage());

                        } else {
                            ProjectUtils.showToast(getActivity(), commonDTO.getMessage());
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ProjectUtils.pauseProgressDialog();
                        Log.e("error_on_changepass_api", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Consts.SEEKER_ID, userSeekerDTO.getData().getId());
                params.put(Consts.OLD_PASSWORD, ProjectUtils.getEditTextValue(etOldPassword));
                params.put(Consts.NEW_PASSWORD, ProjectUtils.getEditTextValue(etNewPassword));
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        seekerDashboardActivity = (SeekerDashboardActivity) activity;

    }
}
