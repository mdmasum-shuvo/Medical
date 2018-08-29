package com.app.shova.medical.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.app.shova.medical.activity.DetailActivity;
import com.app.shova.medical.activity.DoctorPrescriptionActivity;
import com.app.shova.medical.activity.PublicDoctorProfileActivity;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.model.Appointment;
import com.app.shova.medical.model.Doctor;
import com.app.shova.medical.model.Medicine;

/**
 * Created by Masum on 3/20/2018.
 */

public class ActivityUtils {

    private static ActivityUtils sActivityUtils = null;

    public static ActivityUtils getInstance() {
        if (sActivityUtils == null) {
            sActivityUtils = new ActivityUtils();
        }
        return sActivityUtils;
    }

    public void invokeActivity(Activity activity, Class<?> tClass, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }

    public void invokeLoginActivity(Activity activity, Class<?> tClass, boolean shouldFinish){
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }

    public void invokExtraString(Activity activity, Class<?> tClass, boolean shouldFinish,String data){
        Bundle bundle=new Bundle();
        bundle.putString(AppConstants.KEY_PASS_INTENT,data);
        Intent intent = new Intent(activity, tClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }


    public static void invokeDoctorDetails(Activity activity, Doctor doctorDetail) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.KEY_PASS_INTENT, doctorDetail);
        Intent intent = new Intent(activity, PublicDoctorProfileActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);

    }

    public static void invokeAppointDetails(Activity activity, Appointment appointment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.KEY_PASS_INTENT, appointment);
        Intent intent = new Intent(activity, DoctorPrescriptionActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    public void invokePhoneCall(Activity mActivity, String contactNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + contactNumber));

        if (ActivityCompat.checkSelfPermission(mActivity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CALL_PHONE}, 2);

            return;
        }
        mActivity.startActivity(callIntent);
}


    public static void invokeWordDetails(Activity activity, Medicine wordDetail){
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.KEY_PASS_INTENT, wordDetail);
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

}
