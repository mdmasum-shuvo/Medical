package com.app.shova.medical.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.shova.medical.model.Doctor;

import java.util.ArrayList;

public class FavoriteDoctorDbController {

    private SQLiteDatabase db;
    private Context mContext;

    public FavoriteDoctorDbController(Context context) {
        db = DbHelper.getInstance(context).getWritableDatabase();
        mContext = context;
    }


    //doctor  dImgUrl,dName,dEmail,dPhone,dSpecialist,dDescription;
    public int insertData(String dImgUrl, String dName, String dEmail, String dPhone, String dSpecialist, String dDescription) {

        ContentValues values = new ContentValues();
        values.put(DbConstants.D_IMG_URL_FIELD, dImgUrl);
        values.put(DbConstants.D_NAME_FIELD, dName);
        values.put(DbConstants.D_EMAIL_FILED, dEmail);
        values.put(DbConstants.D_PHONE_FIELD, dPhone);
        values.put(DbConstants.D_SPECIALIST_FILED, dSpecialist);
        values.put(DbConstants.D_DESCRIPTION_FIELD, dDescription);

        // Insert the new row, returning the primary key value of the new row
        return (int) db.insert(
                DbConstants.FAVORITE_DOCTOR_TABLE_NAME,
                DbConstants.COLUMN_NAME_NULLABLE,
                values);
    }

    public ArrayList<Doctor> getAllData() {

        String[] projection = {
                DbConstants._D_ID,
                DbConstants.D_IMG_URL_FIELD,
                DbConstants.D_NAME_FIELD,
                DbConstants.D_EMAIL_FILED,
                DbConstants.D_PHONE_FIELD,
                DbConstants.D_SPECIALIST_FILED,
                DbConstants.D_DESCRIPTION_FIELD,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DbConstants._D_ID + " DESC";

        Cursor c = db.query(
                DbConstants.FAVORITE_DOCTOR_TABLE_NAME,  // The table name to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return fetchData(c);
    }


    private ArrayList<Doctor> fetchData(Cursor c) {
        ArrayList<Doctor> favDataArray = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    // get  the  data into array,or class variable
                    //doctor  dImgUrl,dName,dEmail,dPhone,dSpecialist,dDescription;
                    int dId = c.getInt(c.getColumnIndexOrThrow(DbConstants._D_ID));
                    String dImgUrl = c.getString(c.getColumnIndexOrThrow(DbConstants.D_IMG_URL_FIELD));
                    String dName = c.getString(c.getColumnIndexOrThrow(DbConstants.D_NAME_FIELD));
                    String dEmail = c.getString(c.getColumnIndexOrThrow(DbConstants.D_EMAIL_FILED));
                    String dPhone = c.getString(c.getColumnIndexOrThrow(DbConstants.D_PHONE_FIELD));
                    String dSpecialist = c.getString(c.getColumnIndexOrThrow(DbConstants.D_SPECIALIST_FILED));
                    String dDescription = c.getString(c.getColumnIndexOrThrow(DbConstants.D_DESCRIPTION_FIELD));



                    // wrap up data list and return
                    favDataArray.add(new Doctor(dId, dImgUrl, dName, dEmail, dPhone, dSpecialist, dDescription));
                } while (c.moveToNext());
            }
            c.close();
        }
        return favDataArray;
    }

    public void deleteFavoriteItem(int itemId) {
        // Which row to update, based on the ID
        String selection = DbConstants._M_ID + "=?";
        String[] selectionArgs = {String.valueOf(itemId)};

        db.delete(
                DbConstants.FAVORITE_DOCTOR_TABLE_NAME,
                selection,
                selectionArgs);
    }

    public void deleteAllFav() {
        db.delete(
                DbConstants.FAVORITE_DOCTOR_TABLE_NAME,
                null,
                null);
    }
}
