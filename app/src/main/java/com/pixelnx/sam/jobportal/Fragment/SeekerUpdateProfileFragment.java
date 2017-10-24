package com.pixelnx.sam.jobportal.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cocosw.bottomsheet.BottomSheet;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pixelnx.sam.jobportal.Activity.SeekerDashboardActivity;
import com.pixelnx.sam.jobportal.DTO.BaseDTO;
import com.pixelnx.sam.jobportal.DTO.GeneralDTO;
import com.pixelnx.sam.jobportal.DTO.MonthDTO;
import com.pixelnx.sam.jobportal.DTO.UserSeekerDTO;
import com.pixelnx.sam.jobportal.DTO.YearDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.https.UploadFileToServer;
import com.pixelnx.sam.jobportal.network.NetworkManager;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomButton;
import com.pixelnx.sam.jobportal.utils.CustomEdittext;
import com.pixelnx.sam.jobportal.utils.CustomTextSubHeader;
import com.pixelnx.sam.jobportal.utils.ImageCompression;
import com.pixelnx.sam.jobportal.utils.MainFragment;
import com.pixelnx.sam.jobportal.utils.MonthYearPicker;
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class SeekerUpdateProfileFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private LinearLayout LLexperince;
    private CircleImageView IVimage;
    private RadioGroup RGGender, RGExperience;
    private RadioButton RBmale, RBfemale, RBFresher, RBExperinced;
    private CustomEdittext etEmail, etName, etMobile, etCgpa, etFileUpload, etCurrentAddress, etPassYear, etCertification;
    private Spinner SpPrefLoc, SpJobType, SpQualification, SpAreaOfSector, SpSpecialization, SpRoleType, SpTotalExperienceYear, SpToatlExperienceinMonth;
    private CustomButton btnUpdate;
    SeekerDashboardActivity seekerDashboardActivity = new SeekerDashboardActivity();
    private SharedPrefrence prefrence;
    GeneralDTO generalDTO;
    private CustomTextSubHeader tvUsername;
    private UserSeekerDTO userSeekerDTO;
    View view;

    private ArrayList<GeneralDTO.Locations> locationsList = new ArrayList<>();
    private ArrayList<GeneralDTO.Area_of_sectors> area_of_sectorsList = new ArrayList<>();
    private ArrayList<GeneralDTO.Qualifications> qualificationsList = new ArrayList<>();
    private ArrayList<GeneralDTO.Job_types> job_typesList = new ArrayList<>();
    private ArrayList<GeneralDTO.Job_by_roles> job_by_roles_List = new ArrayList<>();
    private ArrayList<GeneralDTO.Specialization> specializationList = new ArrayList<>();
    private List<YearDTO> exprinceYearDTOList = new ArrayList<>();
    private List<MonthDTO> exprinceMonthDTOList = new ArrayList<>();

    private ArrayAdapter<GeneralDTO.Locations> location_Adapter;
    private ArrayAdapter<GeneralDTO.Area_of_sectors> aos_Adapter;
    private ArrayAdapter<GeneralDTO.Qualifications> qualification_Adapter;
    private ArrayAdapter<GeneralDTO.Job_types> job_type_Adapter;
    private ArrayAdapter<GeneralDTO.Job_by_roles> job_by_roles_Adapter;
    private ArrayAdapter<GeneralDTO.Specialization> specialization_Adapter;
    private ArrayAdapter<YearDTO> exprinceYear_adapter;
    private ArrayAdapter<MonthDTO> exprinceMonth_adapter;


    private DisplayImageOptions options;
    private Calendar myCalendar = Calendar.getInstance();

    Uri picUri, filePath;
    int PICK_FROM_CAMERA = 1, PICK_FROM_GALLERY = 2;
    int CROP_CAMERA_IMAGE = 3, CROP_GALLERY_IMAGE = 4;
    BottomSheet.Builder builder;
    String pathOfImage, pathOfResume, extensionIMG, extensionResume;
    Bitmap bm;
    ImageCompression imageCompression;
    byte[] resultByteArray;
    byte[] resultByteArrayRESUME;
    UploadFileToServer uploadFileToServer;
    private int PICK_PDF_REQUEST = 5005;

    private MonthYearPicker myp;
    private Typeface font;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_update_profile_seeker, container, false);
        prefrence = SharedPrefrence.getInstance(getActivity());
        userSeekerDTO = prefrence.getUserDTO(Consts.SEEKER_DTO);
        ProjectUtils.initImageLoader(getActivity());
        seekerDashboardActivity.tvJobs.setText("Profile Setting");
        seekerDashboardActivity.search_icon.setVisibility(View.INVISIBLE);
        seekerDashboardActivity.filter_icon.setVisibility(View.INVISIBLE);
        font = Typeface.createFromAsset(
                SeekerUpdateProfileFragment.this.getActivity().getAssets(),
                "Raleway-Light.ttf");
        init(view);
        tvUsername.setText("" + userSeekerDTO.getData().getFull_name());
        return view;
    }

    private void init(View view) {

        LLexperince = (LinearLayout) view.findViewById(R.id.LLexperince);
        IVimage = (CircleImageView) view.findViewById(R.id.IVimage);
        etEmail = (CustomEdittext) view.findViewById(R.id.etEmail);
        etName = (CustomEdittext) view.findViewById(R.id.etName);
        etMobile = (CustomEdittext) view.findViewById(R.id.etMobile);
        etCgpa = (CustomEdittext) view.findViewById(R.id.etCgpa);
        etFileUpload = (CustomEdittext) view.findViewById(R.id.etFileUpload);
        etCurrentAddress = (CustomEdittext) view.findViewById(R.id.etCurrentAddress);
        etPassYear = (CustomEdittext) view.findViewById(R.id.etPassYear);
        etCertification = (CustomEdittext) view.findViewById(R.id.etCertification);
        tvUsername = (CustomTextSubHeader) view.findViewById(R.id.tvUsername);

        SpRoleType = (Spinner) view.findViewById(R.id.SpRoleType);
        SpPrefLoc = (Spinner) view.findViewById(R.id.SpPrefLoc);
        SpJobType = (Spinner) view.findViewById(R.id.SpJobType);
        SpQualification = (Spinner) view.findViewById(R.id.SpQualification);
        SpAreaOfSector = (Spinner) view.findViewById(R.id.SpAreaOfSector);
        SpSpecialization = (Spinner) view.findViewById(R.id.SpSpecialization);
        SpTotalExperienceYear = (Spinner) view.findViewById(R.id.SpTotalExperienceYear);
        SpToatlExperienceinMonth = (Spinner) view.findViewById(R.id.SpExperienceinMonth);

        RGGender = (RadioGroup) view.findViewById(R.id.RGGender);
        RBmale = (RadioButton) view.findViewById(R.id.RBmale);
        RBfemale = (RadioButton) view.findViewById(R.id.RBfemale);

        RGExperience = (RadioGroup) view.findViewById(R.id.RGExperience);
        RBFresher = (RadioButton) view.findViewById(R.id.RBFresher);
        RBExperinced = (RadioButton) view.findViewById(R.id.RBExperinced);
        btnUpdate = (CustomButton) view.findViewById(R.id.btnUpdate);


        SpPrefLoc.setOnItemSelectedListener(this);
        SpJobType.setOnItemSelectedListener(this);
        SpRoleType.setOnItemSelectedListener(this);
        SpQualification.setOnItemSelectedListener(this);
        SpAreaOfSector.setOnItemSelectedListener(this);
        SpSpecialization.setOnItemSelectedListener(this);
        SpToatlExperienceinMonth.setOnItemSelectedListener(this);
        SpTotalExperienceYear.setOnItemSelectedListener(this);
        RBExperinced.setOnClickListener(this);
        RBFresher.setOnClickListener(this);
        etFileUpload.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        etPassYear.setOnClickListener(this);

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


        etEmail.setText(userSeekerDTO.getData().getEmail());
        etName.setText(userSeekerDTO.getData().getFull_name());
        etMobile.setText(userSeekerDTO.getData().getMobile_no());

        exprinceYearDTOList = new ArrayList<>();
        exprinceYearDTOList.add(new YearDTO("0", "----- SELECT -----"));
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

        exprinceMonthDTOList = new ArrayList<>();
        exprinceMonthDTOList.add(new MonthDTO("0", "----- SELECT -----"));
        exprinceMonthDTOList.add(new MonthDTO("1", "0 month"));
        exprinceMonthDTOList.add(new MonthDTO("2", "1 month"));
        exprinceMonthDTOList.add(new MonthDTO("3", "2 month"));
        exprinceMonthDTOList.add(new MonthDTO("4", "3 month"));
        exprinceMonthDTOList.add(new MonthDTO("5", "4 month"));
        exprinceMonthDTOList.add(new MonthDTO("6", "5 month"));
        exprinceMonthDTOList.add(new MonthDTO("7", "6 month"));
        exprinceMonthDTOList.add(new MonthDTO("8", "7 month"));
        exprinceMonthDTOList.add(new MonthDTO("9", "8 month"));
        exprinceMonthDTOList.add(new MonthDTO("10", "9 month"));
        exprinceMonthDTOList.add(new MonthDTO("11", "10 month"));
        exprinceMonthDTOList.add(new MonthDTO("12", "11 month"));

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.cemra)
                .showImageForEmptyUri(R.drawable.cemra)
                .showImageOnFail(R.drawable.cemra)
                .cacheInMemory(true)
                .considerExifParams(true)
                .cacheOnDisk(true)
                .cacheOnDisc(true)
                .displayer(new SimpleBitmapDisplayer())
                .build();

        builder = new BottomSheet.Builder(getActivity()).sheet(R.menu.menu_cards);
        builder.title("Job Portal : Take Image From");
        builder.listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case R.id.camera_cards:
                        if (ProjectUtils.hasPermissionInManifest(getActivity(), PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                            if (ProjectUtils.hasPermissionInManifest(getActivity(), PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                try {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                    File file = getOutputMediaFile(1);
                                    if (!file.exists()) {
                                        try {
                                            file.createNewFile();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        //Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.asd", newFile);
                                        picUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getApplicationContext().getPackageName() + ".fileprovider", file);
                                    } else {
                                        picUri = Uri.fromFile(file); // create
                                    }


                                    prefrence.setValue(Consts.IMAGE_URI_CAMERA, picUri.toString());
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file
                                    startActivityForResult(intent, PICK_FROM_CAMERA);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        break;
                    case R.id.gallery_cards:
                        if (ProjectUtils.hasPermissionInManifest(getActivity(), PICK_FROM_CAMERA, Manifest.permission.CAMERA)) {
                            if (ProjectUtils.hasPermissionInManifest(getActivity(), PICK_FROM_GALLERY, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                File file = getOutputMediaFile(1);
                                if (!file.exists()) {
                                    try {
                                        file.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                picUri = Uri.fromFile(file);

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_GALLERY);

                            }
                        }
                        break;
                    case R.id.cancel_cards:
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        });
                        break;
                }
            }
        });
        IVimage.setOnClickListener(this);
        getData();
        myp = new MonthYearPicker(getActivity());
        myp.build(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (myp.getSelectedYear() != 0) {
                    etPassYear.setText("" + myp.getSelectedYear());

                } else {
                    ProjectUtils.showToast(getActivity(), getResources().getString(R.string.cant_futuer_date));
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
                        Log.e("error", error.toString());
                    }
                }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void showData() {

        locationsList = generalDTO.getData().getLocations();
        locationsList.add(0, new GeneralDTO.Locations("----- SELECT LOCATION -----"));
        location_Adapter = new ArrayAdapter<GeneralDTO.Locations>(getActivity(), android.R.layout.simple_spinner_dropdown_item, locationsList);
        location_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpPrefLoc.setAdapter(location_Adapter);

        job_typesList = generalDTO.getData().getJob_types();
        job_typesList.add(0, new GeneralDTO.Job_types("----- SELECT JOB TYPE -----"));
        job_type_Adapter = new ArrayAdapter<GeneralDTO.Job_types>(getActivity(), android.R.layout.simple_spinner_dropdown_item, job_typesList);
        job_type_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpJobType.setAdapter(job_type_Adapter);


        qualificationsList = generalDTO.getData().getQualifications();
        qualificationsList.add(0, new GeneralDTO.Qualifications("----- SELECT QUALIFICATION -----"));
        qualification_Adapter = new ArrayAdapter<GeneralDTO.Qualifications>(getActivity(), android.R.layout.simple_spinner_dropdown_item, qualificationsList);
        qualification_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpQualification.setAdapter(qualification_Adapter);

        area_of_sectorsList = generalDTO.getData().getArea_of_sectors();
        area_of_sectorsList.add(0, new GeneralDTO.Area_of_sectors("----- SELECT AREA OF SECTOR -----"));
        aos_Adapter = new ArrayAdapter<GeneralDTO.Area_of_sectors>(getActivity(), android.R.layout.simple_spinner_dropdown_item, area_of_sectorsList);
        aos_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpAreaOfSector.setAdapter(aos_Adapter);

        specializationList = generalDTO.getData().getSpecialization();
        specializationList.add(0, new GeneralDTO.Specialization("----- SELECT SPECIALIZATION -----"));
        specialization_Adapter = new ArrayAdapter<GeneralDTO.Specialization>(getActivity(), android.R.layout.simple_spinner_dropdown_item, specializationList);
        specialization_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpSpecialization.setAdapter(specialization_Adapter);

        job_by_roles_List = generalDTO.getData().getJob_by_roles();
        job_by_roles_List.add(0, new GeneralDTO.Job_by_roles("----- SELECT DESIGNATION -----"));
        job_by_roles_Adapter = new ArrayAdapter<GeneralDTO.Job_by_roles>(getActivity(), android.R.layout.simple_spinner_dropdown_item, job_by_roles_List);
        job_by_roles_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpRoleType.setAdapter(job_by_roles_Adapter);

        exprinceYear_adapter = new ArrayAdapter<YearDTO>(getActivity(), android.R.layout.simple_spinner_dropdown_item, exprinceYearDTOList);
        exprinceYear_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpTotalExperienceYear.setAdapter(exprinceYear_adapter);

        exprinceMonth_adapter = new ArrayAdapter<MonthDTO>(getActivity(), android.R.layout.simple_spinner_dropdown_item, exprinceMonthDTOList);
        exprinceMonth_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpToatlExperienceinMonth.setAdapter(exprinceMonth_adapter);

        if (userSeekerDTO.getData().getProfile_update().equals("1")) {
            viewSeekerProfile();
        }
    }


    public void viewSeekerProfile() {

        userSeekerDTO = prefrence.getUserDTO(Consts.SEEKER_DTO);

        for (int i = 0; i < locationsList.size(); i++) {
            if (locationsList.get(i).getId().equalsIgnoreCase(userSeekerDTO.getData().getSeeker_profile().getPreferred_location())) {
                SpPrefLoc.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < job_typesList.size(); i++) {
            if (job_typesList.get(i).getId().equalsIgnoreCase(userSeekerDTO.getData().getSeeker_profile().getJob_type())) {
                SpJobType.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < qualificationsList.size(); i++) {
            if (qualificationsList.get(i).getId().equalsIgnoreCase(userSeekerDTO.getData().getSeeker_profile().getSeeker_qualification())) {
                SpQualification.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < area_of_sectorsList.size(); i++) {
            if (area_of_sectorsList.get(i).getId().equalsIgnoreCase(userSeekerDTO.getData().getSeeker_profile().getArea_of_sector())) {
                SpAreaOfSector.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < exprinceYearDTOList.size(); i++) {
            if (exprinceYearDTOList.get(i).getYear().equalsIgnoreCase(userSeekerDTO.getData().getSeeker_profile().getExperience_in_year())) {
                SpTotalExperienceYear.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < exprinceMonthDTOList.size(); i++) {
            if (exprinceMonthDTOList.get(i).getMonth().equalsIgnoreCase(userSeekerDTO.getData().getSeeker_profile().getExperience_in_months())) {
                SpToatlExperienceinMonth.setSelection(i);
                break;
            }
        }
        tvUsername.setText(userSeekerDTO.getData().getFull_name());
        etCurrentAddress.setText(userSeekerDTO.getData().getSeeker_profile().getCurrent_address());
        etPassYear.setText(userSeekerDTO.getData().getSeeker_profile().getYear_of_passing());
        etCgpa.setText(userSeekerDTO.getData().getSeeker_profile().getPercentage_or_cgpa());
        etCertification.setText(userSeekerDTO.getData().getSeeker_profile().getCertification());
        etFileUpload.setText(userSeekerDTO.getData().getSeeker_profile().getResume());
        String filenameArray[] = userSeekerDTO.getData().getSeeker_profile().getResume().split("\\.");
        extensionResume = filenameArray[filenameArray.length - 1];
        Log.e("Resume",extensionResume);
        ImageLoader.getInstance().displayImage(userSeekerDTO.getData().getSeeker_profile().getAvtar(), IVimage, options);


        if (userSeekerDTO.getData().getSeeker_profile().getGender().equalsIgnoreCase("Male")) {
            RBmale.setChecked(true);
            RBfemale.setChecked(false);
        } else if (userSeekerDTO.getData().getSeeker_profile().getGender().equalsIgnoreCase("Female")) {
            RBfemale.setChecked(true);
            RBmale.setChecked(false);
        }

        if (userSeekerDTO.getData().getSeeker_profile().getWork_experience().equalsIgnoreCase("Fresher")) {
            RBFresher.setChecked(true);
            RBExperinced.setChecked(false);
            LLexperince.setVisibility(View.GONE);
        } else if (userSeekerDTO.getData().getSeeker_profile().getWork_experience().equalsIgnoreCase("Experienced")) {
            RBExperinced.setChecked(true);
            RBFresher.setChecked(false);
            LLexperince.setVisibility(View.VISIBLE);

            for (int i = 0; i < specializationList.size(); i++) {
                if (specializationList.get(i).getId().equalsIgnoreCase(userSeekerDTO.getData().getSeeker_profile().getSpecialization())) {
                    SpSpecialization.setSelection(i);
                    break;
                }
            }
            for (int i = 0; i < job_by_roles_List.size(); i++) {
                if (job_by_roles_List.get(i).getId().equalsIgnoreCase(userSeekerDTO.getData().getSeeker_profile().getRole_type())) {
                    SpRoleType.setSelection(i);
                    break;
                }
            }

        }


    }

    public void Submit() {
        if (!Validate()) {
            return;
        } else {
            if (NetworkManager.isConnectToInternet(getActivity())) {
                UpdateProfile();

            } else {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_connection));
            }

        }
    }

    public boolean Validate() {

        if (RGGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getActivity(), getResources().getString(R.string.val_gen), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etEmail)) {
            etEmail.setError(getResources().getString(R.string.val_email));
            etEmail.requestFocus();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etName)) {
            etName.setError(getResources().getString(R.string.val_name));
            etName.requestFocus();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etMobile)) {
            etMobile.setError(getResources().getString(R.string.val_phone));
            etMobile.requestFocus();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etCurrentAddress)) {
            etCurrentAddress.setError(getResources().getString(R.string.val_add));
            etCurrentAddress.requestFocus();
            return false;
        } else if (locationsList.get(SpPrefLoc.getSelectedItemPosition()).getLocation_name().equalsIgnoreCase("----- SELECT LOCATION -----")) {
            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.val_location));
            return false;
        } else if (job_typesList.get(SpJobType.getSelectedItemPosition()).getJob_type().equalsIgnoreCase("----- SELECT JOB TYPE -----")) {
            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.val_job_type));
            return false;
        } else if (qualificationsList.get(SpQualification.getSelectedItemPosition()).getQualification().equalsIgnoreCase("----- SELECT QUALIFICATION -----")) {
            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.val_qualification));
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etPassYear)) {
            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.val_pass_year));//
            etPassYear.requestFocus();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etCgpa)) {
            etCgpa.setError(getResources().getString(R.string.val_percent));
            etCgpa.requestFocus();
            return false;
        } else if (area_of_sectorsList.get(SpAreaOfSector.getSelectedItemPosition()).getArea_of_sector().equalsIgnoreCase("----- SELECT AREA OF SECTOR -----")) {
            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.val_area));
            return false;
        } else if (RGExperience.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getActivity(), getResources().getString(R.string.val_experience), Toast.LENGTH_SHORT).show();
            return false;
        } else if (RBExperinced.isChecked()) {
            if (exprinceYearDTOList.get(SpTotalExperienceYear.getSelectedItemPosition()).getYear().equalsIgnoreCase("----- SELECT -----")) {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.val_year));
                return false;
            } else if (exprinceMonthDTOList.get(SpToatlExperienceinMonth.getSelectedItemPosition()).getMonth().equalsIgnoreCase("----- SELECT -----")) {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.val_month));//
                return false;
            } else if (specializationList.get(SpSpecialization.getSelectedItemPosition()).getSpecialization().equalsIgnoreCase("----- SELECT SPECIALIZATION -----")) {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.val_specilaization));
                return false;
            } else if (job_by_roles_List.get(SpRoleType.getSelectedItemPosition()).getJob_by_role().equalsIgnoreCase("----- SELECT DESIGNATION -----")) {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.val_role_type));
                return false;
            } else if (!ProjectUtils.IsEditTextValidation(etCertification)) {
                etCertification.setError(getResources().getString(R.string.val_certification));
                etCertification.requestFocus();
                return false;
            }
        } else if (!validateResume()) {
            return false;
        }
        return true;
    }

    public boolean validateResume() {
        if (etFileUpload.getText().toString().trim().length() <= 0) {
            etFileUpload.setError(getResources().getString(R.string.val_upload_resume));
            etFileUpload.requestFocus();
            return false;
        } else {
            if (!extensionResume.equalsIgnoreCase("pdf")||extensionResume.equalsIgnoreCase("doc")||extensionResume.equalsIgnoreCase("docx")){
                etFileUpload.setError(getResources().getString(R.string.val_upload_resume_a));
                etFileUpload.requestFocus();
                return false;
            }else {
                etFileUpload.setError("");
                etFileUpload.clearFocus();
                return true;
            }

        }
    }


    //
    //
    //
    //

    public void UpdateProfile() {
        ProjectUtils.showProgressDialog(getActivity(), true, "Please wait...");
        uploadFileToServer = new UploadFileToServer(Consts.FILL_SEEKER_PROFILE, getParam(), getByteParam(), getFileNames(), getActivity());
        uploadFileToServer.execute(Consts.POST_METHOD);
        uploadFileToServer.setOnTaskFinishedEvent(new UploadFileToServer.AsyncResponse() {

            @Override
            public void processFinish(boolean output, String message, BaseDTO galleryDTO) {
                ProjectUtils.pauseProgressDialog();

                if (output) {
                    ProjectUtils.pauseProgressDialog();
                    ProjectUtils.showToast(getActivity(), message);
                    userSeekerDTO = (UserSeekerDTO) galleryDTO;

                    prefrence.setUserDTO(userSeekerDTO, Consts.SEEKER_DTO);

                    Log.e("Seek", userSeekerDTO.getMessage() + "   " + userSeekerDTO.getData().getProfile_update());
                } else {
                    ProjectUtils.pauseProgressDialog();
                    if (message != null && message.length() > 0) {
                        ProjectUtils.showToast(getActivity(), message);
                    } else {
                        // ProjectUtils.showToast(getActivity(), getResources().getString(R.string.server_error));
                    }

                }
            }

        });
    }


    public ContentValues getParam() {
        ContentValues values = new ContentValues();
        values.put(Consts.SEEKER_ID, userSeekerDTO.getData().getId());
        if (RBmale.isChecked()) {
            values.put(Consts.GENDER, "Male");
            Log.e("MALE", "MALE");

        } else if (RBfemale.isChecked()) {
            values.put(Consts.GENDER, "Female");
            Log.e("FEMALE", "FEMALE");
        }
        values.put(Consts.CURRENT_ADDRESS, ProjectUtils.getEditTextValue(etCurrentAddress));
        values.put(Consts.PREFERRED_LOCATION, locationsList.get(SpPrefLoc.getSelectedItemPosition()).getId() + "");
        values.put(Consts.JOB_TYPE, job_typesList.get(SpJobType.getSelectedItemPosition()).getId() + "");
        values.put(Consts.SEEKER_QUALIFICATION, qualificationsList.get(SpQualification.getSelectedItemPosition()).getId() + "");
        values.put(Consts.PERCENTAGE_OR_CGPA, ProjectUtils.getEditTextValue(etCgpa));
        values.put(Consts.YEAR_OF_PASSING, ProjectUtils.getEditTextValue(etPassYear));
        values.put(Consts.AREA_OF_SECTOR, area_of_sectorsList.get(SpAreaOfSector.getSelectedItemPosition()).getId() + "");
        if (RBFresher.isChecked()) {
            values.put(Consts.WORK_EXPERIENCE, "Fresher");
            Log.e("fresher", "fresher");
            values.put(Consts.EXPERIENCE_IN_YEAR, "");
            values.put(Consts.EXPERIENCE_IN_MONTHS, "");
            values.put(Consts.SPECIALIZATION, "");
            values.put(Consts.ROLE_TYPE, "");
            values.put(Consts.CERTIFICATION, "");

        } else if (RBExperinced.isChecked()) {
            values.put(Consts.WORK_EXPERIENCE, "Experienced");
            Log.e("Experienced", "Experienced");
            values.put(Consts.EXPERIENCE_IN_YEAR, exprinceYearDTOList.get(SpTotalExperienceYear.getSelectedItemPosition()).getYear() + "");
            values.put(Consts.EXPERIENCE_IN_MONTHS, exprinceMonthDTOList.get(SpToatlExperienceinMonth.getSelectedItemPosition()).getMonth() + "");
            values.put(Consts.SPECIALIZATION, specializationList.get(SpSpecialization.getSelectedItemPosition()).getId() + "");
            values.put(Consts.ROLE_TYPE, job_by_roles_List.get(SpRoleType.getSelectedItemPosition()).getId() + "");
            values.put(Consts.CERTIFICATION, ProjectUtils.getEditTextValue(etCertification));
        }


        Log.e("UPDATE_PROFILE", values.toString());
        return values;
    }

    public ContentValues getByteParam() {
        ContentValues values = new ContentValues();
        values.put(Consts.AVTAR, resultByteArray);
        values.put(Consts.RESUME, resultByteArrayRESUME);

        Log.e("img", values.toString());
        return values;
    }

    public ContentValues getFileNames() {
        ContentValues values = new ContentValues();
        values.put(Consts.AVTAR, extensionIMG);
        values.put(Consts.RESUME, extensionResume);
        Log.e("PDF", values.toString());
        return values;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdate:
                Submit();

                break;
            case R.id.IVimage:
                builder.show();
                break;
            case R.id.etFileUpload:
                showFileChooser();
                break;
            case R.id.RBExperinced:
                LLexperince.setVisibility(View.VISIBLE);
                //  updateExperince();
                break;
            case R.id.RBFresher:
                LLexperince.setVisibility(View.GONE);
                break;
            case R.id.etPassYear:
                myp.show();
                //   ProjectUtils.datePicker(myCalendar, getActivity(), etPassYear, false);
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

    private File getOutputMediaFile(int type) {
        String root = Environment.getExternalStorageDirectory().toString();

        File mediaStorageDir = new File(root, Consts.JOB_PORTAL);

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        /**Create a media file name*/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    Consts.JOB_PORTAL + timeStamp + ".png");

        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            pathOfResume = filePath.getPath();
            etFileUpload.setText(pathOfResume);
            String filenameArray[] = pathOfResume.split("\\.");
            extensionResume = filenameArray[filenameArray.length - 1];
            resultByteArrayRESUME = convertPDFToByteArray(pathOfResume);
        }
        if (requestCode == CROP_CAMERA_IMAGE) {

            if (data != null) {
                picUri = Uri.parse(data.getExtras().getString("resultUri"));

                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    pathOfImage = picUri.getPath();


                    imageCompression = new ImageCompression(getActivity());
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                            updateUserImage(IVimage, "file://" + imagePath);

                            String filenameArray[] = imagePath.split("\\.");
                            extensionIMG = filenameArray[filenameArray.length - 1];

                            try {
                                // bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);

                                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                                bm = BitmapFactory.decodeFile(imagePath, bmOptions);
                                ByteArrayOutputStream buffer = new ByteArrayOutputStream(bm.getWidth() * bm.getHeight());
                                bm.compress(Bitmap.CompressFormat.PNG, 100, buffer);
                                resultByteArray = buffer.toByteArray();

                                bm.recycle();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        if (requestCode == CROP_GALLERY_IMAGE) {

            if (data != null) {
                picUri = Uri.parse(data.getExtras().getString("resultUri"));

                try {
                    bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picUri);
                    pathOfImage = picUri.getPath();

                    imageCompression = new ImageCompression(getActivity());
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                            updateUserImage(IVimage, "file://" + imagePath);
                            Log.e("image", imagePath);
                            String filenameArray[] = imagePath.split("\\.");
                            extensionIMG = filenameArray[filenameArray.length - 1];
                            try {
                                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                                bm = BitmapFactory.decodeFile(imagePath, bmOptions);
                                ByteArrayOutputStream buffer = new ByteArrayOutputStream(bm.getWidth() * bm.getHeight());
                                bm.compress(Bitmap.CompressFormat.PNG, 100, buffer);
                                resultByteArray = buffer.toByteArray();

                                bm.recycle();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        if (requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK) {
            if (picUri != null) {
                picUri = Uri.parse(prefrence.getValue(Consts.IMAGE_URI_CAMERA));
                startCropping(picUri, CROP_CAMERA_IMAGE);
            } else {
                picUri = Uri.parse(prefrence.getValue(Consts.IMAGE_URI_CAMERA));
                startCropping(picUri, CROP_CAMERA_IMAGE);
            }
        }


        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            try {
                Uri tempUri = data.getData();

                Log.e("front tempUri", "" + tempUri);
                if (tempUri != null) {
                    startCropping(tempUri, CROP_GALLERY_IMAGE);
                } else {

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }


    }

    public void startCropping(Uri uri, int requestCode) {

        Intent intent = new Intent(getActivity(), MainFragment.class);
        intent.putExtra("imageUri", uri.toString());
        intent.putExtra("requestCode", requestCode);
        startActivityForResult(intent, requestCode);
    }

    public void updateUserImage(final ImageView imageView, String uri) {
        ImageLoader.getInstance().displayImage(uri, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                IVimage.setImageResource(R.drawable.cemra);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                IVimage.setImageResource(R.drawable.cemra);
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    private static byte[] convertPDFToByteArray(String str) {

        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            inputStream = new FileInputStream(str);

            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos.toByteArray();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        seekerDashboardActivity = (SeekerDashboardActivity) activity;

    }

}
