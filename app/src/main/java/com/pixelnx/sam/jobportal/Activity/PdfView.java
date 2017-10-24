package com.pixelnx.sam.jobportal.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.pixelnx.sam.jobportal.DTO.SingleJobDTO;
import com.pixelnx.sam.jobportal.R;
import com.pixelnx.sam.jobportal.utils.Consts;

public class PdfView extends AppCompatActivity {
    WebView mWebView;
    private SingleJobDTO singleJobDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        if (getIntent().hasExtra(Consts.SINGLE_JOB_DTO)) {
            singleJobDTO = (SingleJobDTO) getIntent().getSerializableExtra(Consts.SINGLE_JOB_DTO);
        }
        mWebView = new WebView(PdfView.this);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + singleJobDTO.getData().getProfile().getResume());
        setContentView(mWebView);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
