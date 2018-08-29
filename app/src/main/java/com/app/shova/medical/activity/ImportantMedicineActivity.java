package com.app.shova.medical.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.app.shova.medical.R;
import com.app.shova.medical.adapter.ImMedicineAdapter;
import com.app.shova.medical.data.sqlite.ImportantmDbController;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Medicine;
import com.app.shova.medical.utility.DividerItemDecoration;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;
import java.util.ArrayList;
import java.util.Collections;

public class ImportantMedicineActivity extends BaseActivity {

    private Context mContext;
    private Activity mActivity;

    private RecyclerView recycleMedicineLIst;
    private ArrayList<Medicine> imMedicineList;
    private ImMedicineAdapter imMedicineAdapter;
    private ImportantmDbController dbController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
        initView();
        initToolbar();
        setToolbarTitle("Important Medicine");
        enableBackButton();
        initFuntionality();
        initListener();
    }

    private void initView() {
        setContentView(R.layout.activity_important_medicine);
        recycleMedicineLIst = findViewById(R.id.recycleView_favorite_doctor);
        recycleMedicineLIst.setHasFixedSize(true);
        recycleMedicineLIst.setLayoutManager(new LinearLayoutManager(this));
        recycleMedicineLIst.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        imMedicineAdapter = new ImMedicineAdapter(this, imMedicineList);
        recycleMedicineLIst.setAdapter(imMedicineAdapter);
        ((DragScrollBar)findViewById(R.id.dragScrollBar_favorite))
                .setIndicator(new AlphabetIndicator(this), true);
    }

    private void initVariable() {

        mContext = getApplicationContext();
        mActivity = ImportantMedicineActivity.this;
        dbController = new ImportantmDbController(mActivity);
        imMedicineList = new ArrayList<>();
    }

    private void initFuntionality() {
        loadFavoriteData();
    }


    private void initListener() {

        imMedicineAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemListener(View viewItem, int position) {
                switch (viewItem.getId()) {
                    case R.id.icon_delete_important_medicine:
                        dbController.deleteFavoriteItem(imMedicineList.get(position).getId());
                        imMedicineList.remove(position);
                        imMedicineAdapter.notifyDataSetChanged();
                        loadFavoriteData();
                        break;

                    case R.id.icon_voice_im_medicine:
                        // speakOut(position);
                        break;
                    default:
                        //sendDataToDetail(position);
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



    //    private void sendDataToDetail(int position) {
//        WordDetail wordDetail=new WordDetail(favWordList.get(position).getWord(),
//                favWordList.get(position).getMeaning(),favWordList.get(position).getType(),
//                favWordList.get(position).getSynonym(),favWordList.get(position).getAntonym(),
//                favWordList.get(position).getExample());
//        ActivityUtils.invokeWordDetails(mActivity,wordDetail);
//
//    }
    private void loadFavoriteData() {
        if (!imMedicineList.isEmpty()) {
            imMedicineList.clear();
        }
        imMedicineList.addAll(dbController.getAllData());
        Collections.sort(imMedicineList, (Medicine s1, Medicine s2) -> {
            return s1.getmName().compareToIgnoreCase(s2.getmName());
        });
        imMedicineAdapter.notifyDataSetChanged();
        if (imMedicineList.isEmpty()){
            showEmptyView();
        }
        else {
            hideLoader();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
