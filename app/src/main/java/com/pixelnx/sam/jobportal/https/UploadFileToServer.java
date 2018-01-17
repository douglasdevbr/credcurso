package com.pixelnx.sam.jobportal.https;


import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.pixelnx.sam.jobportal.DTO.BaseDTO;
import com.pixelnx.sam.jobportal.jsonparser.JSONParser;
import com.pixelnx.sam.jobportal.utils.Consts;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UploadFileToServer extends AsyncTask<String, Void, Boolean> {
    String match;
    ContentValues contentValuesForParam;
    ContentValues contentValuesForByte;
    ContentValues contentValuesForBytePDF;
    Context ctx;
    String readableMsg = "";
    ArrayList<String> urls = new ArrayList<String>();
    HttpClient client;
    BaseDTO baseDTO;


    public UploadFileToServer(String match, ContentValues contentValuesForParam, ContentValues contentValuesForByte, ContentValues contentValuesForBytePDF, Context ctx) {
        this.match = match;
        this.contentValuesForParam = contentValuesForParam;
        this.ctx = ctx;
        this.contentValuesForByte = contentValuesForByte;
        this.contentValuesForBytePDF = contentValuesForBytePDF;
    }

    public interface AsyncResponse {


        void processFinish(boolean output, String message, BaseDTO baseDTO);

        //  void processFinish(boolean output, String message);
    }

    public AsyncResponse delegate = null;

    public void setOnTaskFinishedEvent(AsyncResponse delegate) {
        if (delegate != null) {
            this.delegate = delegate;
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(String... urlsServer) {

        try {
            String response = null;
            client = new HttpClient(Consts.BASE_URL + match);
            client.connectForMultipart();
            addParam();
            addByte();
            //addBytePDf();

            client.finishMultipart();
            response = client.getResponse();

            Log.e("Respone", "" + response.trim());
            JSONParser jsonParser = new JSONParser(ctx, response);
            if (jsonParser != null) {
                if (match.equals(Consts.FILL_SEEKER_PROFILE)) {
                    readableMsg = jsonParser.MESSAGE;
                    baseDTO = jsonParser.seekerProfile();
                    return jsonParser.RESULT;
                }

                if (match.equals(Consts.FILL_RECRUITER_PROFILE)) {
                    readableMsg = jsonParser.MESSAGE;
                    baseDTO = jsonParser.recruiterProfile();
                    return jsonParser.RESULT;
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected void onPostExecute(Boolean result) {

        if (delegate != null) {
            this.delegate.processFinish(result, readableMsg, baseDTO);
        }
    }

    private void addParam() throws UnsupportedEncodingException {
        try {
            Set<Map.Entry<String, Object>> s = contentValuesForParam.valueSet();
            Iterator itr = s.iterator();
            while (itr.hasNext()) {
                Map.Entry me = (Map.Entry) itr.next();
                String key = me.getKey().toString();
                String value = (String) me.getValue();

                // client.addFormPart(URLEncoder.encode(key, "UTF-8"), URLEncoder.encode(value, "UTF-8"));
                client.addFormPart(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addByte() throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        try {
            contentValuesForBytePDF.get(Consts.AVTAR);
            Set<Map.Entry<String, Object>> s = contentValuesForByte.valueSet();
            Iterator itr = s.iterator();
            while (itr.hasNext()) {
                Map.Entry me = (Map.Entry) itr.next();
                String key = me.getKey().toString();
                byte[] value = (byte[]) me.getValue();

                String fileName = (String) contentValuesForBytePDF.get(key);

                String image_name = Consts.JOB_PORTAL + System.currentTimeMillis() + "." + fileName;
                client.addFilePart(key, image_name, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}