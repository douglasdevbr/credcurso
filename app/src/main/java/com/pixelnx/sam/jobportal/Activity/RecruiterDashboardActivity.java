package com.pixelnx.sam.jobportal.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.pixelnx.sam.jobportal.DTO.UserRecruiterDTO;
import com.pixelnx.sam.jobportal.Fragment.JobApplication;
import com.pixelnx.sam.jobportal.Fragment.RecruiterChangePassFragment;
import com.pixelnx.sam.jobportal.Fragment.RecruiterUpdateProfileFragment;
import com.pixelnx.sam.jobportal.LoginActivity;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomTextHeader;

public class RecruiterDashboardActivity extends AppCompatActivity {

    InputMethodManager inputManager;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Context mcontext;
    public ImageView menuLeftIV, search_icon, filter_icon;
    public CustomTextHeader tvJobs;
    private RecruiterUpdateProfileFragment recruiterUpdateProfileFragment = new RecruiterUpdateProfileFragment();
    //FragmentManager fm;
    private SharedPrefrence prefrence;
    private static final String TAG_POST_JOB = "post_job";
    private static final String TAG_UPDATE_PROFILE = "update_profile";
    private static final String TAG_CHANGE_PASS = "change_pass";
    private static final String TAG_JOB_APP = "job_app";
    public static String CURRENT_TAG = TAG_POST_JOB;

    private Handler mHandler;
    public static int navItemIndex = 0;
    private UserRecruiterDTO userRecruiterDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_dashboard);
        mcontext = RecruiterDashboardActivity.this;
        prefrence = SharedPrefrence.getInstance(mcontext);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        userRecruiterDTO = prefrence.getRecruiterDTO(Consts.RECRUITER_DTO);
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tvJobs = (CustomTextHeader) findViewById(R.id.tvJobs);
        menuLeftIV = (ImageView) findViewById(R.id.menuLeftIV);
        search_icon = (ImageView) findViewById(R.id.search_icon);
        filter_icon = (ImageView) findViewById(R.id.filter_icon);
        //fm = getSupportFragmentManager();
        //   fm.beginTransaction().replace(R.id.frame, recruiterUpdateProfileFragment, "").commit();

        menuLeftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });


        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_POST_JOB;
            loadHomeFragment();
        }
    }

    private void loadHomeFragment() {
        selectNavMenu();


        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
//                // update the main content by replacing fragments
//                if (userRecruiterDTO.getData().getRecruiter_profile_update().equals("0")) {
//
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, new RecruiterUpdateProfileFragment(), TAG_UPDATE_PROFILE);
//                    fragmentTransaction.commitAllowingStateLoss();
//                } else {
//                    Fragment fragment = getHomeFragment();
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
//                    fragmentTransaction.commitAllowingStateLoss();
//                }
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();

            }
        };


        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        drawer.closeDrawers();


        invalidateOptionsMenu();
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                JobApplication jobApplication = new JobApplication();
                return jobApplication;

            case 1:
                RecruiterUpdateProfileFragment recruiterUpdateProfileFragment = new RecruiterUpdateProfileFragment();
                return recruiterUpdateProfileFragment;
            case 2:
                RecruiterChangePassFragment recruiterChangePassFragment = new RecruiterChangePassFragment();
                return recruiterChangePassFragment;
            case 3:
                //Recruiter_PostJob_Activity recruiterPostJobFragment = new Recruiter_PostJob_Activity();

                //  return recruiterPostJobFragment;
            default:
                // return new RecruiterUpdateProfileFragment();
                return null;
        }
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.Jobs:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_POST_JOB;
                        break;
                    case R.id.profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_UPDATE_PROFILE;
                        break;
                    case R.id.changepass:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CHANGE_PASS;
                        break;
                    case R.id.signout:
                        logout();
                        break;

                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
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

    public void logout() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.logo)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.logout_dialog))
                .setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        prefrence.clearAllPreferences();
                        Intent i = new Intent(mcontext, LoginActivity.class);

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
