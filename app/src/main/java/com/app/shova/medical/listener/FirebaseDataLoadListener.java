package com.app.shova.medical.listener;

import com.app.shova.medical.model.Medicine;
import com.app.shova.medical.model.User;

import java.util.ArrayList;

/**
 * Created by Masum on 4/6/2018.
 */

public interface FirebaseDataLoadListener {
    void finishMedicineLoadData(ArrayList<Medicine> dataList, boolean isSuccessful);
}
