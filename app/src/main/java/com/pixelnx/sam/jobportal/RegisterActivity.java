package com.pixelnx.sam.jobportal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.pixelnx.sam.jobportal.Fragment.Recruiter_Reg_Fragment;
import com.pixelnx.sam.jobportal.Fragment.Seeker_Reg_Fragment;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fm;
    private Seeker_Reg_Fragment seekerRegFragment = new Seeker_Reg_Fragment();
    private Recruiter_Reg_Fragment recruiterFragment = new Recruiter_Reg_Fragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frame, recruiterFragment, "").commit();

    }

    @Override
    public void onClick(View v) {
        fm.beginTransaction().replace(R.id.frame, recruiterFragment, "").commit();
        /* switch (v.getId()) {
           case R.id.tvSeeker:
                fm.beginTransaction().replace(R.id.frame, seekerRegFragment, "").commit();
                tvSeeker.setTextColor(Color.rgb(106, 100, 231));
                tvRecruiter.setTextColor(Color.rgb(204, 202, 247));
                break;
            case R.id.tvRecruiter:
                fm.beginTransaction().replace(R.id.frame, recruiterFragment, "").commit();
                tvSeeker.setTextColor(Color.rgb(204, 202, 247));
                tvRecruiter.setTextColor(Color.rgb(106, 100, 231));
                break;*/
        }


    }



