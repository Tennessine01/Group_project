package com.ptit.englishapp.realtimedatabase.model;

/*
* 1 node là thông tin cá nhân của người dùng gồm uuid, họ tên, địa chỉ,
* email, số điện thoại, sở thích
*
* */

public class User {
    private String uid;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private String favorite;

    public User(String uid, String name, String address, String email, String phoneNumber, String favorite) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.favorite = favorite;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
}
