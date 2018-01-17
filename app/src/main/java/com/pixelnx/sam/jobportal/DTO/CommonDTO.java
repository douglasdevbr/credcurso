package com.pixelnx.sam.jobportal.DTO;


import java.io.Serializable;

/**
 * Created by shubham on 16/8/17.
 */

public class CommonDTO implements Serializable {
    int code = 0;
    boolean status = false;
    String message = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
