package com.pixelnx.sam.jobportal.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by shubham on 2/8/17.
 */

public class CustomButton extends Button {

    public CustomButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }
    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Raleway-Bold.ttf", context);
setTypeface(customFont);
        setTextSize(14);

    }
}
