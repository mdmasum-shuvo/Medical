package com.app.shova.medical.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.shova.medical.R;
import com.app.shova.medical.adapter.DoctorListAdapter;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.data.sqlite.FavoriteDoctorDbController;
import com.app.shova.medical.listener.OnItemClickListener;
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


public class DoctorFragment extends Fragment {

    private RecyclerView recycleDoctorList;
    private ArrayList<Doctor> doctorList;
    private DoctorListAdapter doctorListAdapter;
    private Activity mActivity;
    private Context mContext;
    private FirebaseDatabase mDatabse;
    private DatabaseReference mRef;
    private FavoriteDoctorDbController dbController;
    private SearchView searchDoctor;
    private ImageView imgFav, imgUnfav, imgCall;
    private ArrayList<Doctor> doctorsFilterList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.doctor_list_layout, container, false);

        initView(rootView);
        initVariable(rootView);
        initFunctionality();
        initListener();

        return rootView;
    }

    private void initView(View rootView) {
        imgFav = rootView.findViewById(R.id.favorite_icon_doctor);
        imgUnfav = rootView.findViewById(R.id.unfavorite_icon_doctor);
        searchDoctor = rootView.findViewById(R.id.searchView);
        imgCall = rootView.findViewById(R.id.doctor_phone_call);
        recycleDoctorList = rootView.findViewById(R.id.recycleView_doctor_list);
        recycleDoctorList.setHasFixedSize(true);
        recycleDoctorList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    private void initVariable(View rootView) {
        mActivity = getActivity();
        mContext = getActivity().getApplicationContext();
        dbController=new FavoriteDoctorDbController(mContext);
        mDatabse = FirebaseDatabase.getInstance();
        mRef = mDatabse.getReference(AppConstants.DOCTOR_FIREBASE_REF);
        doctorList = new ArrayList<>();
        doctorsFilterList = new ArrayList<>();
        doctorListAdapter = new DoctorListAdapter(mActivity, doctorList);
        recycleDoctorList.setAdapter(doctorListAdapter);
        ((DragScrollBar) rootView.findViewById(R.id.dragScrollBar))
                .setIndicator(new AlphabetIndicator(mActivity), true);


    }

    private void initListener() {
        doctorListAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemListener(View view, int position) {
                if (!doctorsFilterList.isEmpty()) {
                    doctorsFilterList.clear();
                }
                doctorsFilterList.addAll(doctorListAdapter.doctorList);
                switch (view.getId()) {
                    case R.id.textView_doctor_name:
                        sendDataToDetail(position);
                        break;
                    case R.id.favorite_icon_doctor:
                        dbController.deleteFavoriteItem(doctorsFilterList.get(position).getId());
                        break;
                    case R.id.unfavorite_icon_doctor:
                        long insert= dbController.insertData(doctorsFilterList.get(position).getdImgUrl(),doctorsFilterList.get(position).getdName(),
                                doctorsFilterList.get(position).getdEmail(),doctorsFilterList.get(position).getdPhone(),
                                doctorsFilterList.get(position).getdSpecialist(),doctorsFilterList.get(position).getdDescription());

                        if (insert>0){
                            Toast.makeText(mActivity, "favorite:"+insert, Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(mActivity, "click on Un favorite icon:", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.doctor_phone_call:
                        Toast.makeText(mActivity, "click on call button:", Toast.LENGTH_SHORT).show();
                        ActivityUtils.getInstance().invokePhoneCall(mActivity,doctorsFilterList.get(position).getdPhone());
                        break;
                    default:
                        Toast.makeText(mActivity, "click on View", Toast.LENGTH_SHORT).show();
                        sendDataToDetail(position);
                        break;
                }

            }
        });

        searchDoctor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPEse
                doctorListAdapter.getFilter().filter(query);

                return false;
            }
        });

    }

    private void initFunctionality() {
        getFirebaseData();
    }

    private void getFirebaseData() {

        Log.e("call function", "get firebase data");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!doctorList.isEmpty()) {
                    doctorList.clear();
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String dImage = snapshot.child(AppConstants.dIMAGE_URL_FIELD).getValue().toString();
                    String dName = snapshot.child(AppConstants.dNAME_FIELD).getValue().toString();
                    String dSpecialist = snapshot.child(AppConstants.dSPECIALIST_FIELD).getValue().toString();
                    String dEmail = snapshot.child(AppConstants.dEMAIL_FIELD).getValue().toString();
                    String dDescription = snapshot.child(AppConstants.dDESCRIPTION_FIELD).getValue().toString();
                    String dPhone = snapshot.child(AppConstants.dPHONE_FIELD).getValue().toString();

                    doctorList.add(new Doctor(dImage, dName, dEmail, dPhone, dSpecialist, dDescription));

                }
                if (!AppConstants.DOCTOR_LIST.isEmpty()){
                    AppConstants.DOCTOR_LIST.clear();
                }
                AppConstants.DOCTOR_LIST.addAll(doctorList);
                doctorListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error", "" + databaseError);
            }
        });
    }

    private void sendDataToDetail(int position) {
        Doctor doctorDetail = new Doctor(doctorsFilterList.get(position).getImgUrl(), doctorsFilterList.get(position).getdName(),
                doctorsFilterList.get(position).getdEmail(), doctorsFilterList.get(position).getdPhone(),
                doctorsFilterList.get(position).getdSpecialist(), doctorsFilterList.get(position).getdDescription());
        ActivityUtils.invokeDoctorDetails(mActivity, doctorDetail);

    }

}
