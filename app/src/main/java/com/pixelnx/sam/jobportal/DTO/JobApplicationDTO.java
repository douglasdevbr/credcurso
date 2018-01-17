package com.pixelnx.sam.jobportal.DTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shubham on 28/8/17.
 */

public class JobApplicationDTO implements Serializable {

    int code = 0;
    boolean status = false;
    String message = "";
    ArrayList<Data> data = new ArrayList<>();

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

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data {
        String id = "";
        String specialization = "";
        String job_discription = "";
        String specialization_id = "";
        String no_applied_by = "";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSpecialization() {
            return specialization;
        }

        public void setSpecialization(String specialization) {
            this.specialization = specialization;
        }

        public String getJob_discription() {
            return job_discription;
        }

        public void setJob_discription(String job_discription) {
            this.job_discription = job_discription;
        }

        public String getSpecialization_id() {
            return specialization_id;
        }

        public void setSpecialization_id(String specialization_id) {
            this.specialization_id = specialization_id;
        }

        public String getNo_applied_by() {
            return no_applied_by;
        }

        public void setNo_applied_by(String no_applied_by) {
            this.no_applied_by = no_applied_by;
        }
    }
}