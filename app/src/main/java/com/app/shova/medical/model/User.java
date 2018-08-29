package com.app.shova.medical.model;

/**
 * Created by Masum on 3/30/2018.
 */

public class User {
    private int id;
    private  String uImg,uName,uEmail,uPhone,uAge,uAddress,uBg,uGender;

    public User(String uImg,String uName, String uEmail, String uPhone, String uAge, String uAddress, String uBg, String uGender) {
        this.uImg = uImg;
        this.uName = uName;
        this.uEmail = uEmail;
        this.uPhone = uPhone;
        this.uAge = uAge;
        this.uAddress = uAddress;
        this.uBg = uBg;
        this.uGender = uGender;
    }

    public User(int id,String uImg,String uName, String uEmail, String uPhone, String uAge, String uAddress, String uBg, String uGender) {
        this.id = id;
        this.uImg = uImg;
        this.uName = uName;
        this.uEmail = uEmail;
        this.uPhone = uPhone;
        this.uAge = uAge;
        this.uAddress = uAddress;
        this.uBg = uBg;
        this.uGender = uGender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getuImg() {
        return uImg;
    }

    public void setuImg(String uImg) {
        this.uImg = uImg;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getuAge() {
        return uAge;
    }

    public void setuAge(String uAge) {
        this.uAge = uAge;
    }

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public String getuBg() {
        return uBg;
    }

    public void setuBg(String uBg) {
        this.uBg = uBg;
    }

    public String getuGender() {
        return uGender;
    }

    public void setuGender(String uGender) {
        this.uGender = uGender;
    }
}
