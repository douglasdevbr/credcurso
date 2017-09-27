package com.pixelnx.sam.jobportal.DTO;

import java.io.Serializable;

/**
 * Created by shubham on 24/8/17.
 */

public class YearDTO implements Serializable {
    String id = "";
    String year = "";

    public YearDTO(String id, String year) {
        this.id = id;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return year.toString();
    }
}
