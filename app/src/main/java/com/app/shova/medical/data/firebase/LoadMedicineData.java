package com.app.shova.medical.data.firebase;

import android.content.Context;
import android.util.Log;

import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.listener.FirebaseDataLoadListener;
import com.app.shova.medical.model.Medicine;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Masum on 4/6/2018.
 */

public class LoadMedicineData {

    private Context context;
    FirebaseDatabase mDatabse;
    DatabaseReference mRef;
    final ArrayList<Medicine> medicineList = new ArrayList<>();
    public static FirebaseDataLoadListener mListener;

    public LoadMedicineData(Context context) {
        this.context = context;
    }


    public void getMedicineData() {
        mDatabse = FirebaseDatabase.getInstance();
        mRef = mDatabse.getReference(AppConstants.FIREBASE_MEDICINE_REF_KEY);
        Log.e("call function","get firebase data");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String mName = snapshot.child(AppConstants.mNAME_FIELD).getValue().toString();
                    String mType = snapshot.child(AppConstants.mTYPE_FIELD).getValue().toString();
                    String mGenric = snapshot.child(AppConstants.mGENRIC_FIELD).getValue().toString();
                    String mDescription = snapshot.child(AppConstants.mDESCRIPTION_FIELD).getValue().toString();
                    String mCompany = snapshot.child(AppConstants.mCOMPANY_FIELD).getValue().toString();
                    String mPrice = snapshot.child(AppConstants.mPRICE_FIELD).getValue().toString();
                    Medicine medicine=new Medicine(mName,mType,mGenric,mDescription,mCompany,mPrice);
                    medicineList.add(medicine);

                }
                    mListener.finishMedicineLoadData(medicineList,true);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error",""+databaseError);
            }
        });
    }

    public void setClickListener(FirebaseDataLoadListener mListener) {
        this.mListener = mListener;
    }
}
