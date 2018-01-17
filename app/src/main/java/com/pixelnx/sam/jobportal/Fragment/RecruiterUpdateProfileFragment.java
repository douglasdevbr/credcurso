package com.pixelnx.sam.jobportal.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pixelnx.sam.jobportal.Activity.RecruiterDashboardActivity;
import com.pixelnx.sam.jobportal.DTO.BaseDTO;
import com.pixelnx.sam.jobportal.DTO.UserRecruiterDTO;
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
import com.pixelnx.sam.jobportal.utils.ProjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class RecruiterUpdateProfileFragment extends Fragment implements View.OnClickListener {

    private CircleImageView ivLogoOrg;
    private RadioGroup rgTypeRec;
    private RadioButton rbCompany, rbConsultant;
    private CustomEdittext etOrgLoc, etOrgAddress, etOrgWebsite, etOrgDescription;
    private CustomButton btnUpdate;
    RecruiterDashboardActivity recruiterDashboardActivity;

    private CustomTextSubHeader tvUsername;
    private SharedPrefrence prefrence;
    UserRecruiterDTO userRecruiterDTO;
    UploadFileToServer uploadFileToServer;
    View view;


    private DisplayImageOptions options;
    Uri picUri;
    int PICK_FROM_CAMERA = 1, PICK_FROM_GALLERY = 2;
    int CROP_CAMERA_IMAGE = 3, CROP_GALLERY_IMAGE = 4;

    BottomSheet.Builder builder;
    String pathOfImage, extensionIMG;
    Bitmap bm;
    ImageCompression imageCompression;
    byte[] resultByteArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_update_profile_recruiter, container, false);
        ProjectUtils.initImageLoader(getActivity());

        prefrence = SharedPrefrence.getInstance(getActivity());
        userRecruiterDTO = prefrence.getRecruiterDTO(Consts.RECRUITER_DTO);

        recruiterDashboardActivity.tvJobs.setText(getResources().getString(R.string.pro_setting));
        recruiterDashboardActivity.search_icon.setVisibility(View.INVISIBLE);
        recruiterDashboardActivity.filter_icon.setVisibility(View.INVISIBLE);
        init(view);
        return view;
    }

    private void init(View view) {
        tvUsername = (CustomTextSubHeader) view.findViewById(R.id.tvUsername);
        ivLogoOrg = (CircleImageView) view.findViewById(R.id.ivLogoOrg);
        etOrgLoc = (CustomEdittext) view.findViewById(R.id.etOrgLoc);
        etOrgAddress = (CustomEdittext) view.findViewById(R.id.etOrgAddress);
        etOrgWebsite = (CustomEdittext) view.findViewById(R.id.etOrgWebsite);
        etOrgDescription = (CustomEdittext) view.findViewById(R.id.etOrgDescription);

        rgTypeRec = (RadioGroup) view.findViewById(R.id.rgTypeRec);
        rbCompany = (RadioButton) view.findViewById(R.id.rbCompany);
        rbConsultant = (RadioButton) view.findViewById(R.id.rbConsultant);

        btnUpdate = (CustomButton) view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        rbCompany.setOnClickListener(this);
        rbConsultant.setOnClickListener(this);


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
        ivLogoOrg.setOnClickListener(this);
        if (userRecruiterDTO.getData().getRecruiter_profile_update().equals("1")) {
            viewProfile();
        }

    }

    public void viewProfile() {
        tvUsername.setText("" + userRecruiterDTO.getData().getOrganisation_name());
        etOrgLoc.setText(userRecruiterDTO.getData().getRecruiter_profile().getOrg_location());
        etOrgAddress.setText(userRecruiterDTO.getData().getRecruiter_profile().getOrg_address());
        etOrgWebsite.setText(userRecruiterDTO.getData().getRecruiter_profile().getOrg_website());
        etOrgDescription.setText(userRecruiterDTO.getData().getRecruiter_profile().getOrg_discription());

        ImageLoader.getInstance().displayImage(userRecruiterDTO.getData().getRecruiter_profile().getOrg_logo(), ivLogoOrg, options);

        if (userRecruiterDTO.getData().getRecruiter_profile().getI_am().equalsIgnoreCase("company")) {
            rbCompany.setChecked(true);
            rbConsultant.setChecked(false);
        } else if (userRecruiterDTO.getData().getRecruiter_profile().getI_am().equalsIgnoreCase("consultant")) {
            rbConsultant.setChecked(true);
            rbCompany.setChecked(false);
        }

    }

    public void submitForm() {
        if (!SelectType()) {
            return;
        } else if (!Validate()) {
            return;
        } else {
            check();
        }
    }


    public boolean SelectType() {

        if (rgTypeRec.getCheckedRadioButtonId() == -1) {

            Toast.makeText(getActivity(), getResources().getString(R.string.val_rc_type), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean Validate() {
        if (!ProjectUtils.IsEditTextValidation(etOrgLoc)) {
            etOrgLoc.setError(getResources().getString(R.string.val_loc));
            etOrgLoc.requestFocus();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etOrgAddress)) {
            etOrgAddress.setError(getResources().getString(R.string.val_add));
            etOrgAddress.requestFocus();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etOrgWebsite)) {
            etOrgWebsite.setError(getResources().getString(R.string.val_web));
            etOrgWebsite.requestFocus();
            return false;
        } else if (!ProjectUtils.IsEditTextValidation(etOrgDescription)) {
            etOrgDescription.setError(getResources().getString(R.string.val_rc_des));
            etOrgDescription.requestFocus();
            return false;
        }


        return true;
    }

    public void check() {
        if (rbCompany.isChecked() || rbConsultant.isChecked()) {
            if (NetworkManager.isConnectToInternet(getActivity())) {
                UpdateProfile();
            } else {
                ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_connection));
            }

        } else {
            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.val_com_con));
        }

    }


    public void UpdateProfile() {
        ProjectUtils.showProgressDialog(getActivity(), true, "Please wait...");


        uploadFileToServer = new UploadFileToServer(Consts.FILL_RECRUITER_PROFILE, getParam(), getByteParam(), getFileNames(), getActivity());
        uploadFileToServer.execute(Consts.POST_METHOD);
        uploadFileToServer.setOnTaskFinishedEvent(new UploadFileToServer.AsyncResponse() {

            @Override
            public void processFinish(boolean output, String message, BaseDTO galleryDTO) {
                ProjectUtils.pauseProgressDialog();


                if (output) {
                    ProjectUtils.pauseProgressDialog();
                    ProjectUtils.showToast(getActivity(), message);

                    userRecruiterDTO = (UserRecruiterDTO) galleryDTO;
                    prefrence.setRecruiterDTO(userRecruiterDTO, Consts.RECRUITER_DTO);

                    Log.e("Recruiter", userRecruiterDTO.getMessage() + "   " + userRecruiterDTO.getData().getRecruiter_profile_update());
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

        values.put(Consts.RECRUITER_ID, userRecruiterDTO.getData().getId());

        if (rbCompany.isChecked()) {
            values.put(Consts.I_AM, "company");
        }
        if (rbConsultant.isChecked()) {
            values.put(Consts.I_AM, "consultant");
        }
        values.put(Consts.ORG_LOCATION, ProjectUtils.getEditTextValue(etOrgLoc));
        values.put(Consts.ORG_ADDRESS, ProjectUtils.getEditTextValue(etOrgAddress));
        values.put(Consts.ORG_WEBSITE, ProjectUtils.getEditTextValue(etOrgWebsite));
        values.put(Consts.ORG_DISCRIPTION, ProjectUtils.getEditTextValue(etOrgDescription));


        Log.e("UPDATED_REC_PROFILE", values.toString());
        return values;
    }

    public ContentValues getByteParam() {
        ContentValues values = new ContentValues();
        values.put(Consts.ORG_LOGO, resultByteArray);

        Log.e("img", values.toString());
        return values;
    }

    public ContentValues getFileNames() {
        ContentValues values = new ContentValues();
        values.put(Consts.ORG_LOGO, extensionIMG);
        Log.e("image_extension", values.toString());
        return values;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdate:
                submitForm();
                break;
            case R.id.ivLogoOrg:
                builder.show();
                break;
        }
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
        if (requestCode == CROP_CAMERA_IMAGE) {

            if (data != null) {
                picUri = Uri.parse(data.getExtras().getString("resultUri"));

                try {
                    //bitmap = MediaStore.Images.Media.getBitmap(EditProfile.this.getContentResolver(), resultUri);
                    pathOfImage = picUri.getPath();


                    imageCompression = new ImageCompression(getActivity());
                    imageCompression.execute(pathOfImage);
                    imageCompression.setOnTaskFinishedEvent(new ImageCompression.AsyncResponse() {
                        @Override
                        public void processFinish(String imagePath) {
                            updateUserImage(ivLogoOrg, "file://" + imagePath);
                            String filenameArray[] = imagePath.split("\\.");
                            extensionIMG = filenameArray[filenameArray.length - 1];
                            try {
                                // bitmap = MediaStore.Images.Media.getBitmap(EditProfile.this.getContentResolver(), resultUri);

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
                            updateUserImage(ivLogoOrg, "file://" + imagePath);
                            String filenameArray[] = imagePath.split("\\.");
                            extensionIMG = filenameArray[filenameArray.length - 1];
                            Log.e("image", imagePath);

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
                ivLogoOrg.setImageResource(R.drawable.cemra);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                ivLogoOrg.setImageResource(R.drawable.cemra);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        recruiterDashboardActivity = (RecruiterDashboardActivity) activity;

    }
}
