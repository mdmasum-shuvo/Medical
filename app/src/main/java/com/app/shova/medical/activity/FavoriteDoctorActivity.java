package com.app.shova.medical.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.shova.medical.R;
import com.app.shova.medical.adapter.FavoriteDoctorAdapter;
import com.app.shova.medical.data.sqlite.FavoriteDoctorDbController;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Doctor;
import com.app.shova.medical.utility.ActivityUtils;
import com.app.shova.medical.utility.DividerItemDecoration;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import java.util.ArrayList;
import java.util.Collections;

public class FavoriteDoctorActivity extends BaseActivity {
    private Context mContext;
    private Activity mActivity;

    private RecyclerView recyclefavoriteDoctorList;
    private ArrayList<Doctor> favDoctorList;
    private FavoriteDoctorAdapter favoriteDoctorAdapter;
    private FavoriteDoctorDbController dbController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
        initView();
        loadFavoriteData();
        initToolbar();
        enableBackButton();
        initListener();
        // add toolbar title
        setToolbarTitle("Favorite Doctor");

    }

    private void initView() {
        setContentView(R.layout.activity_favorite_doctor);
        recyclefavoriteDoctorList = findViewById(R.id.recycleView_favorite_doctor);
        recyclefavoriteDoctorList.setHasFixedSize(true);
        recyclefavoriteDoctorList.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclefavoriteDoctorList.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL, 16));
        favoriteDoctorAdapter = new FavoriteDoctorAdapter(this, favDoctorList);
        recyclefavoriteDoctorList.setAdapter(favoriteDoctorAdapter);
        ((DragScrollBar)findViewById(R.id.dragScrollBar_favorite))
                .setIndicator(new AlphabetIndicator(this), true);
        // init loader
        initLoader();

    }

    private void initVariable() {
        mContext = getApplicationContext();
        mActivity = FavoriteDoctorActivity.this;
        dbController = new FavoriteDoctorDbController(mActivity);
        favDoctorList = new ArrayList<>();
    }

    private void loadFavoriteData() {
        if (!favDoctorList.isEmpty()) {
            favDoctorList.clear();
        }
        favDoctorList.addAll(dbController.getAllData());
        Collections.sort(favDoctorList, (Doctor s1, Doctor s2) -> {
            return s1.getdName().compareToIgnoreCase(s2.getdName());
        });
        if (favDoctorList.isEmpty()){
            showEmptyView();
        }
        else {
            hideLoader();
        }
    }


    private void initListener() {

        favoriteDoctorAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemListener(View viewItem, int position) {
                switch (viewItem.getId()) {
                    case R.id.icon_unfav_doctor:
                        dbController.deleteFavoriteItem(favDoctorList.get(position).getId());
                        favDoctorList.remove(position);
                        favoriteDoctorAdapter.notifyDataSetChanged();
                        loadFavoriteData();
                        break;

                    default:
                        sendDataToDetail(position);
                        break;
                }

            }
        });

        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void sendDataToDetail(int position) {
        Doctor doctorDetail = new Doctor(favDoctorList.get(position).getImgUrl(), favDoctorList.get(position).getdName(),
                favDoctorList.get(position).getdEmail(), favDoctorList.get(position).getdPhone(),
                favDoctorList.get(position).getdSpecialist(), favDoctorList.get(position).getdDescription());
        ActivityUtils.invokeDoctorDetails(mActivity, doctorDetail);
    }


}
