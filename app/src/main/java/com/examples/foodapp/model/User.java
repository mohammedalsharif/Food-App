package com.examples.foodapp.model;

public class User {
    private String imageUrl;
    private String uId;
    private String uName;
    private String uEmail;
    private String uPassword;


    public User() {
    }

    public User(String uId,String imageUrl, String uName, String uEmail, String uPassword) {
        this.uId = uId;
        this.imageUrl=imageUrl;
        this.uName = uName;
        this.uEmail = uEmail;
        this.uPassword = uPassword;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }
}
