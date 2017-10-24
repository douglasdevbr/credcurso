package com.pixelnx.sam.jobportal.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.pixelnx.sam.jobportal.DTO.DummyFilterDTO;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by varun on 27/2/17.
 */
public class ProjectUtils {
    private static Toast toast;
    private static ProgressDialog mProgressDialog;
    private static AlertDialog dialog;
    public static final Calendar refCalender = Calendar.getInstance();
    public static final Calendar myCalendar = Calendar.getInstance();
    public static Date date;
    public static HashMap<Integer, ArrayList<DummyFilterDTO>> map = new HashMap<>();
    public static boolean IsEditTextValidation(EditText text) {
        if (text.getText() != null && text.getText().toString().trim().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean IsEditTextYear(EditText text) {
        if (text.getText() != null && text.getText().toString().trim().length() == 4) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean IsEditTextPercent(EditText text) {
        String regex = "\\d+(?:\\\\.\\\\d+)?%";
        if (text.getText() != null && text.getText().toString().trim().length() <= 3 && text.getText().toString().trim().matches(regex) == true) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean IsPasswordValidation(String password) {
        if (password.length() < 6 || password.length() >= 13) {
            return false;
        }
        return true;
    }

    public static boolean IsEmailValidation(String email) {

        String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else if (email.equals("")) {
            return false;
        }
        return false;
    }

    public static boolean IsMobleValidation(String mobile) {

        String regexStr = "^((0)|(91)|(00)|[7-9]){1}[0-9]{3,14}$";
        if (mobile.length() < 10 || mobile.length() > 13 || mobile.matches(regexStr) == false) {
            return false;
        }
        return true;
    }


    public static boolean hasPermissionInManifest(Activity activity, int requestCode, String permissionName) {
        if (ContextCompat.checkSelfPermission(activity,
                permissionName)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity,
                    new String[]{permissionName},
                    requestCode);
        } else {
            return true;
        }
        return false;
    }

    public static Dialog showProgressDialog(Context context, boolean isCancelable, String message) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        mProgressDialog.setCancelable(true);
        return mProgressDialog;
    }

    public static void pauseProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    public static void showToast(Context context, String message) {
        if (message == null) {
            return;
        }
        if (toast == null && context != null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        }
        if (toast != null) {
            toast.setText(message);
            toast.show();
        }
    }

    public static String getEditTextValue(EditText text) {
        return text.getText().toString().trim();
    }

    public static void initImageLoader(Context context) {

        File cacheDir = StorageUtils.getOwnCacheDirectory(context, Consts.JOB_PORTAL);
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCache(new UnlimitedDiskCache(cacheDir));
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();


        ImageLoader.getInstance().init(config.build());


    }

    /**
     * This method will create alert dialog
     *
     * @param context  Context of calling class
     * @param title    Title of the dialog to be shown
     * @param msg      Msg of the dialog to be shown
     * @param btnText  array of button texts
     * @param listener
     */
    public static void showAlertDialog(Context context, String title,
                                       String msg, String btnText, String btnCancel,
                                       DialogInterface.OnClickListener listener,
                                       DialogInterface.OnClickListener listenerCancel) {

        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                    paramDialogInterface.dismiss();
                }
            };
        if (listenerCancel == null)
            listenerCancel = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                    paramDialogInterface.dismiss();
                }
            };

        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(btnText, listener);
        builder.setNegativeButton(btnCancel, listenerCancel);
        dialog = builder.create();
        dialog.setCancelable(true);
        try {
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
    public static void showAlertDialogOne(Context context, String title,
                                       String msg, String btnText,
                                       DialogInterface.OnClickListener listener) {

        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                    paramDialogInterface.dismiss();
                }
            };

        Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(btnText, listener);
        dialog = builder.create();
        dialog.setCancelable(true);
        try {
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public static void datePicker(final Calendar calendar, final Context context, final EditText editText, final boolean code) {

        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);
                if (code) {
                    if (calendar.getTimeInMillis() >= refCalender.getTimeInMillis()) {
                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        editText.setText(sdf.format(calendar.getTime()));
                    } else {
                        ProjectUtils.showToast(context, "Please select correct date.");
                    }

                } else {

                    if (calendar.getTimeInMillis() <= refCalender.getTimeInMillis()) {
                        String myFormat = "yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        editText.setText(sdf.format(calendar.getTime()));
                    } else {
                        ProjectUtils.showToast(context, "Cannot select future date");
                    }

                }
            }


        }, year, monthOfYear, dayOfMonth);
        datePickerDialog.setTitle("Select Date");
        if (code) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        } else {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }
        datePickerDialog.show();
    }


    public static void timePicker(final Calendar calendar, final Context context, final EditText editText) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);

                SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
                String currentTime = sdf1.format(new Date());
                editText.setText(sdf1.format(calendar.getTime()));
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public static void showSweetDialog(Context context, String title,
                                       String msg, String btnText,
                                       SweetAlertDialog.OnSweetClickListener listener, int dialogType) {

        if (listener == null)
            listener = new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            };
        SweetAlertDialog alertDialog = new SweetAlertDialog(context, dialogType);
        alertDialog.setTitleText(title);
        alertDialog.setConfirmClickListener(listener);
        alertDialog.setContentText(msg);
        alertDialog.setConfirmText(btnText);
        try {
           /* TextView text = (TextView) alertDialog.findViewById(R.id.title_text);
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
            text.setTypeface(customFont);
            text.setGravity(Gravity.CENTER);*/
            alertDialog.show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static void dateFormate(TextView textView, String txt) {//"2017-09-21 12:42:52"
        DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

        DateFormat writeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            date = readFormat.parse(txt);
            Log.e("Date one", "" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            String formattedDate = writeFormat.format(date);
            textView.setText(formattedDate);
        }
    }

    public static void dateFormateOne(TextView textView, String txt) { //"1970-01-01"

        DateFormat readFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        DateFormat writeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            date = readFormat.parse(txt);
            Log.e("Date one", "" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            String formattedDate = writeFormat.format(date);
            textView.setText(formattedDate);
        }
    }
}