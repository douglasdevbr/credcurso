package com.pixelnx.sam.jobportal.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shubham on 2/8/17.
 */

public class CustomTextHeader extends TextView {
    public CustomTextHeader(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomTextHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomTextHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    public CustomTextHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Raleway-Light.ttf", context);
        setTypeface(customFont);
        setTextSize(22);

    }

}
