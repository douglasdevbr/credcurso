package com.pixelnx.sam.jobportal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.pixelnx.sam.jobportal.Activity.RecruiterDashboardActivity;
import com.pixelnx.sam.jobportal.Activity.SeekerDashboardActivity;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;

import io.fabric.sdk.android.Fabric;

/**
 * Created by varunverma on 27/9/17.
 */

public class Splash extends AppCompatActivity {
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1003;
    private String[] permissions = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.CALL_PRIVILEGED, android.Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE};
    private boolean cameraAccepted, storageAccepted, accessNetState, call_privilage, callPhone, readPhone;
    private static int SPLASH_TIME_OUT = 3000;
    private SharedPrefrence prefrence;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        prefrence = SharedPrefrence.getInstance(Splash.this);

        if (!hasPermissions(Splash.this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

        } else {
            handler.postDelayed(mTask, SPLASH_TIME_OUT);
        }


    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    Runnable mTask = new Runnable() {
        @Override
        public void run() {

            if (prefrence.getBooleanValue(Consts.IS_REGISTER_SEEKER)) {
                startActivity(new Intent(Splash.this, SeekerDashboardActivity.class));
                finish();

            } else if (prefrence.getBooleanValue(Consts.IS_REGISTER_RECRUITER)) {
                startActivity(new Intent(Splash.this, RecruiterDashboardActivity.class));
                finish();

            } else {
                Intent in = new Intent(Splash.this, LoginActivity.class);
                startActivity(in);
                finish();
            }

        }

    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                try {

                    cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    prefrence.setBooleanValue(Consts.CAMERA_ACCEPTED, cameraAccepted);

                    storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    prefrence.setBooleanValue(Consts.STORAGE_ACCEPTED, storageAccepted);

                    accessNetState = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    prefrence.setBooleanValue(Consts.NETWORK_ACCEPTED, accessNetState);

                    call_privilage = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    prefrence.setBooleanValue(Consts.CALL_PR_ACCEPTED, call_privilage);

                    callPhone = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    prefrence.setBooleanValue(Consts.CALL_PH_ACCEPTED, callPhone);

                    readPhone = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    prefrence.setBooleanValue(Consts.READ_PH_ACCEPTED, readPhone);

                    handler.postDelayed(mTask, 100);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}

