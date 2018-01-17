package com.pixelnx.sam.jobportal.jsonparser;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.pixelnx.sam.jobportal.DTO.UserRecruiterDTO;
import com.pixelnx.sam.jobportal.DTO.UserSeekerDTO;
import com.pixelnx.sam.jobportal.preferences.SharedPrefrence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONParser {
    String jsonObjResponse;
    public String STATUS = "";
    public String MESSAGE = "";
    public boolean RESULT = false;
    public Context context;
    public static String PIC_KEY = "profilepic";
    private SharedPrefrence sharedPrefrence;
    JSONObject jObj;
    JSONArray jsonArray;

    public static String TAG_ERROR = "status";
    public static String TAG_MESSAGE = "message";


    public JSONParser(Context context, String response) {
        this.context = context;
        this.jsonObjResponse = response;
        sharedPrefrence = SharedPrefrence.getInstance(context);
        try {

            Object json = new JSONTokener(response).nextValue();
            if (json instanceof JSONObject) {
                jObj = new JSONObject(response);
                STATUS = getJsonString(jObj, TAG_ERROR);

                MESSAGE = getJsonString(jObj, TAG_MESSAGE);
                if (STATUS.equals("false"))
                    RESULT = false;
                else
                    RESULT = true;
            }
            //you have an object
            else if (json instanceof JSONArray) {
                jsonArray = new JSONArray(response);
                RESULT = true;
            }

        } catch (JSONException e) {
            jObj = null;
            e.printStackTrace();
        }
    }

    public JSONParser(Context context, String response, boolean isPayment) {
        this.context = context;
        this.jsonObjResponse = response;
        sharedPrefrence = SharedPrefrence.getInstance(context);

        try {

            Object json = new JSONTokener(response).nextValue();
            if (json instanceof JSONObject) {
                jObj = new JSONObject(response);
                STATUS = getJsonString(jObj, TAG_ERROR);


                if (STATUS.equals("success")) {
                    RESULT = true;
                    MESSAGE = "Payment success";
                } else {
                    RESULT = false;
                    MESSAGE = "Payment Fail";
                }

            }
            //you have an object
            else if (json instanceof JSONArray) {
                jsonArray = new JSONArray(response);
                RESULT = true;
            }

        } catch (JSONException e) {
            jObj = null;
            e.printStackTrace();
        }
    }

    public static boolean getBoolean(JSONObject jObj, String val) {
        if (val.equals("true"))
            return true;
        else
            return false;
    }

    public static JSONObject getJsonObject(JSONObject obj, String parameter) {
        try {
            return obj.getJSONObject(parameter);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }

    }

    public static String getJsonString(JSONObject obj, String parameter) {
        try {
            return obj.getString(parameter);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String optJsonString(JSONObject obj, String parameter) {
        try {
            return obj.optString(parameter);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static JSONArray getJsonArray(JSONObject obj, String parameter) {
        try {
            return obj.getJSONArray(parameter);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }

    }

    public UserSeekerDTO seekerProfile()

    {
        UserSeekerDTO seekerProfileDTO = new UserSeekerDTO();

        Log.e("seekerProfile", jObj.toString());

        try {
            seekerProfileDTO = new Gson().fromJson(jObj.toString(), UserSeekerDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return seekerProfileDTO;
    }

    public UserRecruiterDTO recruiterProfile()

    {
        UserRecruiterDTO recruiterProfileDTO = new UserRecruiterDTO();

        Log.e("RecruiterProfile", jObj.toString());

        try {
            recruiterProfileDTO = new Gson().fromJson(jObj.toString(), UserRecruiterDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recruiterProfileDTO;
    }


}