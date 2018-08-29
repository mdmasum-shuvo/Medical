package com.app.shova.medical.model;


import java.io.Serializable;

public class Medicine implements Serializable{
    private int id;
    private String mName,mType,mGenric,mDescription,mCompany,mPrice;

    public Medicine(String mName, String mType, String mGenric, String mDescription, String mCompany, String mPrice) {
        this.mName = mName;
        this.mType = mType;
        this.mGenric = mGenric;
        this.mDescription = mDescription;
        this.mCompany = mCompany;
        this.mPrice = mPrice;
    }


    public Medicine(int id, String mName, String mType, String mGenric, String mDescription, String mCompany, String mPrice) {
        this.id = id;
        this.mName = mName;
        this.mType = mType;
        this.mGenric = mGenric;
        this.mDescription = mDescription;
        this.mCompany = mCompany;
        this.mPrice = mPrice;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmGenric() {
        return mGenric;
    }

    public void setmGenric(String mGenric) {
        this.mGenric = mGenric;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmCompany() {
        return mCompany;
    }

    public void setmCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }
}
