package com.app.shova.medical.appConstant;

import com.app.shova.medical.model.Doctor;
import com.app.shova.medical.model.Medicine;
import com.app.shova.medical.model.User;

import java.util.ArrayList;

/**
 * Created by Masum on 3/8/2018.
 */

public class AppConstants {

    //request code
    public static final int REQ_CODE_SPEECH_INPUT = 100;
    public static final int REQUEST_CODE_SIGNUP = 0;
    public static final int DOCTOR_IMAGE_REQ_CODE = 1;
    public static final int USER_IMAGE_REQ_CODE = 2;
    public static final int DOCTOR_REQUEST_IMAGE_CAPTURE = 3;
    public static final int USER_REQUEST_IMAGE_CAPTURE=4;

    //time event
    public static final int LOADING_TIME_DELAY=2000;
    public static final int SPLASH_TIME=2000;


    //firebase reference constant
    public static final String dSTORAGE_PATH="doctor/";
    public static final String dDATABASE_PATH="doctor";

    public static final String uSTORAGE_PATH="user/";
    public static final String uDATABASE_PATH="user";
    public static final String LOGIN_REFERENCE="login";

    //firebase medicine constant
    public static final String mNAME_FIELD="name";
    public static final String mTYPE_FIELD="type";
    public static final String mDESCRIPTION_FIELD="description";
    public static final String mGENRIC_FIELD="genric";
    public static final String mCOMPANY_FIELD="company";
    public static final String mPRICE_FIELD="price";
    //firebase doctor constant
    public static final String dIMAGE_URL_FIELD="imgUrl";
    public static final String dNAME_FIELD="dName";
    public static final String dEMAIL_FIELD="dEmail";
    public static final String dDESCRIPTION_FIELD="dDescription";
    public static final String dPHONE_FIELD="dPhone";
    public static final String dSPECIALIST_FIELD="dSpecialist";

    //firebase appointment constant
    public static final String APPOINT_DOCTOR_NAME="dName";
    public static final String APPOINT_PATIENT_NAME="uName";
    public static final String APPOINT_SYNDROME="syndrome";


    //firebase user constant
    public static final String USER_NAME="uName";
    public static final String USER_ADDRESS="uAddress";
    public static final String USER_BLOODGROUP="uBg";
    public static final String USER_AGE="uAge";
    public static final String USER_GENDER="uGender";
    public static final String USER_EMAIL="uEmail";
    public static final String USER_PHONE="uPhone";
    public static final String USER_IMG_URL="uImg";


    //
    public static final String KEY_PASS_INTENT ="doctor";
    //Constant data
    public static final String[] LOGIN_PERSON={"select one","doctor","user"};
    public static final String[] GENDER_SPINNER={"male","female"};
    public static final String TAG_LOGING_ACTIVITY = "LoginActivity";


    public static final String FIREBASE_MEDICINE_REF_KEY ="medicine";

    //string utils
    public static final String SELECT_IMAGE_TITLE ="Please Select Image";
    public static final String DOCTOR_FIREBASE_REF ="doctor";
    public static final String DOCTOR_APPOINTMENT_REF ="appointment";
    public static final String USER_TYPE_PREF_VALUE_DOCTOR = "doctor";
    public static final String USER_TYPE_PREF_VALUE_USER= "user";
    public static final String USER_TYPE_DOCTOR = "doctor";


    public static final String[] TIME_A_DAY ={"1","2","3","4"} ;
    public static final String DOCTOR_PRESCRIPTION_REF ="prescription" ;
    public static final String FIREBASE_USER_REF_KEY ="user" ;
    public static final String PRESCRIPTION_MEDICIINE_NAME_FILELD ="mName" ;
    public static final String PRESCRIPTION_MEDICIINE_TIMES ="time" ;

    //ArrayList
    public static ArrayList<Doctor> DOCTOR_LIST=new ArrayList<>();
    public static ArrayList<User> USER_LIST=new ArrayList<>();
    public static ArrayList<Medicine> MEDICINE_LIST=new ArrayList<>();
}
