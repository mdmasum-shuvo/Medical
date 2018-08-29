package com.app.shova.medical.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.shova.medical.R;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.model.Medicine;

public class DetailActivity extends AppCompatActivity {
    private TextView tvVoice, tvName, tvDescription, tvType, tvPrice, tvGenric, tvCompany;
    ImageView ivUnFavorite, ivFavorite;
    Medicine wordDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initVariable();
    }

    private void initVariable() {
        Bundle extraDetail = getIntent().getExtras();
        wordDetail = (Medicine) extraDetail.getSerializable(AppConstants.KEY_PASS_INTENT);
        setAllData(wordDetail);
    }

    private void initView() {
        setContentView(R.layout.activity_detail);
        ivUnFavorite = findViewById(R.id.un_favorite_medicine_icon);
        ivFavorite = findViewById(R.id.favorite_medicine_icon);
        tvName = findViewById(R.id.textview_m_name);
        tvType =findViewById(R.id.textview_m_type);
        tvGenric=findViewById(R.id.textview_m_genric);
        tvDescription =findViewById(R.id.textview_m_description);
        tvPrice = findViewById(R.id.textview_m_price);
        tvCompany = findViewById(R.id.textview_m_company);
    }

    private void setAllData(Medicine medicine) {
        tvName.setText(medicine.getmName());
        tvType.setText(medicine.getmType());
        tvGenric.setText(medicine.getmGenric());
        tvPrice.setText(medicine.getmPrice());
        tvDescription.setText(medicine.getmDescription());
        tvCompany.setText(medicine.getmCompany());
    }
}
