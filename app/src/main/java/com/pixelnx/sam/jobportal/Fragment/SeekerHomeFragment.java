package com.pixelnx.sam.jobportal.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.Activity.FilterActivity;
import com.pixelnx.sam.jobportal.Activity.SeekerDashboardActivity;
import com.pixelnx.sam.jobportal.Adapter.AdapterDashboard;
import com.pixelnx.sam.jobportal.DTO.ActiveJobDTO;
import com.pixelnx.sam.jobportal.DTO.DummyFilterDTO;
import com.pixelnx.sam.jobportal.DTO.UserSeekerDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.network.NetworkManager;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomEdittext;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class SeekerHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerview = null;
    private AdapterDashboard adapterDashboard;
    private ActiveJobDTO activeJobDTO;
    private ArrayList<ActiveJobDTO.Data> dataArrayList;
    private LinearLayoutManager linearLayoutManagerVertical;
    private ArrayList<String> process = new ArrayList<>();
    View view;
    private SharedPrefrence prefrence;
    private UserSeekerDTO userSeekerDTO;
    SeekerDashboardActivity seekerDashboardActivity;
    private LinearLayout searchLayout;
    private CustomEdittext etSearch;
    private ImageView ivSearch;
    InputMethodManager inputManager;
    private LinearLayout llIcon;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String id = "";
    HashMap<Integer, ArrayList<DummyFilterDTO>> map;
    private ArrayList<DummyFilterDTO> dummyFilterList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        prefrence = SharedPrefrence.getInstance(getActivity());
        map = ProjectUtils.map;
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        searchLayout = (LinearLayout) view.findViewById(R.id.searchLayout);
        etSearch = (CustomEdittext) view.findViewById(R.id.etSearch);
        ivSearch = (ImageView) view.findViewById(R.id.ivSearch);
        llIcon = (LinearLayout) view.findViewById(R.id.llIcon);
        if (prefrence.getBooleanValue(Consts.IS_REGISTER_SEEKER)) {
            userSeekerDTO = prefrence.getUserDTO(Consts.SEEKER_DTO);
            id = userSeekerDTO.getData().getId();
        } else {
            id = "guest";
        }

        seekerDashboardActivity.tvJobs.setVisibility(View.VISIBLE);
        seekerDashboardActivity.tvJobs.setText("Jobs");
        seekerDashboardActivity.search_icon.setVisibility(View.VISIBLE);
        seekerDashboardActivity.filter_icon.setVisibility(View.VISIBLE);

        seekerDashboardActivity.search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchLayout.getVisibility() == View.VISIBLE) {
                    etSearch.setText("");
                    seekerDashboardActivity.search_icon.setImageResource(R.drawable.search);
                    searchLayout.setVisibility(View.GONE);
                    showjobs();


                } else {
                    seekerDashboardActivity.search_icon.setImageResource(R.drawable.close);
                    searchLayout.setVisibility(View.VISIBLE);
                }

            }
        });
        seekerDashboardActivity.filter_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FilterActivity.class));
                map.clear();
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                submitForm();

            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    submitForm();

                    return true;
                }
                return false;
            }
        });


        return view;
    }

    public void submitForm() {
        if (!validateSearch()) {
            return;
        } else {
            showjobs();

        }
    }

    public boolean validateSearch() {
        if (etSearch.getText().toString().trim().length() <= 0) {
            etSearch.setError("please enter value");
            etSearch.requestFocus();
            return false;
        } else {
            return true;
        }
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
                                            showjobs();

                                        } else {
                                            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_connection));
                                        }
                                    }
                                }
        );
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        seekerDashboardActivity = (SeekerDashboardActivity) activity;

    }

    @Override
    public void onRefresh() {
        showjobs();
    }

    public String getUpdateJson() {
        map = ProjectUtils.map;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(Consts.SEEKER_ID, id);
            jsonObject.put(Consts.VALUE, ProjectUtils.getEditTextValue(etSearch));

            JSONArray job_type = new JSONArray();
            JSONArray job_location = new JSONArray();
            JSONArray specialization = new JSONArray();
            JSONArray qualification = new JSONArray();
            JSONArray job_by_roles = new JSONArray();
            JSONArray area_of_sector = new JSONArray();
            JSONArray experience = new JSONArray();
            if (!map.isEmpty()) {


                if (map.containsKey(0)) {
                    dummyFilterList = map.get(0);
                    jsonObject.put(Consts.JOB_TYPE, getJsonArray(dummyFilterList));


                }
                if (map.containsKey(1)) {
                    dummyFilterList = map.get(1);
                    jsonObject.put(Consts.JOB_LOCATION, getJsonArray(dummyFilterList));


                }
                if (map.containsKey(2)) {
                    dummyFilterList = map.get(2);
                    jsonObject.put(Consts.JOB_BY_ROLES, getJsonArray(dummyFilterList));


                }

                if (map.containsKey(3)) {
                    dummyFilterList = map.get(3);
                    jsonObject.put(Consts.QUALIFICATION, getJsonArray(dummyFilterList));


                }
                if (map.containsKey(4)) {
                    dummyFilterList = map.get(4);
                    jsonObject.put(Consts.EXPERIENCE, getJsonArray(dummyFilterList));
                }
                if (map.containsKey(5)) {
                    dummyFilterList = map.get(5);
                    jsonObject.put(Consts.SPECIALIZATION, getJsonArray(dummyFilterList));


                }


                if (map.containsKey(6)) {
                    dummyFilterList = map.get(6);
                    jsonObject.put(Consts.AREA_OF_SECTOR, getJsonArray(dummyFilterList));


                }
            } else {
                jsonObject.put(Consts.JOB_LOCATION, job_location);
                jsonObject.put(Consts.SPECIALIZATION, specialization);
                jsonObject.put(Consts.QUALIFICATION, qualification);
                jsonObject.put(Consts.JOB_BY_ROLES, job_by_roles);
                jsonObject.put(Consts.AREA_OF_SECTOR, area_of_sector);
                jsonObject.put(Consts.EXPERIENCE, experience);
                jsonObject.put(Consts.JOB_TYPE, job_type);
            }


            Log.e("update_json", jsonObject.toString());

            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject().toString();
        }
    }

    public JSONArray getJsonArray(ArrayList<DummyFilterDTO> dummyList) {
        JSONArray roleArray = new JSONArray();

        try {
            for (int i = 0; i < dummyList.size(); i++) {
                if (dummyList.get(i).isChecked()) {
                    roleArray.put(dummyList.get(i).getName());

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roleArray;
    }


    public void showjobs() {


        JsonObjectRequest request_json = null;
        try {
            request_json = new JsonObjectRequest(Request.Method.POST, Consts.BASE_URL + Consts.ACTIVE_JOBS_API, new JSONObject(getUpdateJson()),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("JOB", response.toString());
                            swipeRefreshLayout.setRefreshing(false);
                            activeJobDTO = new Gson().fromJson(String.valueOf(response), ActiveJobDTO.class);
                            if (activeJobDTO.isStatus()) {
                                dataArrayList = activeJobDTO.getData();
                                linearLayoutManagerVertical = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                recyclerview.setLayoutManager(linearLayoutManagerVertical);
                                recyclerview.setItemAnimator(new DefaultItemAnimator());
                                if (dataArrayList.size() != 0) {
                                    recyclerview.setVisibility(View.VISIBLE);
                                    llIcon.setVisibility(View.GONE);
                                    adapterDashboard = new AdapterDashboard(dataArrayList, getActivity(), SeekerHomeFragment.this);
                                    recyclerview.setAdapter(adapterDashboard);
                                } else {
                                    recyclerview.setVisibility(View.GONE);
                                    llIcon.setVisibility(View.VISIBLE);
                                }

                            } else {
                                ProjectUtils.showToast(getActivity(), activeJobDTO.getMessage());
                                recyclerview.setVisibility(View.GONE);
                                llIcon.setVisibility(View.VISIBLE);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request_json);
    }

    public void showData() {

        if (map.containsKey(0)) {
            dummyFilterList = map.get(0);

        } else if (map.containsKey(1)) {
            dummyFilterList = map.get(1);


        } else if (map.containsKey(2)) {
            dummyFilterList = map.get(2);

        } else if (map.containsKey(3)) {
            dummyFilterList = map.get(3);


        } else if (map.containsKey(4)) {
            dummyFilterList = map.get(4);


        } else if (map.containsKey(5)) {
            dummyFilterList = map.get(5);


        } else if (map.containsKey(6)) {
            dummyFilterList = map.get(6);
        }
    }


}
