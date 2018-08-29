package com.app.shova.medical.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.shova.medical.R;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.model.User;
import com.bumptech.glide.Glide;

public class PublicUserProfileActivity extends BaseActivity {
    private TextView tvName,tvEmail,tvPhone, tvAddress, tvGender,tvEmailOne;
    private ImageView imgDocPro;
    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
        initView();
        initToolbar();
        initListener();
        initFunctionality();


    }

    private void initView() {
        setContentView(R.layout.activity_public_user_profile);

    }

    private void initVariable(){
        mActivity=PublicUserProfileActivity.this;
//        Bundle extraDetail=getIntent().getExtras();
//        User user= (User) extraDetail.getSerializable(AppConstants.KEY_PASS_INTENT);
//        setDetailData(user);
//        setToolbarTitle(user.getuName());

    }

    private void initFunctionality() {
    }

    private void initListener() {
    }

    private void setDetailData(User user) {

        tvName.setText(user.getuName());
        tvEmail.setText(user.getuEmail());
        tvEmailOne.setText(user.getuEmail());
        tvPhone.setText(user.getuPhone());
        tvAddress.setText(user.getuAddress());
        tvGender.setText(user.getuGender());
        //Loading image from Glide library.
        Glide.with(this).load(user.getuImg()).into(imgDocPro);
    }

}
