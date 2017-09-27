package com.pixelnx.sam.jobportal.DTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shubham on 29/8/17.
 */

public class JobsViewDTO implements Serializable {


    String code = "";
    boolean status = false;
    String message = "";
    ArrayList<Data> data = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data {
        String full_name = "";
        String mobile_no = "";
        String email = "";
        String seeker_id = "";
        String role_type = "";

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSeeker_id() {
            return seeker_id;
        }

        public void setSeeker_id(String seeker_id) {
            this.seeker_id = seeker_id;
        }

        public String getRole_type() {
            return role_type;
        }

        public void setRole_type(String role_type) {
            this.role_type = role_type;
        }
    }

}
