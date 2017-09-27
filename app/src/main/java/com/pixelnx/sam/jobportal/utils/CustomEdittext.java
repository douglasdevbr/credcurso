package com.pixelnx.sam.jobportal.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by shubham on 2/8/17.
 */

public class CustomEdittext extends EditText {


    public CustomEdittext(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    public CustomEdittext(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Raleway-Light.ttf", context);
        setTypeface(customFont);
        setTextSize(14);


    }
}
