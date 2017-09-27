
package com.pixelnx.sam.jobportal.DTO;

import java.io.Serializable;

public class UserSeekerDTO extends BaseDTO implements Serializable {

    private int code;
    private boolean status;
    private String message;
    public Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }



    public class Data {

        String id = "";
        String full_name = "";
        String email = "";
        String mobile_no = "";
        String profile_update = "";
        String verified = "";
        String token = "";
        String created_at = "";
        String updated_at = "";
        String jwt_token = "";
        String role = "";
        public Profile seeker_profile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getProfile_update() {
            return profile_update;
        }

        public void setProfile_update(String profile_update) {
            this.profile_update = profile_update;
        }

        public String getVerified() {
            return verified;
        }

        public void setVerified(String verified) {
            this.verified = verified;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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

        public String getJwt_token() {
            return jwt_token;
        }

        public void setJwt_token(String jwt_token) {
            this.jwt_token = jwt_token;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public Profile getSeeker_profile() {
            return seeker_profile;
        }

        public void setSeeker_profile(Profile seeker_profile) {
            this.seeker_profile = seeker_profile;
        }
    }

        public class Profile {
            String avtar = " ";
            String resume = "";
            String id = "";
            String seeker_id = "";
            String gender = "";
            String current_address = "";
            String preferred_location = "";
            String job_type = "";
            String seeker_qualification = "";
            String year_of_passing = "";
            String percentage_or_cgpa = "";
            String area_of_sector = "";
            String work_experience = "";
            String experience_in_year = "";
            String experience_in_months = "";
            String specialization = "";
            String role_type = "";
            String certification = "";
            String created_at = "";
            String updated_at = "";

            public String getAvtar() {
                return avtar;
            }

            public void setAvtar(String avtar) {
                this.avtar = avtar;
            }

            public String getResume() {
                return resume;
            }

            public void setResume(String resume) {
                this.resume = resume;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSeeker_id() {
                return seeker_id;
            }

            public void setSeeker_id(String seeker_id) {
                this.seeker_id = seeker_id;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getCurrent_address() {
                return current_address;
            }

            public void setCurrent_address(String current_address) {
                this.current_address = current_address;
            }

            public String getPreferred_location() {
                return preferred_location;
            }

            public void setPreferred_location(String preferred_location) {
                this.preferred_location = preferred_location;
            }

            public String getJob_type() {
                return job_type;
            }

            public void setJob_type(String job_type) {
                this.job_type = job_type;
            }

            public String getSeeker_qualification() {
                return seeker_qualification;
            }

            public void setSeeker_qualification(String seeker_qualification) {
                this.seeker_qualification = seeker_qualification;
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

            public String getArea_of_sector() {
                return area_of_sector;
            }

            public void setArea_of_sector(String area_of_sector) {
                this.area_of_sector = area_of_sector;
            }

            public String getWork_experience() {
                return work_experience;
            }

            public void setWork_experience(String work_experience) {
                this.work_experience = work_experience;
            }

            public String getExperience_in_year() {
                return experience_in_year;
            }

            public void setExperience_in_year(String experience_in_year) {
                this.experience_in_year = experience_in_year;
            }

            public String getExperience_in_months() {
                return experience_in_months;
            }

            public void setExperience_in_months(String experience_in_months) {
                this.experience_in_months = experience_in_months;
            }

            public String getSpecialization() {
                return specialization;
            }

            public void setSpecialization(String specialization) {
                this.specialization = specialization;
            }

            public String getRole_type() {
                return role_type;
            }

            public void setRole_type(String role_type) {
                this.role_type = role_type;
            }

            public String getCertification() {
                return certification;
            }

            public void setCertification(String certification) {
                this.certification = certification;
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
        }

    }



