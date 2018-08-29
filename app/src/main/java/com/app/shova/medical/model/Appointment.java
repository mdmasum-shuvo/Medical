package com.app.shova.medical.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Appointment implements Serializable{

    private String dName,uName, syndrome,apointId;

    public Appointment(String dName, String uName, String syndrome) {
        this.dName = dName;
        this.uName = uName;
        this.syndrome = syndrome;
    }

    public Appointment(String dName, String uName, String syndrome, String apointId) {
        this.dName = dName;
        this.uName = uName;
        this.syndrome = syndrome;
        this.apointId = apointId;
    }


    public String getApointId() {
        return apointId;
    }

    public void setApointId(String apointId) {
        this.apointId = apointId;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getSyndrome() {
        return syndrome;
    }

    public void setSyndrome(String syndrome) {
        this.syndrome = syndrome;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("dName", dName);
        result.put("uName", uName);
        result.put("syndrome", syndrome);


        return result;
    }
}
