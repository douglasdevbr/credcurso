package com.pixelnx.sam.jobportal.DTO;

import java.io.Serializable;

/**
 * Created by shubham on 24/8/17.
 */

public class MonthDTO implements Serializable {
    String id ="";
    String month = "";

    public MonthDTO(String id, String month) {
        this.id = id;
        this.month = month;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return month.toString();
    }
}
