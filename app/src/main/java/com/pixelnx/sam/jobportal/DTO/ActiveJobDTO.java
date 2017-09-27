
package com.pixelnx.sam.jobportal.DTO;

import java.io.Serializable;
import java.util.ArrayList;

public class ActiveJobDTO implements Serializable {

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

    public class Data implements Serializable {
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

        Posted_Recruiter posted_recruiter;

        String job_type_id = "";
        String job_by_roles_id = "";
        String qualification_id = "";
        String job_location_id = "";
        String specialization_id = "";
        boolean is_applied=false;

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

        public Posted_Recruiter getPosted_recruiter() {
            return posted_recruiter;
        }

        public void setPosted_recruiter(Posted_Recruiter posted_recruiter) {
            this.posted_recruiter = posted_recruiter;
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

        public boolean is_applied() {
            return is_applied;
        }

        public void setIs_applied(boolean is_applied) {
            this.is_applied = is_applied;
        }
    }

        public class Posted_Recruiter implements Serializable {
            String id = "";
            String organisation_name = "";
            String recruiter_email = "";
            String recruiter_mobile_no = "";
            String recruiter_profile_update = "";
            String recruiter_verified = "";
            String token = "";
            String created_at = "";
            String updated_at = "";
            String device_id = "";
            String device_token = "";
            String device_type = "";
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

            public String getDevice_id() {
                return device_id;
            }

            public void setDevice_id(String device_id) {
                this.device_id = device_id;
            }

            public String getDevice_token() {
                return device_token;
            }

            public void setDevice_token(String device_token) {
                this.device_token = device_token;
            }

            public String getDevice_type() {
                return device_type;
            }

            public void setDevice_type(String device_type) {
                this.device_type = device_type;
            }

            public Recruiter_Profile getRecruiter_profile() {
                return recruiter_profile;
            }

            public void setRecruiter_profile(Recruiter_Profile recruiter_profile) {
                this.recruiter_profile = recruiter_profile;
            }
        }

            public class Recruiter_Profile implements Serializable {
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






