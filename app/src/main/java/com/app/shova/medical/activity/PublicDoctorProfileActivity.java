package com.app.shova.medical.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.shova.medical.R;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.data.preference.AppPreference;
import com.app.shova.medical.data.preference.PrefKey;
import com.app.shova.medical.model.Appointment;
import com.app.shova.medical.model.Doctor;
import com.app.shova.medical.utility.ActivityUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PublicDoctorProfileActivity extends BaseActivity {
    private TextView tvName, tvEmail, tvPhone, tvDescription, tvSpecialist, tvEmailOne,txtQuote;
    private ImageView imgDocPro;
    private Activity mActivity;
    private Context mContext;
    private Button btnCallDoctor, btnApoint;
    private Doctor doctor;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initVariable();
        initToolbar();
        initFunctionality();


    }


    private void initVariable() {
        mActivity = PublicDoctorProfileActivity.this;
        mContext = getApplicationContext();
        Bundle extraDetail = getIntent().getExtras();
        doctor = (Doctor) extraDetail.getSerializable(AppConstants.KEY_PASS_INTENT);
        setDetailData(doctor);
        setToolbarTitle(doctor.getdName());
        initListener(doctor);
        //firebase initializer
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(AppConstants.DOCTOR_APPOINTMENT_REF);
    }

    private void initView() {
        setContentView(R.layout.activity_public_doctor_profile);

        tvName = findViewById(R.id.textView_name_doc);
        tvEmailOne = findViewById(R.id.textView_email_doctor_one);
        tvEmail = findViewById(R.id.textView_email_doc);
        tvPhone = findViewById(R.id.textView_phone_doc);
        tvDescription = findViewById(R.id.textView_doctor_description);
        tvSpecialist = findViewById(R.id.textView_speciality_doc);
        txtQuote=findViewById(R.id.textView_qoute);

        imgDocPro = findViewById(R.id.imageView_doctor);

        btnApoint = findViewById(R.id.btn_apoint_to_doctor);
        btnCallDoctor = findViewById(R.id.btn_call_doctor);
        initLoader();
    }


    private void initFunctionality() {
    }

    private void initListener(Doctor doctor) {
        btnCallDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().invokePhoneCall(mActivity, doctor.getdPhone());
            }
        });

        btnApoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppPreference.getInstance(mContext).getBoolean(PrefKey.IS_LOGIN)){
                    if(AppPreference.getInstance(mContext).getString(PrefKey.PREF_USER_TYPE).equalsIgnoreCase(AppConstants.USER_TYPE_PREF_VALUE_USER)){
                        createAppointDialoge();
                    }
                    else {
                        Toast.makeText(mContext, "please signUp or login as a User", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(mContext, "login or sign up first", Toast.LENGTH_SHORT).show();
                    ActivityUtils.getInstance().invokeLoginActivity(mActivity,SignInActivity.class,true);
                }

            }
        });
    }

    private void setDetailData(Doctor doctor) {
        showLoader();
        tvName.setText(doctor.getdName());
        tvEmail.setText(doctor.getdEmail());
        tvEmailOne.setText(doctor.getdEmail());
        tvPhone.setText(doctor.getdPhone());
        tvDescription.setText(doctor.getdDescription());
        tvSpecialist.setText(doctor.getdSpecialist());
        txtQuote.setText("If you can't explain it simply you don't\\n understand it well enough.\n   --"+doctor.getdName());
        //Loading image from Glide library.
        Glide.with(mActivity).load(doctor.getImgUrl()).into(imgDocPro);
        hideLoader();
    }


    private void createAppointDialoge() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
        View mView = getLayoutInflater().inflate(R.layout.dialoge_appoint, null);
        EditText eSyndrome = mView.findViewById(R.id.syndrome);
        TextView btnSend = mView.findViewById(R.id.txt_send_data);
        TextView btnCancel = mView.findViewById(R.id.txt_cancel);
        String uName = AppPreference.getInstance(mContext).getString(PrefKey.PREF_USER_EMAIL);
        String dname = doctor.getdEmail();
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoader();
                if (!eSyndrome.equals(null)) {

                    // by implementing firebase auth
                    if (TextUtils.isEmpty(userId)) {
                        userId = mFirebaseDatabase.push().getKey();
                    }

                    Appointment appointment = new Appointment(dname, uName, eSyndrome.getText().toString());

                    mFirebaseDatabase.child(userId).setValue(appointment);

                    //addUserChangeListener();
                    dialog.dismiss();
                    hideLoader();
                } else {
                    eSyndrome.setError("field can't be empty to send Syndrome");
                    hideLoader();
                }


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Appointment user = dataSnapshot.getValue(Appointment.class);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }


}
