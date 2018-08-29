package com.app.shova.medical.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.shova.medical.model.Medicine;

import java.util.ArrayList;

/**
 * Created by Masum on 4/17/2018.
 */

public class ImportantmDbController {

    private SQLiteDatabase db;
    private Context mContext;

    public ImportantmDbController(Context context) {
        db = DbHelper.getInstance(context).getWritableDatabase();
        mContext = context;
    }

    public int insertData(String mName, String mType, String mGenric, String mDescription, String mCompany, String mPrice) {

        ContentValues values = new ContentValues();
        values.put(DbConstants.M_NAME_FIELD, mName);
        values.put(DbConstants.M_GENRIC_FIELD, mGenric);
        values.put(DbConstants.M_TYPE_FILED, mType);
        values.put(DbConstants.M_DESCRIPTION_FILED, mDescription);
        values.put(DbConstants.M_COMPANY_FIELD, mCompany);
        values.put(DbConstants.M_PRICE_FIELD, mPrice);

        // Insert the new row, returning the primary key value of the new row
        return (int) db.insert(
                DbConstants.IMPORTANT_TABLE_MEDICINE_NAME,
                DbConstants.COLUMN_NAME_NULLABLE,
                values);
    }

    public ArrayList<Medicine> getAllData() {


        String[] projection = {
                DbConstants._M_ID,
                DbConstants.M_NAME_FIELD,
                DbConstants.M_GENRIC_FIELD,
                DbConstants.M_TYPE_FILED,
                DbConstants.M_DESCRIPTION_FILED,
                DbConstants.M_COMPANY_FIELD,
                DbConstants.M_PRICE_FIELD,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DbConstants._M_ID + " DESC";

        Cursor c = db.query(
                DbConstants.IMPORTANT_TABLE_MEDICINE_NAME,  // The table name to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return fetchData(c);
    }

    private ArrayList<Medicine> fetchData(Cursor c) {
        ArrayList<Medicine> favDataArray = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    // get  the  data into array,or class variable
                    int mItemId = c.getInt(c.getColumnIndexOrThrow(DbConstants._M_ID));
                    String mName = c.getString(c.getColumnIndexOrThrow(DbConstants.M_NAME_FIELD));
                    String mType = c.getString(c.getColumnIndexOrThrow(DbConstants.M_TYPE_FILED));
                    String mGenric = c.getString(c.getColumnIndexOrThrow(DbConstants.M_GENRIC_FIELD));
                    String mDescription = c.getString(c.getColumnIndexOrThrow(DbConstants.M_DESCRIPTION_FILED));
                    String mCompany = c.getString(c.getColumnIndexOrThrow(DbConstants.M_COMPANY_FIELD));
                    String mPrice = c.getString(c.getColumnIndexOrThrow(DbConstants.M_PRICE_FIELD));


                    // wrap up data list and return
                    favDataArray.add(new Medicine(mItemId, mName, mGenric, mType, mDescription, mCompany, mPrice));
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
                DbConstants.IMPORTANT_TABLE_MEDICINE_NAME,
                selection,
                selectionArgs);
    }

    public void deleteAllFav() {
        db.delete(
                DbConstants.IMPORTANT_TABLE_MEDICINE_NAME,
                null,
                null);
    }

}
