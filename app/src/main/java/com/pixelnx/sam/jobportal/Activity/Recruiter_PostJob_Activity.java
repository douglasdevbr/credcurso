package com.pixelnx.sam.jobportal.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.DTO.CommonDTO;
import com.pixelnx.sam.jobportal.DTO.GeneralDTO;
import com.pixelnx.sam.jobportal.DTO.MonthDTO;
import com.pixelnx.sam.jobportal.DTO.MyPostJobDTO;
import com.pixelnx.sam.jobportal.DTO.UserRecruiterDTO;
import com.pixelnx.sam.jobportal.DTO.YearDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.network.NetworkManager;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomButton;
import com.pixelnx.sam.jobportal.utils.CustomEdittext;
import com.pixelnx.sam.jobportal.utils.CustomTextHeader;
import com.pixelnx.sam.jobportal.utils.MonthYearPicker;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Recruiter_PostJob_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private CustomEdittext etCgpa, etPassYear, etJobDescription, etSkillRequired, etSalFrom, etSalTo, etVacancies, etLastDateOfApp;
    private Spinner SpJobType, SpDesignation, SpQualification, SpJobLocation, SpSpecialization, SpExperience, SpPerDuration, SpAreaOfSector;
    private CheckBox cbFtoF, cbWritten, cbTelephonic, cbGD, cbWalkin;
    private CustomButton btnPostJob;
    RecruiterDashboardActivity recruiterDashboardActivity;
    private SharedPrefrence prefrence;
    GeneralDTO generalDTO;
    UserRecruiterDTO userRecruiterDTO = new UserRecruiterDTO();
    private Context mContext;
    private ImageView ivBack;
    private ArrayList<GeneralDTO.Job_types> job_typesList = new ArrayList<>();
    private ArrayList<GeneralDTO.Job_by_roles> designationList = new ArrayList<>();
    private ArrayList<GeneralDTO.Qualifications> qualificationsList = new ArrayList<>();
    private ArrayList<GeneralDTO.Locations> locationsList = new ArrayList<>();
    private ArrayList<GeneralDTO.Specialization> specializationList = new ArrayList<>();
    private List<YearDTO> exprinceYearDTOList;
    private List<MonthDTO> exprinceMonthDTOList = new ArrayList<>();
    private ArrayList<GeneralDTO.Area_of_sectors> area_of_sectorsList = new ArrayList<>();
    private ArrayList<String> process;
    private ArrayAdapter<GeneralDTO.Job_types> job_type_Adapter;
    private ArrayAdapter<GeneralDTO.Job_by_roles> designation_adapter;
    private ArrayAdapter<GeneralDTO.Qualifications> qualification_Adapter;
    private ArrayAdapter<GeneralDTO.Locations> location_Adapter;
    private ArrayAdapter<GeneralDTO.Specialization> specialization_Adapter;
    private ArrayAdapter<YearDTO> exprinceYear_adapter;
    private ArrayAdapter<MonthDTO> exprinceMonth_adapter;
    private ArrayAdapter<GeneralDTO.Area_of_sectors> aos_Adapter;

    JSONArray jsonArray = new JSONArray();
    View view;
    private Calendar myCalendar = Calendar.getInstance();
    int flag = 0;
    private CustomTextHeader tvHeader;
    private MyPostJobDTO myPostJobDTO;
    String id = "";
    private MonthYearPicker myp;
    private Typeface font;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_post_job);
        mContext = Recruiter_PostJob_Activity.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        ProjectUtils.initImageLoader(mContext);
        flag = getIntent().getIntExtra(Consts.FLAG, 0);
        font = Typeface.createFromAsset(
                Recruiter_PostJob_Activity.this.getAssets(),
                "Raleway-Light.ttf");

        userRecruiterDTO = prefrence.getRecruiterDTO(Consts.RECRUITER_DTO);
        init();


    }


    private void init() {
        tvHeader = (CustomTextHeader) findViewById(R.id.tvHeader);
        etCgpa = (CustomEdittext) findViewById(R.id.etCgpa);
        etPassYear = (CustomEdittext) findViewById(R.id.etPassYear);
        etJobDescription = (CustomEdittext) findViewById(R.id.etJobDescription);
        etSkillRequired = (CustomEdittext) findViewById(R.id.etSkillRequired);
        etSalFrom = (CustomEdittext) findViewById(R.id.etSalFrom);
        etSalTo = (CustomEdittext) findViewById(R.id.etSalTo);
        etVacancies = (CustomEdittext) findViewById(R.id.etVacancies);
        etLastDateOfApp = (CustomEdittext) findViewById(R.id.etLastDateOfApp);


        SpJobType = (Spinner) findViewById(R.id.SpJobType);
        SpDesignation = (Spinner) findViewById(R.id.SpDesignation);
        SpQualification = (Spinner) findViewById(R.id.SpQualification);
        SpJobLocation = (Spinner) findViewById(R.id.SpJobLocation);
        SpSpecialization = (Spinner) findViewById(R.id.SpSpecialization);
        SpExperience = (Spinner) findViewById(R.id.SpExperience);
        SpPerDuration = (Spinner) findViewById(R.id.SpPerDuration);
        SpAreaOfSector = (Spinner) findViewById(R.id.SpAreaOfSector);

        cbFtoF = (CheckBox) findViewById(R.id.cbFtoF);
        cbWritten = (CheckBox) findViewById(R.id.cbWritten);
        cbTelephonic = (CheckBox) findViewById(R.id.cbTelephonic);
        cbGD = (CheckBox) findViewById(R.id.cbGD);
        cbWalkin = (CheckBox) findViewById(R.id.cbWalkin);

        ivBack = (ImageView) findViewById(R.id.ivBack);

        btnPostJob = (CustomButton) findViewById(R.id.btnPostJob);

        SpJobType.setOnItemSelectedListener(this);
        SpDesignation.setOnItemSelectedListener(this);
        SpQualification.setOnItemSelectedListener(this);
        SpJobLocation.setOnItemSelectedListener(this);
        SpSpecialization.setOnItemSelectedListener(this);
        SpExperience.setOnItemSelectedListener(this);
        SpPerDuration.setOnItemSelectedListener(this);
        SpAreaOfSector.setOnItemSelectedListener(this);

        cbFtoF.setOnClickListener(this);
        cbWalkin.setOnClickListener(this);
        cbGD.setOnClickListener(this);
        cbTelephonic.setOnClickListener(this);
        cbWritten.setOnClickListener(this);

        cbFtoF.setTypeface(font);
        cbWalkin.setTypeface(font);
        cbGD.setTypeface(font);
        cbTelephonic.setTypeface(font);
        cbWritten.setTypeface(font);

        etLastDateOfApp.setOnClickListener(this);
        etPassYear.setOnClickListener(this);
        ivBack.setOnClickListener(this);


        btnPostJob.setOnClickListener(this);

        exprinceYearDTOList = new ArrayList<>();
        exprinceYearDTOList.add(new YearDTO("0", "----- SELECT EXPERIENCE -----"));
        exprinceYearDTOList.add(new YearDTO("1", "Fresher"));
        exprinceYearDTOList.add(new YearDTO("2", "06 Months"));
        exprinceYearDTOList.add(new YearDTO("3", "1 Year"));
        exprinceYearDTOList.add(new YearDTO("4", "1.5 Year"));
        exprinceYearDTOList.add(new YearDTO("5", "2 Year"));
        exprinceYearDTOList.add(new YearDTO("6", "2.5 Year"));
        exprinceYearDTOList.add(new YearDTO("7", "3 Year"));
        exprinceYearDTOList.add(new YearDTO("8", "3+ Year"));


        exprinceMonthDTOList = new ArrayList<>();
        exprinceMonthDTOList.add(new MonthDTO("0", "--SELECT--"));
        exprinceMonthDTOList.add(new MonthDTO("1", "Year"));
        exprinceMonthDTOList.add(new MonthDTO("2", "Month"));
        exprinceMonthDTOList.add(new MonthDTO("3", "Day"));
        exprinceMonthDTOList.add(new MonthDTO("4", "Hour"));

        etCgpa.setFilters(new InputFilter[]{
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    int beforeDecimal = 2, afterDecimal = 2;

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        String temp = etCgpa.getText() + source.toString();

                        if (temp.equals(".")) {
                            return "0.";
                        } else if (temp.toString().indexOf(".") == -1) {
                            // no decimal point placed yet
                            if (temp.length() > beforeDecimal) {
                                return "";
                            }
                        } else {
                            temp = temp.substring(temp.indexOf(".") + 1);
                            if (temp.length() > afterDecimal) {
                                return "";
                            }
                        }

                        return super.filter(source, start, end, dest, dstart, dend);
                    }
                }
        });

        getData();

        myp = new MonthYearPicker(this);
        myp.build(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (myp.getSelectedYear() != 0) {
                    etPassYear.setText("" + myp.getSelectedYear());

                } else {
                    ProjectUtils.showToast(mContext, getResources().getString(R.string.cant_futuer_date));
                }
            }
        }, null);

    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Consts.BASE_URL + Consts.GENERAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            generalDTO = new Gson().fromJson(response, GeneralDTO.class);
                            showData();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

    }

    public void showData() {
        job_typesList = generalDTO.getData().getJob_types();
        job_typesList.add(0, new GeneralDTO.Job_types("----- SELECT JOB TYPE -----"));
        job_type_Adapter = new ArrayAdapter<GeneralDTO.Job_types>(mContext, android.R.layout.simple_spinner_dropdown_item, job_typesList);
        job_type_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpJobType.setAdapter(job_type_Adapter);

        designationList = generalDTO.getData().getJob_by_roles();
        designationList.add(0, new GeneralDTO.Job_by_roles("----- SELECT DESIGNATION -----"));
        designation_adapter = new ArrayAdapter<GeneralDTO.Job_by_roles>(mContext, android.R.layout.simple_spinner_dropdown_item, designationList);
        designation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpDesignation.setAdapter(designation_adapter);

        qualificationsList = generalDTO.getData().getQualifications();
        qualificationsList.add(0, new GeneralDTO.Qualifications("----- SELECT QUALIFICATION -----"));
        qualification_Adapter = new ArrayAdapter<GeneralDTO.Qualifications>(mContext, android.R.layout.simple_spinner_dropdown_item, qualificationsList);
        qualification_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpQualification.setAdapter(qualification_Adapter);

        locationsList = generalDTO.getData().getLocations();
        locationsList.add(0, new GeneralDTO.Locations("----- SELECT LOCATION -----"));
        location_Adapter = new ArrayAdapter<GeneralDTO.Locations>(mContext, android.R.layout.simple_spinner_dropdown_item, locationsList);
        location_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpJobLocation.setAdapter(location_Adapter);


        area_of_sectorsList = generalDTO.getData().getArea_of_sectors();
        area_of_sectorsList.add(0, new GeneralDTO.Area_of_sectors("----- SELECT AREA -----"));
        aos_Adapter = new ArrayAdapter<GeneralDTO.Area_of_sectors>(mContext, android.R.layout.simple_spinner_dropdown_item, area_of_sectorsList);
        aos_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpAreaOfSector.setAdapter(aos_Adapter);

        specializationList = generalDTO.getData().getSpecialization();
        specializationList.add(0, new GeneralDTO.Specialization(" ----- SELECT SPECIALIZATION -----"));
        specialization_Adapter = new ArrayAdapter<GeneralDTO.Specialization>(mContext, android.R.layout.simple_spinner_dropdown_item, specializationList);
        specialization_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpSpecialization.setAdapter(specialization_Adapter);

        exprinceYear_adapter = new ArrayAdapter<YearDTO>(mContext, android.R.layout.simple_spinner_dropdown_item, exprinceYearDTOList);
        exprinceYear_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpExperience.setAdapter(exprinceYear_adapter);

        exprinceMonth_adapter = new ArrayAdapter<MonthDTO>(mContext, android.R.layout.simple_spinner_dropdown_item, exprinceMonthDTOList);
        exprinceMonth_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpPerDuration.setAdapter(exprinceMonth_adapter);

        if (flag == 1) {
            btnPostJob.setText("POST JOB");
            tvHeader.setText("Post A Job");
        } else if (flag == 2) {
            id = getIntent().getStringExtra(Consts.JOB_ID);
            btnPostJob.setText("UPDATE JOB");
            tvHeader.setText("Edit Job");
            getPostJob();
        }
    }


    public void Submit() {

        if (NetworkManager.isConnectToInternet(mContext)) {
            submitForm();

        } else {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_connection));
        }
    }

    /*public boolean Validate() {
        if (job_typesList.get(SpJobType.getSelectedItemPosition()).getJob_type().equalsIgnoreCase("----- SELECT JOB TYPE -----")) {
            ProjectUtils.showToast(mContext, "Please select Job type");
            return false;
        } else if (designationList.get(SpDesignation.getSelectedItemPosition()).getJob_by_role().equalsIgnoreCase("----- SELECT DESIGNATION -----")) {
            ProjectUtils.showToast(mContext, "Please select Designation");
            return false;
        } else if (qualificationsList.get(SpQualification.getSelectedItemPosition()).getQualification().equalsIgnoreCase("----- SELECT QUALIFICATION -----")) {
            ProjectUtils.showToast(mContext, "Please select Qualification");
            return false;
        } else if (locationsList.get(SpJobLocation.getSelectedItemPosition()).getLocation_name().equalsIgnoreCase("----- SELECT LOCATION -----")) {
            ProjectUtils.showToast(mContext, "Please select Location");
            return false;
        } else if (etPassYear.getText().toString().trim().equals("")) {
            ProjectUtils.showToast(mContext, "Please enter year of passing");
            etPassYear.requestFocus();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etCgpa)) {
            etCgpa.setError("Please enter percentage");
            etCgpa.requestFocus();
            return false;
        } else if (specializationList.get(SpSpecialization.getSelectedItemPosition()).getSpecialization().equalsIgnoreCase("----- SELECT SPECIALIZATION -----")) {
            ProjectUtils.showToast(mContext, "Please select Specialization");
            return false;
        } else if (area_of_sectorsList.get(SpAreaOfSector.getSelectedItemPosition()).getArea_of_sector().equalsIgnoreCase("----- SELECT AREA -----")) {
            ProjectUtils.showToast(mContext, "Please select AREA");
            return false;
        } else if (exprinceYearDTOList.get(SpExperience.getSelectedItemPosition()).getYear().equalsIgnoreCase("----- SELECT EXPERIENCE -----")) {
            ProjectUtils.showToast(mContext, "Please select Experience");
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etSalFrom)) {
            etSalFrom.setError("Please enter min sal");
            etSalFrom.requestFocus();

            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etSalTo)) {
            etSalTo.setError("Please enter max sal");
            etSalTo.requestFocus();
            return false;
        } else if (exprinceMonthDTOList.get(SpPerDuration.getSelectedItemPosition()).getMonth().equalsIgnoreCase("-- SELECT --")) {
            ProjectUtils.showToast(mContext, "Please select Duration");
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etVacancies)) {
            etVacancies.setError("Please enter no. of vacancies");
            etVacancies.requestFocus();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etSkillRequired)) {
            etSkillRequired.setError("Please enter Required Skills");
            etSkillRequired.requestFocus();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etLastDateOfApp)) {
            ProjectUtils.showToast(mContext, "Please enter last date of application");
            etLastDateOfApp.requestFocus();
            return false;
        } else if (!(cbFtoF.isChecked() || cbWritten.isChecked() || cbTelephonic.isChecked() || cbGD.isChecked() || cbWalkin.isChecked())) {
            ProjectUtils.showToast(mContext, "please select hiring process");
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etJobDescription)) {
            etJobDescription.setError("Please enter Job Description");
            etJobDescription.requestFocus();
            return false;
        }

        return true;
    }*/
    public void submitForm() {
        if (!validateJobType()) {
            return;
        } else if (!validateDesignation()) {
            return;
        } else if (!validateQualification()) {
            return;
        } else if (!validateLocation()) {
            return;
        } else if (!validatePassing()) {
            return;
        } else if (!validateCgpa()) {
            return;
        } else if (!validateSpeciailization()) {
            return;
        } else if (!validateArea()) {
            return;
        } else if (!validateExperience()) {
            return;
        } else if (!validateSalFrom()) {
            return;
        } else if (!validateSalTo()) {
            return;
        } else if (!validatePerValue()) {
            return;
        } else if (!validateVacancies()) {
            return;
        } else if (!validateSkills()) {
            return;
        } else if (!validateLastDate()) {
            return;
        } else if (!validateHiring()) {
            return;
        } else if (!validateJobDescription()) {
            return;
        } else {
            postJob();

        }
    }

    public boolean validateJobType() {
        if (job_typesList.get(SpJobType.getSelectedItemPosition()).getJob_type().equalsIgnoreCase("----- SELECT JOB TYPE -----")) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_job_type));
            return false;
        } else {
            return true;
        }
    }

    public boolean validateDesignation() {
        if (designationList.get(SpDesignation.getSelectedItemPosition()).getJob_by_role().equalsIgnoreCase("----- SELECT DESIGNATION -----")) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_designation));
            return false;
        } else {
            return true;
        }
    }

    public boolean validateQualification() {
        if (qualificationsList.get(SpQualification.getSelectedItemPosition()).getQualification().equalsIgnoreCase("----- SELECT QUALIFICATION -----")) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_qualification));
            return false;
        } else {
            return true;
        }
    }

    public boolean validateLocation() {
        if (locationsList.get(SpJobLocation.getSelectedItemPosition()).getLocation_name().equalsIgnoreCase("----- SELECT LOCATION -----")) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_location));
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePassing() {
        if (etPassYear.getText().toString().trim().length() <= 0) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_pass_year));
            etPassYear.requestFocus();
            return false;
        } else {
            etPassYear.clearFocus();
            return true;
        }
    }

    public boolean validateCgpa() {
        if (etCgpa.getText().toString().trim().length() <= 0) {
            etCgpa.setError(getResources().getString(R.string.val_percent));
            etCgpa.requestFocus();
            return false;
        } else {
            etCgpa.clearFocus();
            return true;
        }
    }

    public boolean validateSpeciailization() {
        if (specializationList.get(SpSpecialization.getSelectedItemPosition()).getSpecialization().equalsIgnoreCase(" ----- SELECT SPECIALIZATION -----")) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_specilaization));
            return false;
        } else {
            return true;
        }
    }

    public boolean validateArea() {
        if (area_of_sectorsList.get(SpAreaOfSector.getSelectedItemPosition()).getArea_of_sector().equalsIgnoreCase("----- SELECT AREA -----")) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_area));
            return false;
        } else {
            return true;
        }
    }

    public boolean validateExperience() {
        if (exprinceYearDTOList.get(SpExperience.getSelectedItemPosition()).getYear().equalsIgnoreCase("----- SELECT EXPERIENCE -----")) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_experience));
            return false;
        } else {
            return true;
        }
    }

    public boolean validateSalFrom() {
        if (etSalFrom.getText().toString().trim().length() <= 0) {
            etSalFrom.setError(getResources().getString(R.string.min_sal));
            etSalFrom.requestFocus();
            return false;
        } else {
            etSalFrom.clearFocus();
            return true;
        }
    }

    public boolean validateSalTo() {
        if (etSalTo.getText().toString().trim().length() <= 0) {
            etSalTo.setError(getResources().getString(R.string.max_sal));
            etSalTo.requestFocus();
            return false;
        } else {
            int min = Integer.parseInt(etSalFrom.getText().toString().trim());
            int max = Integer.parseInt(etSalTo.getText().toString().trim());
            if (min < max) {
                etSalTo.clearFocus();
                return true;
            } else {
                etSalTo.setError(getResources().getString(R.string.max_min_sal));
                etSalTo.requestFocus();
                return false;
            }

        }
    }

    public boolean validatePerValue() {
        if (exprinceMonthDTOList.get(SpPerDuration.getSelectedItemPosition()).getMonth().equalsIgnoreCase("--SELECT--")) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_duration));
            return false;
        } else {
            return true;
        }
    }

    public boolean validateVacancies() {
        if (etVacancies.getText().toString().trim().length() <= 0) {
            etVacancies.setError(getResources().getString(R.string.val_vacancies));
            etVacancies.requestFocus();
            return false;
        } else {
            etVacancies.clearFocus();
            return true;
        }
    }

    public boolean validateSkills() {
        if (etSkillRequired.getText().toString().trim().length() <= 0) {
            etSkillRequired.setError(getResources().getString(R.string.val_skills));
            etSkillRequired.requestFocus();
            return false;
        } else {
            etSkillRequired.clearFocus();
            return true;
        }
    }

    public boolean validateLastDate() {
        if (etLastDateOfApp.getText().toString().trim().length() <= 0) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_last_date));
            etLastDateOfApp.requestFocus();
            return false;
        } else {
            etLastDateOfApp.clearFocus();
            return true;
        }
    }

    public boolean validateHiring() {
        if (!(cbFtoF.isChecked() || cbWritten.isChecked() || cbTelephonic.isChecked() || cbGD.isChecked() || cbWalkin.isChecked())) {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.val_hiring));
            return false;
        } else {

            return true;
        }

    }

    public boolean validateJobDescription() {
        if (etJobDescription.getText().toString().trim().length() <= 0) {
            etJobDescription.setError(getResources().getString(R.string.val_des));
            etJobDescription.requestFocus();
            return false;
        } else {
            etJobDescription.clearFocus();
            return true;
        }
    }


    public void postJob() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.POST_JOB_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("POST_JOB", response.toString());

                        CommonDTO commonDTO = new CommonDTO();
                        commonDTO = new Gson().fromJson(response, CommonDTO.class);

                        if (commonDTO.isStatus()) {
                            ProjectUtils.showToast(mContext, commonDTO.getMessage());
                            finish();
                        } else {
                            ProjectUtils.showToast(mContext, commonDTO.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if (flag == 1) {
                    params.put(Consts.JOB_ID, "0");

                } else if (flag == 2) {
                    params.put(Consts.JOB_ID, id);

                }//dfddfd


                params.put(Consts.RECRUITER_ID, userRecruiterDTO.getData().getId());
                params.put(Consts.JOB_TYPE, Integer.parseInt(job_typesList.get(SpJobType.getSelectedItemPosition()).getId()) + "");
                params.put(Consts.JOB_BY_ROLES, Integer.parseInt(designationList.get(SpDesignation.getSelectedItemPosition()).getId()) + "");
                params.put(Consts.QUALIFICATION, Integer.parseInt(qualificationsList.get(SpQualification.getSelectedItemPosition()).getId()) + "");
                params.put(Consts.JOB_LOCATION, Integer.parseInt(locationsList.get(SpJobLocation.getSelectedItemPosition()).getId()) + "");
                params.put(Consts.YEAR_OF_PASSING, ProjectUtils.getEditTextValue(etPassYear));
                params.put(Consts.PERCENTAGE_OR_CGPA, ProjectUtils.getEditTextValue(etCgpa));
                params.put(Consts.SPECIALIZATION, Integer.parseInt(specializationList.get(SpSpecialization.getSelectedItemPosition()).getId()) + "");
                params.put(Consts.AREA_OF_SECTOR, Integer.parseInt(area_of_sectorsList.get(SpAreaOfSector.getSelectedItemPosition()).getId()) + "");
                params.put(Consts.SKILLS_REQUIRED, ProjectUtils.getEditTextValue(etSkillRequired));
                params.put(Consts.EXPERIENCE, exprinceYearDTOList.get(SpExperience.getSelectedItemPosition()).getYear() + "");
                params.put(Consts.JOB_DISCRIPTION, ProjectUtils.getEditTextValue(etJobDescription));
                params.put(Consts.MIN_SAL, ProjectUtils.getEditTextValue(etSalFrom));
                params.put(Consts.MAX_SAL, ProjectUtils.getEditTextValue(etSalTo));
                params.put(Consts.PER, exprinceMonthDTOList.get(SpPerDuration.getSelectedItemPosition()).getMonth() + "");
                params.put(Consts.VACANCIES, ProjectUtils.getEditTextValue(etVacancies));
                params.put(Consts.LAST_DATE, ProjectUtils.getEditTextValue(etLastDateOfApp));

                JSONArray jsonArray = new JSONArray();
                if (cbFtoF.isChecked()) {
                    String p1 = cbFtoF.getText().toString().trim();
                    jsonArray.put(p1);
                }

                if (cbGD.isChecked()) {
                    String p2 = cbGD.getText().toString().trim();
                    jsonArray.put(p2);

                }

                if (cbTelephonic.isChecked()) {
                    String p3 = cbTelephonic.getText().toString().trim();
                    jsonArray.put(p3);
                }

                if (cbWalkin.isChecked()) {
                    String p4 = cbWalkin.getText().toString().trim();
                    jsonArray.put(p4);
                }

                if (cbWritten.isChecked()) {
                    String p5 = cbWritten.getText().toString().trim();
                    jsonArray.put(p5);
                }


                params.put(Consts.PROCESS, jsonArray.toString());

                Log.e("POST_JOB", params.toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

    }

    public void getPostJob() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.BASE_URL + Consts.GET_RECRUITER_JOB_DETAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("GET_JOB", response.toString());

                        myPostJobDTO = new MyPostJobDTO();
                        myPostJobDTO = new Gson().fromJson(response, MyPostJobDTO.class);

                        if (myPostJobDTO.isStatus()) {
                            ProjectUtils.showToast(mContext, myPostJobDTO.getMessage());
                            viewJob();
                        } else {
                            ProjectUtils.showToast(mContext, myPostJobDTO.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(Consts.JOB_ID, id);
                params.put(Consts.RECRUITER_ID, userRecruiterDTO.getData().getId());
                Log.e("GET_JOB", params.toString());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPostJob:
                Submit();
                break;
            case R.id.etPassYear:
                myp.show();
                //   ProjectUtils.datePicker(myCalendar, mContext, etPassYear, false);
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.etLastDateOfApp:
                ProjectUtils.datePicker(myCalendar, mContext, etLastDateOfApp, true);
                break;
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTypeface(font);
        ((TextView) adapterView.getChildAt(0)).setTextSize(14);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//
        finish();
    }

    public void viewJob() {

        for (int i = 0; i < locationsList.size(); i++) {
            if (locationsList.get(i).getId().equalsIgnoreCase(myPostJobDTO.getData().getJob_location_id())) {
                SpJobLocation.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < job_typesList.size(); i++) {
            if (job_typesList.get(i).getId().equalsIgnoreCase(myPostJobDTO.getData().getJob_type_id())) {
                SpJobType.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < qualificationsList.size(); i++) {
            if (qualificationsList.get(i).getId().equalsIgnoreCase(myPostJobDTO.getData().getQualification_id())) {
                SpQualification.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < specializationList.size(); i++) {
            if (specializationList.get(i).getId().equalsIgnoreCase(myPostJobDTO.getData().getSpecialization_id())) {
                SpSpecialization.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < area_of_sectorsList.size(); i++) {
            if (area_of_sectorsList.get(i).getId().equalsIgnoreCase(myPostJobDTO.getData().getArea_of_sector_id())) {
                SpAreaOfSector.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < designationList.size(); i++) {
            if (designationList.get(i).getId().equalsIgnoreCase(myPostJobDTO.getData().getJob_by_roles_id())) {
                SpDesignation.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < exprinceYearDTOList.size(); i++) {
            if (exprinceYearDTOList.get(i).getYear().equalsIgnoreCase(myPostJobDTO.getData().getExperience())) {
                SpExperience.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < exprinceMonthDTOList.size(); i++) {
            if (exprinceMonthDTOList.get(i).getMonth().equalsIgnoreCase(myPostJobDTO.getData().getPer())) {
                SpPerDuration.setSelection(i);
                break;
            }
        }

        etPassYear.setText(myPostJobDTO.getData().getYear_of_passing());
        etCgpa.setText(myPostJobDTO.getData().getPercentage_or_cgpa());
        etSalFrom.setText(myPostJobDTO.getData().getMin_sal());
        etSalTo.setText(myPostJobDTO.getData().getMax_sal());
        etVacancies.setText(myPostJobDTO.getData().getVacancies());
        etSkillRequired.setText(myPostJobDTO.getData().getSkills_required());
        etLastDateOfApp.setText(myPostJobDTO.getData().getLast_date());
        etJobDescription.setText(myPostJobDTO.getData().getJob_discription());
        process = new ArrayList<>();
        process = myPostJobDTO.getData().getProcess();
        //, , , ,
        if (process.contains("Face to Face")) {
            cbFtoF.setChecked(true);
        }
        if (process.contains("Written-test")) {
            cbWritten.setChecked(true);
        }
        if (process.contains("Telephonic")) {
            cbTelephonic.setChecked(true);
        }
        if (process.contains("Group Discussion")) {
            cbGD.setChecked(true);
        }
        if (process.contains("Walk In")) {
            cbWalkin.setChecked(true);
        }

    }

}