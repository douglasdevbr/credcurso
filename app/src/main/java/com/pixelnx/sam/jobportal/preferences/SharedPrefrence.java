package com.pixelnx.sam.jobportal.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixelnx.sam.jobportal.DTO.ActiveJobDTO;
import com.pixelnx.sam.jobportal.DTO.UserRecruiterDTO;
import com.pixelnx.sam.jobportal.DTO.UserSeekerDTO;

import java.lang.reflect.Type;

public class SharedPrefrence {
    public static SharedPreferences myPrefs;
    public static SharedPreferences.Editor prefsEditor;
    public static SharedPrefrence myObj;

    private SharedPrefrence() {

    }

    public void clearAllPreferences() {
        prefsEditor = myPrefs.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public static SharedPrefrence getInstance(Context ctx) {
        if (myObj == null) {
            myObj = new SharedPrefrence();
            myPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
            prefsEditor = myPrefs.edit();
        }
        return myObj;
    }

    public void clearPreferences(String key) {
        prefsEditor.remove(key);
        prefsEditor.commit();
    }


    public void setIntValue(String Tag, int value) {
        prefsEditor.putInt(Tag, value);
        prefsEditor.apply();
    }

    public int getIntValue(String Tag) {
        return myPrefs.getInt(Tag, 0);

    }

    public void setLongValue(String Tag, long value) {
        prefsEditor.putLong(Tag, value);
        prefsEditor.apply();
    }

    public long getLongValue(String Tag) {
        return myPrefs.getLong(Tag, 0);

    }


    public void setValue(String Tag, String token) {
        prefsEditor.putString(Tag, token);
        prefsEditor.commit();
    }


    public String getValue(String Tag) {
        return myPrefs.getString(Tag, "default");
    }

    public boolean getBooleanValue(String Tag) {
        return myPrefs.getBoolean(Tag, false);

    }

    public void setBooleanValue(String Tag, boolean token) {
        prefsEditor.putBoolean(Tag, token);
        prefsEditor.commit();
    }

    public void setUserDTO(UserSeekerDTO userSeekerDTO, String tag) {

        //convert to string using gson
        Gson gson = new Gson();
        String hashMapString = gson.toJson(userSeekerDTO);

        prefsEditor.putString(tag, hashMapString);
        prefsEditor.apply();
    }

    public UserSeekerDTO getUserDTO(String tag) {
        String obj = myPrefs.getString(tag, "defValue");
        if (obj.equals("defValue")) {
            return new UserSeekerDTO();
        } else {

            Gson gson = new Gson();
            String storedHashMapString = myPrefs.getString(tag, "");
            Type type = new TypeToken<UserSeekerDTO>() {}.getType();
            UserSeekerDTO testHashMap = gson.fromJson(storedHashMapString, type);

            return testHashMap;
        }
    }

    public void setRecruiterDTO(UserRecruiterDTO userRecruiterDTO, String tag) {

        //convert to string using gson
        Gson gson = new Gson();
        String hashMapString = gson.toJson(userRecruiterDTO);

        prefsEditor.putString(tag, hashMapString);
        prefsEditor.apply();
    }

    public UserRecruiterDTO getRecruiterDTO(String tag) {
        String obj = myPrefs.getString(tag, "defValue");
        if (obj.equals("defValue")) {
            return new UserRecruiterDTO();
        } else {

            Gson gson = new Gson();
            String storedHashMapString = myPrefs.getString(tag, "");
            Type type = new TypeToken<UserRecruiterDTO>() {
            }.getType();
            UserRecruiterDTO testHashMap = gson.fromJson(storedHashMapString, type);

            return testHashMap;
        }
    }

    public void setActiveJobsDTO(ActiveJobDTO activeJobDTO, String tag) {

        //convert to string using gson
        Gson gson = new Gson();
        String hashMapString = gson.toJson(activeJobDTO);

        prefsEditor.putString(tag, hashMapString);
        prefsEditor.apply();
    }

    public ActiveJobDTO getActiveJobsDTO(String tag) {
        String obj = myPrefs.getString(tag, "defValue");
        if (obj.equals("defValue")) {
            return new ActiveJobDTO();
        } else {

            Gson gson = new Gson();
            String storedHashMapString = myPrefs.getString(tag, "");
            Type type = new TypeToken<ActiveJobDTO>() {
            }.getType();
            ActiveJobDTO testHashMap = gson.fromJson(storedHashMapString, type);

            return testHashMap;
        }
    }





}
