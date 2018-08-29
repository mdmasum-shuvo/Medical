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
import com.app.shova.medical.model.Doctor;
import com.app.shova.medical.utility.ActivityUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DoctorPersonalProfileActivity extends BaseActivity {
    private TextView tvName,tvEmail,tvPhone,tvDescription,tvSpecialist,tvEmailOne;
    private ImageView imgDocPro;
    private Activity mActivity;
    private Context mContext;
    private ArrayList<Doctor> doctorList;
    private LinearLayout llBtnMyPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initToolbar();

        initVariable();
        initListener();
        initFunctionality();
//        backToHome(toolbar);
    }

    private void initVariable() {
        mActivity=DoctorPersonalProfileActivity.this;
        mContext=getApplicationContext();
        doctorList=new ArrayList<>();
        doctorList.addAll(AppConstants.DOCTOR_LIST);
    }


    private void initView() {
        setContentView(R.layout.activity_doctor_personal_profile);
        tvName=findViewById(R.id.textView_name_doc);
        tvEmailOne=findViewById(R.id.textView_email_doctor_one);
        tvEmail=findViewById(R.id.textView_email_doc);
        tvPhone=findViewById(R.id.textView_phone_doc);
        tvDescription=findViewById(R.id.textView_qoute);
        tvSpecialist=findViewById(R.id.textView_speciality_doc);
        imgDocPro=findViewById(R.id.imageView_doctor);
        llBtnMyPatient =findViewById(R.id.layout_mypatient);
        initLoader();
    }


    private void initFunctionality() {
        showLoader();
        for (int i=0;i<doctorList.size();i++){
            if(doctorList.get(i).getdEmail().equalsIgnoreCase(AppPreference.getInstance(mContext).getString(PrefKey.PREF_USER_EMAIL))){
                setDetailData(i);
                break;
            }
        }
    }

    private void initListener() {
        llBtnMyPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().invokeActivity(mActivity,MyPatientActivity.class,false);
            }
        });

    }

    private void setDetailData(int i) {

        tvName.setText(doctorList.get(i).getdName());
        tvEmail.setText(doctorList.get(i).getdEmail());
        tvEmailOne.setText(doctorList.get(i).getdEmail());
        tvPhone.setText(doctorList.get(i).getdPhone());
        tvDescription.setText(doctorList.get(i).getdDescription());
        tvSpecialist.setText(doctorList.get(i).getdSpecialist());
        //Loading image from Glide library.
        Glide.with(this).load(doctorList.get(i).getImgUrl()).into(imgDocPro);
        setToolbarTitle(doctorList.get(i).getdName());

        hideLoader();
    }





}


