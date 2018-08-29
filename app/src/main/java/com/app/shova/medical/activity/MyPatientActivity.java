package com.app.shova.medical.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.app.shova.medical.R;
import com.app.shova.medical.adapter.DoctorListAdapter;
import com.app.shova.medical.adapter.PatientListAdapter;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.data.preference.AppPreference;
import com.app.shova.medical.data.preference.PrefKey;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Appointment;
import com.app.shova.medical.model.Doctor;
import com.app.shova.medical.utility.ActivityUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import java.util.ArrayList;

public class MyPatientActivity extends BaseActivity {

    private RecyclerView recyclePatientList;
    private ArrayList<Appointment> patientList;
    private PatientListAdapter patientListAdapter;
    private Activity mActivity;
    private Context mContext;
    private FirebaseDatabase mDatabse;
    private DatabaseReference mRef;
    private SearchView searchPatient;
    private ArrayList<Doctor> patientFilterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
        initView();
        initToolbar();
        setToolbarTitle("Appoint Patient List");
        initListener();
        initFunctionality();

    }

    private void initVariable() {
        mActivity=MyPatientActivity.this;
        mContext=getApplicationContext();
        patientList=new ArrayList<>();
        patientFilterList=new ArrayList<>();
        patientListAdapter = new PatientListAdapter(mActivity, patientList);
        mDatabse = FirebaseDatabase.getInstance();
        mRef = mDatabse.getReference(AppConstants.DOCTOR_APPOINTMENT_REF);
    }

    private void initView() {
        setContentView(R.layout.activity_my_patient);
        searchPatient=findViewById(R.id.searchView);
        recyclePatientList =findViewById(R.id.recycleView_patient_list);
        recyclePatientList.setHasFixedSize(true);
        recyclePatientList.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclePatientList.setAdapter(patientListAdapter);
        ((DragScrollBar)findViewById(R.id.dragScrollBar))
                .setIndicator(new AlphabetIndicator(mActivity), true);
    }

    private void initListener() {
        patientListAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemListener(View view, int position) {
                switch (view.getId()){
                    case R.id.btn_prescrip:
                        sendDataToPrescriptActivity(position);
                        break;
                    default:
                        sendDataToPrescriptActivity(position);
                        break;
                }


            }
        });
    }

    private void initFunctionality() {

        getFirebaseData();
    }


    private void getFirebaseData() {
        if (!patientList.isEmpty()) {
            patientList.clear();
        }
        Log.e("call function", "get firebase data");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (AppPreference.getInstance(mContext).getString(PrefKey.PREF_USER_EMAIL).
                            equalsIgnoreCase(snapshot.child(AppConstants.APPOINT_DOCTOR_NAME).getValue().toString())){

                        String appointId=snapshot.getKey().toString();
                        String appointDemail = snapshot.child(AppConstants.APPOINT_DOCTOR_NAME).getValue().toString();
                        String appointPemail = snapshot.child(AppConstants.APPOINT_PATIENT_NAME).getValue().toString();
                        String syndrome = snapshot.child(AppConstants.APPOINT_SYNDROME).getValue().toString();
                        patientList.add(new Appointment(appointDemail,appointPemail,syndrome,appointId));
                    }



                }

                patientListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error", "" + databaseError);
            }
        });
    }

    private void sendDataToPrescriptActivity(int position) {
        Appointment appointDetail = new Appointment(patientList.get(position).getdName(),patientList.get(position).getuName(),patientList.get(position).getSyndrome(),patientList.get(position).getApointId());
        ActivityUtils.invokeAppointDetails(mActivity, appointDetail);

    }

}
