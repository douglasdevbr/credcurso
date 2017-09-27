package com.pixelnx.sam.jobportal.utils;

/**
 * Created by shubham on 19/7/17.
 */

public interface Consts {
    public static String JOB_PORTAL = "JobPortal";
    public static String BASE_URL = "http://phpstack-104300-296238.cloudwaysapps.com/api/";
    public static String IS_REGISTER_SEEKER = "is_register_seeker";
    public static String IS_REGISTER_RECRUITER = "is_register_recruiter";

    //http://phpstack-104300-296238.cloudwaysapps.com/api/fill-seeker-profile
    /*API DETAILS*/
    public static String POST_METHOD = "POST";
    public static String SIGNUP_API_SEEKER = "register-new-seeker";
    public static String SIGNUP_API_RECRUITER = "register-new-recruiter";
    public static String LOGIN_API_SEEKER = "login-seeker";
    public static String LOGIN_API_RECRUITER = "recruiter-login";
    public static String ACTIVE_JOBS_API = "search-job";
    public static String APPLY_JOB_API = "apply-on-job";
    public static String POST_JOB_API = "post-job";
    public static String GENERAL = "general ";
    public static String FILL_SEEKER_PROFILE = "fill-seeker-profile";
    public static String FILL_RECRUITER_PROFILE = "fill-recruiter-profile";
    public static String SEEKER_CHANGE_PASSWORD = "seeker-change-password ";
    public static String RECRUITER_CHANGE_PASSWORD = "recruiter-change-password ";
    public static String FORGOT_PASSWORD = "forgot-password ";
    public static String GET_RECRUITER_JOBS = "get-recruiter-jobs";
    public static String JOB_APPLICATION = "job-application";
    public static String DELETE_JOB = "delete-job";
    public static String SEEKER_PROFILE_DETAIL_ON_JOB = "seeker-profile-detail-on-job";
    public static String GET_RECRUITER_JOB_DETAIL = "get-recruiter-job-detail";


    /*API DETAILS*/


    /*PROJECT DATA*/

    public static String SEEKER_DTO = "seeker_dto";
    public static String ET_NAME = "et_name";
    public static String RECRUITER_DTO = "recruiter_dto";
    public static String ACTIVE_JOBS_DTO = "active_jobs_dto";
    public static String RECRUITER_PROFILE_DTO = "recruiter_profile_dto";


    String IMAGE_URI_CAMERA = "image_uri_camera";
    String JOB_SINGLE_DATA = "job_single";
    String FLAG = "flag";
    String TOKAN = "tokan";
    String TAG_GUEST = "guest";

    /*PROJECT DATA*/


    /*Signup parameter FOR SEEKER*/
    String FULL_NAME = "full_name";
    String EMAIL = "email";
    String MOBILE_NO = "mobile_no";
    // String PASSWORD = "password";
    /*Signup parameter FOR SEEKER*/


    /*Signup parameter FOR RECRUITER*/
    String ORGANISATION_NAME = "organisation_name";
    String RECRUITER_EMAIL = "recruiter_email";
    String PASSWORD = "password";
    String RECRUITER_MOBILE_NO = "recruiter_mobile_no";
    /*Signup parameter FOR RECRUITER*/


    /* Login parameter FOR SEEKER AND RECRUITER */
    String VALUE = "value";
    String VALUE_RECRUITER = "value_recruiter";
    String DEVICE_ID = "device_id";
    String DEVICE_TOKEN = "device_token";
    String DEVICE_TYPE = "device_type";
    /* Login parameter FOR SEEKER AND RECRUITER */


    /*ACTIVE_JOBS & APPLY_JOB PARAMETER*/
    String SEEKER_ID = "seeker_id";
    String JOB_ID = "job_id";
   /*ACTIVE JOBS & APPLY_JOB PARAMETER*/

    /*UPDATE PROFILE SEEKER*/
    String GENDER = "gender";
    String AVTAR = "avtar";
    String CURRENT_ADDRESS = "current_address";
    String PREFERRED_LOCATION = "preferred_location";
    String JOB_TYPE = "job_type";
    String SEEKER_QUALIFICATION = "seeker_qualification";
    String YEAR_OF_PASSING = "year_of_passing";
    String PERCENTAGE_OR_CGPA = "percentage_or_cgpa";
    String AREA_OF_SECTOR = "area_of_sector";
    String WORK_EXPERIENCE = "work_experience";
    String EXPERIENCE_IN_YEAR = "experience_in_year";
    String EXPERIENCE_IN_MONTHS = "experience_in_months";
    String SPECIALIZATION = "specialization";
    String ROLE_TYPE = "role_type";
    String CERTIFICATION = "certification";
    String RESUME = "resume";
    /*UPDATE PROFILE SEEKER*/


    /*UPDATE PROFILE RECRUITER*/
    String RECRUITER_ID = "recruiter_id";
    String I_AM = "i_am";
    String ORG_LOGO = "org_logo";
    String ORG_LOCATION = "org_location";
    String ORG_ADDRESS = "org_address";
    String ORG_WEBSITE = "org_website";
    String ORG_DISCRIPTION = "org_discription";

    /*UPDATE PROFILE RECRUITER*/


    /*CHANGE PASSWORD FOR SEEKER*/
    //String SEEKER_ID = "seeker_id";
    String OLD_PASSWORD = "old_password";
    String NEW_PASSWORD = "new_password";
    /*CHANGE PASSWORD FOR SEEKER*/


    /*POST JOB PARAMETER*/
    //String RECRUITER_ID = "recruiter_id";
    // String JOB_TYPE = "job_type";
    String JOB_BY_ROLES = "job_by_roles";
    String QUALIFICATION = "qualification";
    String JOB_LOCATION = "job_location";
    // String YEAR_OF_PASSING = "year_of_passing";
    // String PERCENTAGE_OR_CGPA = "percentage_or_cgpa";
    String SKILLS_REQUIRED = "skills_required";
    String EXPERIENCE = "experience";
    String JOB_DISCRIPTION = "job_discription";
    String MIN_SAL = "min_sal";
    String MAX_SAL = "max_sal";
    String PER = "per";
    String VACANCIES = "vacancies";
    String LAST_DATE = "last_date";
    String PROCESS = "process";

    /*POST JOB PARAMETER*/


    /*FORGOT PASSWORD */
    String ROLE = "role";
    // String EMAIL = "email";

    /*FORGOT PASSWORD*/

    /*Filter*/
    //   String JOB_LOCATION = "job_location";
    //String SPECIALIZATION = "specialization";
    //String QUALIFICATION = "qualification";
    //String JOB_BY_ROLES = "job_by_roles";
    //  String AREA_OF_SECTOR = "area_of_sector";
    //  String EXPERIENCE = "experience";
    // String JOB_TYPE = "job_type";
/*Filter*/


    String CAMERA_ACCEPTED = "camera_accepted";
    String STORAGE_ACCEPTED = "storage_accepted";
    String NETWORK_ACCEPTED = "network_accepted";
    String CALL_PR_ACCEPTED = "call_pr_accepted";
    String CALL_PH_ACCEPTED = "call_ph_accepted";
    String READ_PH_ACCEPTED = "read_ph_accepted";

}
