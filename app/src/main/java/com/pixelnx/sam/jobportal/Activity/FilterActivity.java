package com.pixelnx.sam.jobportal.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.pixelnx.sam.jobportal.Adapter.AdapterFilterCategory;
import com.pixelnx.sam.jobportal.Adapter.AdapterFilterItem;
import com.pixelnx.sam.jobportal.DTO.DummyFilterDTO;
import com.pixelnx.sam.jobportal.DTO.GeneralDTO;
import com.pixelnx.sam.jobportal.DTO.YearDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private ListView lvCategory, lvItem;
    private GeneralDTO generalDTO;
    private ImageView ivFilter, ivBack;
    private ArrayList<DummyFilterDTO> dummyFilterList;

    private ArrayList<String> categoryList;
    private ArrayList<GeneralDTO.Locations> locationsList = new ArrayList<>();
    private ArrayList<GeneralDTO.Area_of_sectors> area_of_sectorsList = new ArrayList<>();
    private ArrayList<GeneralDTO.Qualifications> qualificationsList = new ArrayList<>();
    private ArrayList<GeneralDTO.Job_types> job_typesList = new ArrayList<>();
    private ArrayList<GeneralDTO.Job_by_roles> job_by_roles_List = new ArrayList<>();
    private ArrayList<GeneralDTO.Specialization> specializationList = new ArrayList<>();
    private List<YearDTO> exprinceYearDTOList;

    private AdapterFilterItem adapterFilterItem;
    private AdapterFilterCategory adapterFilterCategory;
    private ArrayAdapter<GeneralDTO.Locations> location_Adapter;
    private ArrayAdapter<GeneralDTO.Area_of_sectors> aos_Adapter;
    private ArrayAdapter<GeneralDTO.Qualifications> qualification_Adapter;
    private ArrayAdapter<GeneralDTO.Job_types> job_type_Adapter;
    private ArrayAdapter<GeneralDTO.Job_by_roles> job_by_roles_Adapter;
    private ArrayAdapter<GeneralDTO.Specialization> specialization_Adapter;
    private ArrayAdapter<YearDTO> exprinceYear_adapter;
    HashMap<Integer, ArrayList<DummyFilterDTO>> map;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mContext = FilterActivity.this;
        map = ProjectUtils.map;
        init();
    }

    public void init() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivFilter = (ImageView) findViewById(R.id.ivFilter);
        ivBack.setOnClickListener(this);
        ivFilter.setOnClickListener(this);

        lvCategory = (ListView) findViewById(R.id.lvCategory);
        lvItem = (ListView) findViewById(R.id.lvItem);

        categoryList = new ArrayList<>();
        categoryList.add("Job Type");
        categoryList.add("Job Location");
        categoryList.add("Designation");
        categoryList.add("Qualification");
        categoryList.add("Experience");
        categoryList.add("Specialization");
        categoryList.add("Area of Sector");


        exprinceYearDTOList = new ArrayList<>();
        exprinceYearDTOList.add(new YearDTO("1", "0 year"));
        exprinceYearDTOList.add(new YearDTO("2", "1 year"));
        exprinceYearDTOList.add(new YearDTO("3", "2 year"));
        exprinceYearDTOList.add(new YearDTO("4", "3 year"));
        exprinceYearDTOList.add(new YearDTO("5", "4 year"));
        exprinceYearDTOList.add(new YearDTO("6", "5 year"));
        exprinceYearDTOList.add(new YearDTO("7", "6 year"));
        exprinceYearDTOList.add(new YearDTO("8", "7 year"));
        exprinceYearDTOList.add(new YearDTO("9", "8 year"));
        exprinceYearDTOList.add(new YearDTO("10", "9 year"));
        exprinceYearDTOList.add(new YearDTO("11", "10 year"));
        exprinceYearDTOList.add(new YearDTO("12", "11 year"));
        exprinceYearDTOList.add(new YearDTO("13", "12+ year"));


        adapterFilterCategory = new AdapterFilterCategory(mContext, categoryList, FilterActivity.this);
        lvCategory.setAdapter(adapterFilterCategory);

        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //showData(position);

            }
        });
        if (map.containsKey(0)) {
            dummyFilterList = map.get(0);
            adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
            lvItem.setAdapter(adapterFilterItem);
        } else {
            getData();

        }

    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Consts.BASE_URL + Consts.GENERAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            generalDTO = new Gson().fromJson(response, GeneralDTO.class);
                            savetomap();
                            if (map.containsKey(0)) {
                                dummyFilterList = map.get(0);
                                shortlistlowtohigh();
                                adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
                                lvItem.setAdapter(adapterFilterItem);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                    }
                }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(FilterActivity.this);
        requestQueue.add(stringRequest);

    }

    public void showData(int pos) {
        if (pos == 0) {

            if (map.containsKey(0)) {
                dummyFilterList = map.get(0);
                shortlistlowtohigh();
                adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
                lvItem.setAdapter(adapterFilterItem);

            }
        } else if (pos == 1) {
            if (map.containsKey(1)) {
                dummyFilterList = map.get(1);
                shortlistlowtohigh();
                adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
                lvItem.setAdapter(adapterFilterItem);

            }
        } else if (pos == 2) {
            if (map.containsKey(2)) {
                dummyFilterList = map.get(2);
                shortlistlowtohigh();
                adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
                lvItem.setAdapter(adapterFilterItem);
            }
        } else if (pos == 3) {
            if (map.containsKey(3)) {
                dummyFilterList = map.get(3);
                shortlistlowtohigh();
                adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
                lvItem.setAdapter(adapterFilterItem);

            }
        } else if (pos == 4) {
            if (map.containsKey(4)) {
                dummyFilterList = map.get(4);
                shortlistlowtohigh();
                adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
                lvItem.setAdapter(adapterFilterItem);
            }

        } else if (pos == 5) {
            if (map.containsKey(5)) {
                dummyFilterList = map.get(5);
                shortlistlowtohigh();
                adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
                lvItem.setAdapter(adapterFilterItem);
            }

        } else if (pos == 6) {
            if (map.containsKey(6)) {
                dummyFilterList = map.get(6);
                shortlistlowtohigh();
                adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
                lvItem.setAdapter(adapterFilterItem);
            }

        }

    }


    public void savetomap() {
        dummyFilterList = new ArrayList<>();
        job_typesList = generalDTO.getData().getJob_types();
        for (int i = 0; i < job_typesList.size(); i++) {
            dummyFilterList.add(new DummyFilterDTO(job_typesList.get(i).getId(), job_typesList.get(i).getJob_type()));
        }
        
        adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
        lvItem.setAdapter(adapterFilterItem);
        map.put(0, dummyFilterList);


        dummyFilterList = new ArrayList<>();
        locationsList = generalDTO.getData().getLocations();
        for (int i = 0; i < locationsList.size(); i++) {
            dummyFilterList.add(new DummyFilterDTO(locationsList.get(i).getId(), locationsList.get(i).getLocation_name()));
        }
        
        adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
        lvItem.setAdapter(adapterFilterItem);
        map.put(1, dummyFilterList);

        dummyFilterList = new ArrayList<>();
        job_by_roles_List = generalDTO.getData().getJob_by_roles();

        for (int i = 0; i < job_by_roles_List.size(); i++) {
            dummyFilterList.add(new DummyFilterDTO(job_by_roles_List.get(i).getId(), job_by_roles_List.get(i).getJob_by_role()));
        }
        
        adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
        lvItem.setAdapter(adapterFilterItem);
        map.put(2, dummyFilterList);


        dummyFilterList = new ArrayList<>();
        qualificationsList = generalDTO.getData().getQualifications();

        for (int i = 0; i < qualificationsList.size(); i++) {
            dummyFilterList.add(new DummyFilterDTO(qualificationsList.get(i).getId(), qualificationsList.get(i).getQualification()));
        }
        
        adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
        lvItem.setAdapter(adapterFilterItem);
        map.put(3, dummyFilterList);

        dummyFilterList = new ArrayList<>();
        for (int i = 0; i < exprinceYearDTOList.size(); i++) {
            dummyFilterList.add(new DummyFilterDTO(exprinceYearDTOList.get(i).getId(), exprinceYearDTOList.get(i).getYear()));
        }
        
        adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
        lvItem.setAdapter(adapterFilterItem);
        map.put(4, dummyFilterList);


        dummyFilterList = new ArrayList<>();
        specializationList = generalDTO.getData().getSpecialization();

        for (int i = 0; i < specializationList.size(); i++) {
            dummyFilterList.add(new DummyFilterDTO(specializationList.get(i).getId(), specializationList.get(i).getSpecialization()));
        }
        
        adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
        lvItem.setAdapter(adapterFilterItem);
        map.put(5, dummyFilterList);


        dummyFilterList = new ArrayList<>();
        area_of_sectorsList = generalDTO.getData().getArea_of_sectors();
        for (int i = 0; i < area_of_sectorsList.size(); i++) {
            dummyFilterList.add(new DummyFilterDTO(area_of_sectorsList.get(i).getId(), area_of_sectorsList.get(i).getArea_of_sector()));
        }
        
        adapterFilterItem = new AdapterFilterItem(mContext, dummyFilterList);
        lvItem.setAdapter(adapterFilterItem);
        map.put(6, dummyFilterList);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                map.clear();
                finish();
                break;
            case R.id.ivFilter:
                finish();
                break;
        }
    }

    public void shortlistlowtohigh() {
        Collections.sort(dummyFilterList, new Comparator<DummyFilterDTO>() {

            public int compare(DummyFilterDTO obj1, DummyFilterDTO obj2) {
                return obj1.getName().compareToIgnoreCase(obj2.getName());

            }
        });
    }

}
