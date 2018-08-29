package com.app.shova.medical.model;

import java.io.Serializable;

/**
 * Created by Masum on 3/10/2018.
 */

public class Doctor implements Serializable{
    private int id;
    private String  dImgUrl,dName,dEmail,dPhone,dSpecialist,dDescription;

    public Doctor(String dImgUrl, String dName, String dEmail, String dPhone, String dSpecialist, String dDescription) {
        this.dName = dName;
        this.dEmail = dEmail;
        this.dPhone = dPhone;
        this.dImgUrl = dImgUrl;
        this.dSpecialist = dSpecialist;
        this.dDescription = dDescription;
    }

    public Doctor(int id, String dImgUrl, String dName, String dEmail, String dPhone, String dSpecialist, String dDescription) {
        this.id=id;
        this.dName = dName;
        this.dEmail = dEmail;
        this.dPhone = dPhone;
        this.dImgUrl = dImgUrl;
        this.dSpecialist = dSpecialist;
        this.dDescription = dDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getdImgUrl() {
        return dImgUrl;
    }

    public void setdImgUrl(String dImgUrl) {
        this.dImgUrl = dImgUrl;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdEmail() {
        return dEmail;
    }

    public void setdEmail(String dEmail) {
        this.dEmail = dEmail;
    }

    public String getdPhone() {
        return dPhone;
    }

    public void setdPhone(String dPhone) {
        this.dPhone = dPhone;
    }

    public String getdSpecialist() {
        return dSpecialist;
    }

    public void setdSpecialist(String dSpecialist) {
        this.dSpecialist = dSpecialist;
    }

    public String getdDescription() {
        return dDescription;
    }

    public void setdDescription(String dDescription) {
        this.dDescription = dDescription;
    }

    public String getImgUrl() {
        return dImgUrl;
    }

    public void setImgUrl(String dImgUrl) {
        this.dImgUrl = dImgUrl;
    }
}
