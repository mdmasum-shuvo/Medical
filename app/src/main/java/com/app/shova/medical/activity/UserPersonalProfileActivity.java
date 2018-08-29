package com.app.shova.medical.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.shova.medical.R;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.data.preference.AppPreference;
import com.app.shova.medical.data.preference.PrefKey;
import com.app.shova.medical.utility.ActivityUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserPersonalProfileActivity extends BaseActivity {
    private Activity mActivity;
    private Context mContext;
    private TextView tvName, tvEmail1, tvEmail2, tvAddress, tvPhone, tvBloofGrp, tvAge;
    private ImageView imgPatient;
    private LinearLayout btnMyDoctor, btnMyPrescription;
    private FirebaseDatabase mDatabse;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initvariable();
        initView();
        initListener();
        initFunctionality();


    }

    private void initvariable() {
        mActivity = UserPersonalProfileActivity.this;
        mContext = getApplicationContext();
        mDatabse = FirebaseDatabase.getInstance();
        mRef = mDatabse.getReference(AppConstants.FIREBASE_USER_REF_KEY);
    }

    private void initView() {
        setContentView(R.layout.activity_user_personal_profile);
        btnMyDoctor = findViewById(R.id.my_doctor_layout);
        btnMyPrescription = findViewById(R.id.my_prescription_layout);
        tvName = findViewById(R.id.textView_name_patient);
        tvEmail1 = findViewById(R.id.textView_email_patient_one);
        tvEmail2 = findViewById(R.id.textView_email_patient);
        tvAddress = findViewById(R.id.textView_Adress_patient);
        tvPhone = findViewById(R.id.textView_phone_patient);
        tvAge = findViewById(R.id.textView_age_patient);
        tvBloofGrp = findViewById(R.id.textView_blood_group_patient);
        imgPatient = findViewById(R.id.imageView_patient);
    }

    private void initListener() {
        btnMyDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().invokeActivity(mActivity,FavoriteDoctorActivity.class,false);

            }
        });

        btnMyPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().invokeActivity(mActivity, PatientPrescriptionActivity.class, false);

            }
        });
    }

    private void initFunctionality() {

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child(AppConstants.USER_EMAIL).getValue().
                            toString().equalsIgnoreCase(AppPreference.
                            getInstance(mContext).getString(PrefKey.PREF_USER_EMAIL))) {
                        String email= snapshot.child(AppConstants.USER_EMAIL).getValue().toString();
                        String name= snapshot.child(AppConstants.USER_NAME).getValue().toString();
                        String address= snapshot.child(AppConstants.USER_ADDRESS).getValue().toString();
                        String phone= snapshot.child(AppConstants.USER_PHONE).getValue().toString();
                        String age= snapshot.child(AppConstants.USER_AGE).getValue().toString();
                        String bGroup= snapshot.child(AppConstants.USER_BLOODGROUP).getValue().toString();
                        String image=snapshot.child(AppConstants.USER_IMG_URL).getValue().toString();

                        setDetail(image,name,email,phone,address,age,bGroup);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setDetail(String image, String name, String email, String phone, String address, String age, String bGroup) {

        tvName.setText(name);
        tvEmail1.setText(email);
        tvEmail2.setText(email);
        tvPhone.setText(phone);
        tvAddress.setText(address);
        tvAge.setText(age);
        tvBloofGrp.setText(bGroup);
        //Loading image from Glide library.
        Glide.with(mContext).load(image).placeholder(R.color.monsoon).into(imgPatient);
    }

}
