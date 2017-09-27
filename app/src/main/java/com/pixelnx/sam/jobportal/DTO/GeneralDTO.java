package com.pixelnx.sam.jobportal.DTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shubham on 12/8/17.
 */

public class GeneralDTO implements Serializable {


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    String code = "";
    String status = "";
    String message = "";
    Data data;

    public class Data {
        ArrayList<Locations> locations = new ArrayList<>();
        ArrayList<Area_of_sectors> area_of_sectors = new ArrayList<>();
        ArrayList<Specialization> specialization = new ArrayList<>();
        ArrayList<Qualifications> qualifications = new ArrayList<>();
        ArrayList<Job_by_roles> job_by_roles = new ArrayList<>();
        ArrayList<Job_types> job_types = new ArrayList<>();

        public ArrayList<Locations> getLocations() {
            return locations;
        }

        public void setLocations(ArrayList<Locations> locations) {
            this.locations = locations;
        }

        public ArrayList<Area_of_sectors> getArea_of_sectors() {
            return area_of_sectors;
        }

        public void setArea_of_sectors(ArrayList<Area_of_sectors> area_of_sectors) {
            this.area_of_sectors = area_of_sectors;
        }

        public ArrayList<Specialization> getSpecialization() {
            return specialization;
        }

        public void setSpecialization(ArrayList<Specialization> specialization) {
            this.specialization = specialization;
        }

        public ArrayList<Qualifications> getQualifications() {
            return qualifications;
        }

        public void setQualifications(ArrayList<Qualifications> qualifications) {
            this.qualifications = qualifications;
        }

        public ArrayList<Job_by_roles> getJob_by_roles() {
            return job_by_roles;
        }

        public void setJob_by_roles(ArrayList<Job_by_roles> job_by_roles) {
            this.job_by_roles = job_by_roles;
        }

        public ArrayList<Job_types> getJob_types() {
            return job_types;
        }

        public void setJob_types(ArrayList<Job_types> job_types) {
            this.job_types = job_types;
        }
    }

    public static class Locations {
        String id = "";
        String location_name = "";
        String created_at = "";
        String updated_at = "";

        public Locations(String id, String location_name) {
            this.id = id;
            this.location_name = location_name;
        }

        public Locations(String location_name) {
            this.location_name = location_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLocation_name() {
            return location_name;
        }

        public void setLocation_name(String location_name) {
            this.location_name = location_name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        @Override
        public String toString() {
            return location_name.toString();
        }
    }

    public static class Area_of_sectors {
        String id = "";
        String area_of_sector = "";
        String created_at = "";
        String updated_at = "";

        public Area_of_sectors(String id, String area_of_sector) {
            this.id = id;
            this.area_of_sector = area_of_sector;
        }

        public Area_of_sectors(String area_of_sector) {
            this.area_of_sector = area_of_sector;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getArea_of_sector() {
            return area_of_sector;
        }

        public void setArea_of_sector(String area_of_sector) {
            this.area_of_sector = area_of_sector;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        @Override
        public String toString() {
            return area_of_sector.toString();
        }
    }

    public static class Specialization {
        String id = "";
        String specialization = "";
        String created_at = "";
        String updated_at = "";

        public Specialization(String id, String specialization) {
            this.id = id;
            this.specialization = specialization;
        }

        public Specialization(String specialization) {
            this.specialization = specialization;
        }

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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        @Override
        public String toString() {
            return specialization.toString();
        }
    }

    public static class Qualifications {
        String id = "";
        String qualification = "";
        String created_at = "";
        String updated_at = "";

        public Qualifications(String id, String qualification) {
            this.id = id;
            this.qualification = qualification;
        }

        public Qualifications(String qualification) {
            this.qualification = qualification;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        @Override
        public String toString() {
            return qualification.toString();
        }
    }

    public static class Job_by_roles {
        String id = "";
        String job_by_role = "";
        String created_at = "";
        String updated_at = "";
        boolean isSelected=false;

        public Job_by_roles(String id, String job_by_role) {
            this.id = id;
            this.job_by_role = job_by_role;
        }

        public Job_by_roles(String job_by_role) {
            this.job_by_role = job_by_role;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJob_by_role() {
            return job_by_role;
        }

        public void setJob_by_role(String job_by_role) {
            this.job_by_role = job_by_role;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        @Override
        public String toString() {
            return job_by_role.toString();
        }
    }

    public static class Job_types {
        String id = "";
        String job_type = "";
        String created_at = "";
        String updated_at = "";

        public Job_types(String id, String job_type) {
            this.id = id;
            this.job_type = job_type;
        }

        public Job_types(String job_type) {
            this.job_type = job_type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJob_type() {
            return job_type;
        }

        public void setJob_type(String job_type) {
            this.job_type = job_type;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        @Override
        public String toString() {
            return job_type.toString();

        }


    }
}