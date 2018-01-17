package com.pixelnx.sam.jobportal.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.pixelnx.sam.jobportal.DTO.UserSeekerDTO;
import com.pixelnx.sam.jobportal.Fragment.SeekerChangePassFragment;
import com.pixelnx.sam.jobportal.Fragment.SeekerHomeFragment;
import com.pixelnx.sam.jobportal.Fragment.SeekerUpdateProfileFragment;
import com.pixelnx.sam.jobportal.LoginActivity;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;
import com.pixelnx.sam.jobportal.utils.Config;
import com.pixelnx.sam.jobportal.utils.Consts;
import com.pixelnx.sam.jobportal.utils.CustomTextHeader;


public class SeekerDashboardActivity extends AppCompatActivity {
    InputMethodManager inputManager;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Context mcontext;
    public ImageView menuLeftIV, search_icon, filter_icon;
    public CustomTextHeader tvJobs;
    private static final String TAG = SeekerDashboardActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private SeekerHomeFragment seekerHomeFragment = new SeekerHomeFragment();
    //  FragmentManager fm;
    private SharedPrefrence prefrence;
    private static final String TAG_HOME = "home";
    private static final String TAG_UPDATE_PROFILE = "update_profile";
    private static final String TAG_CHANGE_PASS = "change_pass";
    public static String CURRENT_TAG = TAG_HOME;
    private Handler mHandler;
    public static int navItemIndex = 0;
    private UserSeekerDTO userSeekerDTO;
    int guest_tag = 0;
    private boolean shouldLoadHomeFragOnBackPress = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mcontext = SeekerDashboardActivity.this;
        prefrence = SharedPrefrence.getInstance(mcontext);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        userSeekerDTO = prefrence.getUserDTO(Consts.SEEKER_DTO);
        mHandler = new Handler();
        displayFirebaseRegId();
        if (getIntent().hasExtra(Consts.TAG_GUEST)) {
            guest_tag = getIntent().getIntExtra(Consts.TAG_GUEST, 0);

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        menuLeftIV = (ImageView) findViewById(R.id.menuLeftIV);
        tvJobs = (CustomTextHeader) findViewById(R.id.tvJobs);
        search_icon = (ImageView) findViewById(R.id.search_icon);
        filter_icon = (ImageView) findViewById(R.id.filter_icon);
        if (guest_tag == 1) {
            menuLeftIV.setVisibility(View.VISIBLE);
        } else if (guest_tag == 2) {
            menuLeftIV.setVisibility(View.GONE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            menuLeftIV.setVisibility(View.VISIBLE);
        }
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
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                String message = intent.getExtras().getString("message");

                Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                // txtMessage.setText(message);
            }

        };


    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Log.e("Firebase Reg Id: ", "" + regId);

    }

    private void loadHomeFragment() {
        selectNavMenu();
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
//                if (userSeekerDTO.getData().getProfile_update().equals("0")) {
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, new SeekerUpdateProfileFragment(), TAG_UPDATE_PROFILE);
//                    fragmentTransaction.commitAllowingStateLoss();
//                } else {
//                    Fragment fragment = getSeekerHomeFragment();
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
//                    fragmentTransaction.commitAllowingStateLoss();
//                }
                Fragment fragment = getSeekerHomeFragment();
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

    private Fragment getSeekerHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                SeekerHomeFragment seekerHomeFragment = new SeekerHomeFragment();
                return seekerHomeFragment;
            case 1:
                // photos
                SeekerUpdateProfileFragment seekerUpdateProfileFragment = new SeekerUpdateProfileFragment();
                return seekerUpdateProfileFragment;
            case 2:
                // movies fragment
                SeekerChangePassFragment seekerChangePassFragment = new SeekerChangePassFragment();
                return seekerChangePassFragment;
            default:
                return new SeekerHomeFragment();
        }
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.Jobs:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;

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
        if (guest_tag == 1) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
                return;
            }
            if (shouldLoadHomeFragOnBackPress) {

                if (navItemIndex != 0) {
                    navItemIndex = 0;
                    CURRENT_TAG = TAG_HOME;
                    loadHomeFragment();
                    return;
                }
            }

            clickClose();

        } else if (guest_tag == 2) {
            Intent in = new Intent(mcontext, LoginActivity.class);
            startActivity(in);
            finish();
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
                return;
            }
            if (shouldLoadHomeFragOnBackPress) {

                if (navItemIndex != 0) {
                    navItemIndex = 0;
                    CURRENT_TAG = TAG_HOME;
                    loadHomeFragment();
                    return;
                }
            }
            clickClose();

        }

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
