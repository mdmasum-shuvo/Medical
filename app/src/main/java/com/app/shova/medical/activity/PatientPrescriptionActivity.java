package com.app.shova.medical.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.shova.medical.R;
import com.app.shova.medical.adapter.PatientPrescriptionAdapter;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.data.preference.AppPreference;
import com.app.shova.medical.data.preference.PrefKey;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Prescription;
import com.app.shova.medical.utility.ActivityUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientPrescriptionActivity extends BaseActivity {
    private RecyclerView recycleDoctorDescription, recyclePrescription;

    private Activity mActivity;
    private Context mContext;
    private FirebaseDatabase mDatabse;
    private DatabaseReference mAppointRef, mPrescriptionKeyRef, getmPrescriptionDataRef;
    private ArrayList<Prescription> prescriptionsList;

    private PatientPrescriptionAdapter patientPrescriptionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
        initView();
        initToolbar();
        setToolbarTitle("Prescription");
        initListener();
        initFunctionality();


    }


    private void initVariable() {
        mActivity = PatientPrescriptionActivity.this;
        mContext = getApplicationContext();
        mDatabse = FirebaseDatabase.getInstance();
        mAppointRef = mDatabse.getReference(AppConstants.DOCTOR_APPOINTMENT_REF);
        mPrescriptionKeyRef = mDatabse.getReference(AppConstants.DOCTOR_PRESCRIPTION_REF);
        prescriptionsList = new ArrayList<>();
        patientPrescriptionAdapter = new PatientPrescriptionAdapter(mContext, prescriptionsList);
    }

    private void initView() {
        setContentView(R.layout.activity_patient_prescription);

//        recycleDoctorDescription =findViewById(R.id.recycle_doctorDescription);
//        recycleDoctorDescription.setHasFixedSize(true);
//        recycleDoctorDescription.setLayoutManager(new LinearLayoutManager(mActivity));

        recyclePrescription = findViewById(R.id.recycle_patient_prescription);
        recyclePrescription.setHasFixedSize(true);
        recyclePrescription.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclePrescription.setAdapter(patientPrescriptionAdapter);

    }

    private void initListener() {

        patientPrescriptionAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemListener(View view, int position) {
                switch (view.getId()) {
                    case R.id.button_set_alarm:
                        ActivityUtils.getInstance().invokExtraString(mActivity, SetAlarmActivity.class, false, prescriptionsList.get(position).getTime());
                        break;
                }
            }
        });
    }


    private void initFunctionality() {
        if (!prescriptionsList.isEmpty()) {
            prescriptionsList.clear();
        }

        mAppointRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String patientName = AppPreference.getInstance(mContext).getString(PrefKey.PREF_USER_EMAIL);

                    if (snapshot.child(AppConstants.APPOINT_PATIENT_NAME).getValue().toString().equalsIgnoreCase(patientName)) {
                        String appointKey = snapshot.getKey().toString();
                        mPrescriptionKeyRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                    if (appointKey.equalsIgnoreCase(snapshot1.getKey().toString()))

                                    {
                                        getmPrescriptionDataRef = mDatabse.getReference(AppConstants.DOCTOR_PRESCRIPTION_REF + "/" + appointKey);
                                        getmPrescriptionDataRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {
                                                    String mName = snapshot2.child(AppConstants.PRESCRIPTION_MEDICIINE_NAME_FILELD).getValue().toString();
                                                    String time = snapshot2.child(AppConstants.PRESCRIPTION_MEDICIINE_TIMES).getValue().toString();
                                                    Prescription prescription = new Prescription(mName, time);
                                                    prescriptionsList.add(prescription);

                                                }

                                                patientPrescriptionAdapter.notifyDataSetChanged();

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
