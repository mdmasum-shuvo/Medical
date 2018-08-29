package com.app.shova.medical.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.app.shova.medical.R;
import com.app.shova.medical.adapter.MedicineHistoryAdapter;
import com.app.shova.medical.data.sqlite.MedicineHistoryDbController;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Medicine;
import com.app.shova.medical.utility.DividerItemDecoration;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import java.util.ArrayList;
import java.util.Collections;

public class MedicineHistoryListActivity extends BaseActivity {
    private Context mContext;
    private Activity mActivity;

    private RecyclerView recycleMedicineLIst;
    private ArrayList<Medicine> historyMedicineList;
    private MedicineHistoryAdapter medicineHistoryAdapter;
    private MedicineHistoryDbController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
        initView();
        initToolbar();
        setToolbarTitle("Medicine History");
        enableBackButton();
        initFuntionality();
        initListener();
    }

    private void initVariable() {
        mContext = getApplicationContext();
        mActivity = MedicineHistoryListActivity.this;
        dbController = new MedicineHistoryDbController(mActivity);
        historyMedicineList = new ArrayList<>();
    }

    private void initView() {
        setContentView(R.layout.activity_medicine_history_list);
        recycleMedicineLIst = (RecyclerView) findViewById(R.id.recycleView_history);
        recycleMedicineLIst.setHasFixedSize(true);
        recycleMedicineLIst.setLayoutManager(new LinearLayoutManager(this));
        recycleMedicineLIst.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        medicineHistoryAdapter = new MedicineHistoryAdapter(this, historyMedicineList);
        recycleMedicineLIst.setAdapter(medicineHistoryAdapter);
        ((DragScrollBar) findViewById(R.id.dragScrollBar_history))
                .setIndicator(new AlphabetIndicator(this), true);

    }

    private void initFuntionality() {
        loadHistoryData();
    }


    private void initListener() {

        medicineHistoryAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemListener(View viewItem, int position) {
                switch (viewItem.getId()) {
                    case R.id.icon_delete_important_medicine:
                        dbController.deleteHistoryItem(historyMedicineList.get(position).getId());
                        historyMedicineList.remove(position);
                        medicineHistoryAdapter.notifyDataSetChanged();
                        loadHistoryData();
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

    }

    private void loadHistoryData() {
        if (!historyMedicineList.isEmpty()) {
            historyMedicineList.clear();
        }
        historyMedicineList.addAll(dbController.getAllMedicineData());
        Collections.sort(historyMedicineList, (Medicine s1, Medicine s2) -> {
            return s1.getmName().compareToIgnoreCase(s2.getmName());
        });
        medicineHistoryAdapter.notifyDataSetChanged();
        if (historyMedicineList.isEmpty()){
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
