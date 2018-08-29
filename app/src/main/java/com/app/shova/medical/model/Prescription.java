package com.app.shova.medical.model;

public class Prescription {

    String appointId,mName,time;

    public Prescription(String appointId, String mName, String time) {
        this.appointId = appointId;
        this.mName = mName;
        this.time = time;
    }

    public Prescription(String mName, String time) {
        this.mName = mName;
        this.time = time;
    }

    public String getAppointId() {
        return appointId;
    }

    public void setAppointId(String appointId) {
        this.appointId = appointId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
