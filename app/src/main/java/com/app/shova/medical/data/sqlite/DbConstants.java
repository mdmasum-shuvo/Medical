package com.app.shova.medical.data.sqlite;

/**
 * Created by Masum on 3/11/2018.
 */

public class DbConstants {

    // commons
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    public static final String COLUMN_NAME_NULLABLE = null;
    // favourites
    public static final String IMPORTANT_TABLE_MEDICINE_NAME = "favourite_medicine";
    public static final String HISTORY_TABLE_MEDICINE_NAME = "history_medicine";
    public static final String FAVORITE_DOCTOR_TABLE_NAME = "doctor_favorite";
    public static final String HISTORY_DOCTOR_TABLE_NAME = "doctor_history";

    //medicine
    public static final String _M_ID = "_id";
    public static final String M_NAME_FIELD = "m_name";
    public static final String M_TYPE_FILED = "m_type";
    public static final String M_GENRIC_FIELD = "m_genric";
    public static final String M_DESCRIPTION_FILED = "m_description";
    public static final String M_COMPANY_FIELD = "m_company";
    public static final String M_PRICE_FIELD = "m_price";

    //doctor  dImgUrl,dName,dEmail,dPhone,dSpecialist,dDescription;

    public static final String _D_ID = "_id";
    public static final String D_NAME_FIELD = "d_name";
    public static final String D_EMAIL_FILED = "d_email";
    public static final String D_PHONE_FIELD = "d_phone";
    public static final String D_SPECIALIST_FILED = "d_specialist";
    public static final String D_DESCRIPTION_FIELD = "d_description";
    public static final String D_IMG_URL_FIELD = "d_img_url";


    //important medicine table sql
    public static final String SQL_CREATE_FAVOURITE_MEDICINE_ENTRIES =
            "CREATE TABLE " + IMPORTANT_TABLE_MEDICINE_NAME + " (" +
                    _M_ID + " INTEGER PRIMARY KEY," +
                    M_NAME_FIELD + TEXT_TYPE + COMMA_SEP +
                    M_GENRIC_FIELD + TEXT_TYPE + COMMA_SEP +
                    M_TYPE_FILED + TEXT_TYPE + COMMA_SEP +
                    M_DESCRIPTION_FILED + TEXT_TYPE + COMMA_SEP +
                    M_COMPANY_FIELD + TEXT_TYPE + COMMA_SEP +
                    M_PRICE_FIELD + TEXT_TYPE + " )";
    public static final String SQL_DELETE_FAVOURITE_ENTRIES =
            "DROP TABLE IF EXISTS " + IMPORTANT_TABLE_MEDICINE_NAME;

    //history medicine table sql
    public static final String SQL_CREATE_HISTORY_MEDICINE_ENTRIES =
            "CREATE TABLE " + HISTORY_TABLE_MEDICINE_NAME + " (" +
                    _M_ID + " INTEGER PRIMARY KEY," +
                    M_NAME_FIELD + TEXT_TYPE + COMMA_SEP +
                    M_GENRIC_FIELD + TEXT_TYPE + COMMA_SEP +
                    M_TYPE_FILED + TEXT_TYPE + COMMA_SEP +
                    M_DESCRIPTION_FILED + TEXT_TYPE + COMMA_SEP +
                    M_COMPANY_FIELD + TEXT_TYPE + COMMA_SEP +
                    M_PRICE_FIELD + TEXT_TYPE + " )";
    public static final String SQL_DELETE_HISTORY_MEDICINE_ENTRIES =
            "DROP TABLE IF EXISTS " + HISTORY_TABLE_MEDICINE_NAME;


    //doctor favorite table sql
    public static final String SQL_CREATE_DOCTOR_FAVORITE_ENTRIES =
            "CREATE TABLE " + FAVORITE_DOCTOR_TABLE_NAME + " (" +
                    _D_ID + " INTEGER PRIMARY KEY," +
                    D_NAME_FIELD + TEXT_TYPE + COMMA_SEP +
                    D_EMAIL_FILED + TEXT_TYPE + COMMA_SEP +
                    D_PHONE_FIELD + TEXT_TYPE + COMMA_SEP +
                    D_SPECIALIST_FILED + TEXT_TYPE + COMMA_SEP +
                    D_DESCRIPTION_FIELD + TEXT_TYPE + COMMA_SEP +
                    D_IMG_URL_FIELD + TEXT_TYPE + " )";
    public static final String SQL_DELETE_DOCTOR_FAVORITE_ENTRIES =
            "DROP TABLE IF EXISTS " + FAVORITE_DOCTOR_TABLE_NAME;

    //doctor history table sql
    public static final String SQL_CREATE_DOCTOR_HISTORY_ENTRIES =
            "CREATE TABLE " + HISTORY_DOCTOR_TABLE_NAME + " (" +
                    _D_ID + " INTEGER PRIMARY KEY," +
                    D_NAME_FIELD + TEXT_TYPE + COMMA_SEP +
                    D_EMAIL_FILED + TEXT_TYPE + COMMA_SEP +
                    D_PHONE_FIELD + TEXT_TYPE + COMMA_SEP +
                    D_SPECIALIST_FILED + TEXT_TYPE + COMMA_SEP +
                    D_DESCRIPTION_FIELD + TEXT_TYPE + COMMA_SEP +
                    D_IMG_URL_FIELD + TEXT_TYPE + " )";
    public static final String SQL_DELETE_DOCTOR_HISTORY_ENTRIES =
            "DROP TABLE IF EXISTS " + HISTORY_DOCTOR_TABLE_NAME;


}
