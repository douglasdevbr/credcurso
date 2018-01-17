
package com.pixelnx.sam.jobportal.DTO;

import java.io.Serializable;

public class UserRecruiterDTO extends BaseDTO implements Serializable {

    private int code;
    private boolean status;
    private String message = "";
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
        String organisation_name = "";
        String recruiter_email = "";
        String recruiter_mobile_no = "";
        String recruiter_profile_update = "";
        String recruiter_verified = "";
        String token = "";
        String created_at = "";
        String updated_at = "";
        String jwt_token = "";
        String role = "";
        Recruiter_Profile recruiter_profile;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrganisation_name() {
            return organisation_name;
        }

        public void setOrganisation_name(String organisation_name) {
            this.organisation_name = organisation_name;
        }

        public String getRecruiter_email() {
            return recruiter_email;
        }

        public void setRecruiter_email(String recruiter_email) {
            this.recruiter_email = recruiter_email;
        }

        public String getRecruiter_mobile_no() {
            return recruiter_mobile_no;
        }

        public void setRecruiter_mobile_no(String recruiter_mobile_no) {
            this.recruiter_mobile_no = recruiter_mobile_no;
        }

        public String getRecruiter_profile_update() {
            return recruiter_profile_update;
        }

        public void setRecruiter_profile_update(String recruiter_profile_update) {
            this.recruiter_profile_update = recruiter_profile_update;
        }

        public String getRecruiter_verified() {
            return recruiter_verified;
        }

        public void setRecruiter_verified(String recruiter_verified) {
            this.recruiter_verified = recruiter_verified;
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

        public Recruiter_Profile getRecruiter_profile() {
            return recruiter_profile;
        }

        public void setRecruiter_profile(Recruiter_Profile recruiter_profile) {
            this.recruiter_profile = recruiter_profile;
        }

        public class Recruiter_Profile {
            String id = "";
            String recruiter_id = "";
            String i_am = "";
            String org_location = "";
            String org_address = "";
            String org_website = "";
            String org_discription = "";
            String org_logo = "";
            String created_at = "";
            String updated_at = "";

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

            public String getI_am() {
                return i_am;
            }

            public void setI_am(String i_am) {
                this.i_am = i_am;
            }

            public String getOrg_location() {
                return org_location;
            }

            public void setOrg_location(String org_location) {
                this.org_location = org_location;
            }

            public String getOrg_address() {
                return org_address;
            }

            public void setOrg_address(String org_address) {
                this.org_address = org_address;
            }

            public String getOrg_website() {
                return org_website;
            }

            public void setOrg_website(String org_website) {
                this.org_website = org_website;
            }

            public String getOrg_discription() {
                return org_discription;
            }

            public void setOrg_discription(String org_discription) {
                this.org_discription = org_discription;
            }

            public String getOrg_logo() {
                return org_logo;
            }

            public void setOrg_logo(String org_logo) {
                this.org_logo = org_logo;
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


}

