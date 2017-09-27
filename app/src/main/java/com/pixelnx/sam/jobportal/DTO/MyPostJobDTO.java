package com.pixelnx.sam.jobportal.DTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by varunverma on 7/9/17.
 */

public class MyPostJobDTO implements Serializable {
    String code = "";
    boolean status = false;
    String message = "";
    Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        String id = "";
        String recruiter_id = "";
        String job_type = "";
        String job_by_roles = "";
        String qualification = "";
        String job_location = "";
        String year_of_passing = "";
        String percentage_or_cgpa = "";
        String specialization = "";
        String skills_required = "";
        String experience = "";
        String job_discription = "";
        String min_sal = "";
        String max_sal = "";
        String per = "";
        String vacancies = "";
        String last_date = "";
        ArrayList<String> process = new ArrayList<>();
        String created_at = "";
        String updated_at = "";
        String area_of_sector = "";
        String area_of_sector_id = "";
        String job_type_id = "";
        String job_by_roles_id = "";
        String qualification_id = "";
        String job_location_id = "";
        String specialization_id = "";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRecruiter_id() {
            return recruiter_id;
        }

        public void setRecruiter_id(String recruiter_id) {
            this.recruiter_id = recruiter_id;
        }

        public String getJob_type() {
            return job_type;
        }

        public void setJob_type(String job_type) {
            this.job_type = job_type;
        }

        public String getJob_by_roles() {
            return job_by_roles;
        }

        public void setJob_by_roles(String job_by_roles) {
            this.job_by_roles = job_by_roles;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getJob_location() {
            return job_location;
        }

        public void setJob_location(String job_location) {
            this.job_location = job_location;
        }

        public String getYear_of_passing() {
            return year_of_passing;
        }

        public void setYear_of_passing(String year_of_passing) {
            this.year_of_passing = year_of_passing;
        }

        public String getPercentage_or_cgpa() {
            return percentage_or_cgpa;
        }

        public void setPercentage_or_cgpa(String percentage_or_cgpa) {
            this.percentage_or_cgpa = percentage_or_cgpa;
        }

        public String getSpecialization() {
            return specialization;
        }

        public void setSpecialization(String specialization) {
            this.specialization = specialization;
        }

        public String getSkills_required() {
            return skills_required;
        }

        public void setSkills_required(String skills_required) {
            this.skills_required = skills_required;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getJob_discription() {
            return job_discription;
        }

        public void setJob_discription(String job_discription) {
            this.job_discription = job_discription;
        }

        public String getMin_sal() {
            return min_sal;
        }

        public void setMin_sal(String min_sal) {
            this.min_sal = min_sal;
        }

        public String getMax_sal() {
            return max_sal;
        }

        public void setMax_sal(String max_sal) {
            this.max_sal = max_sal;
        }

        public String getPer() {
            return per;
        }

        public void setPer(String per) {
            this.per = per;
        }

        public String getVacancies() {
            return vacancies;
        }

        public void setVacancies(String vacancies) {
            this.vacancies = vacancies;
        }

        public String getLast_date() {
            return last_date;
        }

        public void setLast_date(String last_date) {
            this.last_date = last_date;
        }

        public ArrayList<String> getProcess() {
            return process;
        }

        public void setProcess(ArrayList<String> process) {
            this.process = process;
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

        public String getArea_of_sector() {
            return area_of_sector;
        }

        public void setArea_of_sector(String area_of_sector) {
            this.area_of_sector = area_of_sector;
        }

        public String getArea_of_sector_id() {
            return area_of_sector_id;
        }

        public void setArea_of_sector_id(String area_of_sector_id) {
            this.area_of_sector_id = area_of_sector_id;
        }

        public String getJob_type_id() {
            return job_type_id;
        }

        public void setJob_type_id(String job_type_id) {
            this.job_type_id = job_type_id;
        }

        public String getJob_by_roles_id() {
            return job_by_roles_id;
        }

        public void setJob_by_roles_id(String job_by_roles_id) {
            this.job_by_roles_id = job_by_roles_id;
        }

        public String getQualification_id() {
            return qualification_id;
        }

        public void setQualification_id(String qualification_id) {
            this.qualification_id = qualification_id;
        }

        public String getJob_location_id() {
            return job_location_id;
        }

        public void setJob_location_id(String job_location_id) {
            this.job_location_id = job_location_id;
        }

        public String getSpecialization_id() {
            return specialization_id;
        }

        public void setSpecialization_id(String specialization_id) {
            this.specialization_id = specialization_id;
        }
    }
}
