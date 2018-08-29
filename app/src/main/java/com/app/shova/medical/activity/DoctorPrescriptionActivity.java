package com.app.shova.medical.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.app.shova.medical.R;
import com.app.shova.medical.adapter.PrescriptionAdapter;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.model.Appointment;
import com.app.shova.medical.model.Medicine;
import com.app.shova.medical.model.Prescription;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class DoctorPrescriptionActivity extends BaseActivity {
    private FloatingActionButton fabAdd;
    private ImageView imgSavePrescription;
    private ArrayList<Medicine> allMedicineList;
    private ArrayList<String> allMedicineNameList;
    private ArrayList<Prescription> writtenPrecript;
    private ArrayAdapter<String> adapterMedicine, adapterTimes;
    private PrescriptionAdapter prescriptionAdapter;
    private RecyclerView recyclerViewPrescription;
    private MaterialBetterSpinner timesSpinner;
    private AutoCompleteTextView etSearchMedicine;
    private Activity mActivity;
    private Context mContext;
    private String userId;
    private ProgressDialog progressDialog;
    private FirebaseDatabase mDatabse;
    private DatabaseReference mRefPrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
        initView();
        initToolbar();
        setToolbarTitle("Write Prescription");
        initListener();
        initFunctionality();


    }

    private void initVariable() {
        mActivity = DoctorPrescriptionActivity.this;
        mContext = getApplicationContext();
        allMedicineList = new ArrayList<>();
        allMedicineNameList = new ArrayList<>();
        writtenPrecript = new ArrayList<>();
        if (!allMedicineList.isEmpty()) {
            allMedicineList.clear();
        }
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setIndeterminate(true);
        Bundle extraDetail = getIntent().getExtras();
        Appointment appointment = (Appointment) extraDetail.getSerializable(AppConstants.KEY_PASS_INTENT);
        mDatabse = FirebaseDatabase.getInstance();
        mRefPrescription = mDatabse.getReference(AppConstants.DOCTOR_PRESCRIPTION_REF + "/" + appointment.getApointId());
        allMedicineList.addAll(AppConstants.MEDICINE_LIST);
    }

    private void initView() {
        setContentView(R.layout.activity_prescription);
        imgSavePrescription = findViewById(R.id.btn_save_prescription);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab);
        timesSpinner = findViewById(R.id.spinner_times);
        etSearchMedicine = findViewById(R.id.searchBox);
        etSearchMedicine.setThreshold(1);
        adapterMedicine = new ArrayAdapter<String>(mContext, android.R.layout.select_dialog_item, allMedicineNameList);
        etSearchMedicine.setAdapter(adapterMedicine);

        adapterTimes = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, AppConstants.TIME_A_DAY);
        timesSpinner.setAdapter(adapterTimes);

        recyclerViewPrescription = findViewById(R.id.recycleView_prescription);
        recyclerViewPrescription.setHasFixedSize(true);
        recyclerViewPrescription.setLayoutManager(new LinearLayoutManager(mActivity));
        prescriptionAdapter = new PrescriptionAdapter(mActivity, writtenPrecript);
        recyclerViewPrescription.setAdapter(prescriptionAdapter);

    }

    private void initListener() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                writtenPrecript.add(new Prescription(etSearchMedicine.getText().toString(), timesSpinner.getText().toString()));
                prescriptionAdapter.notifyDataSetChanged();
                etSearchMedicine.equals("");

            }
        });


        imgSavePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Setting progressDialog Title.
                progressDialog.setTitle("Prescription Sending...");

                // Showing progressDialog.
                progressDialog.show();

                for (int i = 0; i < writtenPrecript.size(); i++) {
                    userId = null;
                    if (TextUtils.isEmpty(userId)) {
                        userId = mRefPrescription.push().getKey();
                    }
                    Prescription prescription = new Prescription(writtenPrecript.get(i).getmName(), writtenPrecript.get(i).getTime());
                    mRefPrescription.child(userId).setValue(prescription);
                }

                progressDialog.dismiss();

            }
        });
    }

    private void initFunctionality() {
        addMedicineListTOAutoComplete();
    }


    private void addMedicineListTOAutoComplete() {
        for (int i = 0; i < allMedicineList.size(); i++) {
            allMedicineNameList.add(allMedicineList.get(i).getmName());
        }
        Log.e("all word", "" + allMedicineNameList);
        adapterMedicine.notifyDataSetChanged();


    }

}
