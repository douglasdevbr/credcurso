package com.pixelnx.sam.jobportal.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shubham on 2/8/17.
 */

public class CustomTextviewBold extends TextView {


    public CustomTextviewBold(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomTextviewBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomTextviewBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    public CustomTextviewBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Raleway-Bold.ttf", context);
        setTypeface(customFont);
        setTextSize(14);

    }
}
