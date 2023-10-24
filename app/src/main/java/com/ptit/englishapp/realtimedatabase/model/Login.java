package com.ptit.englishapp.realtimedatabase.model;

import java.util.Date;

public class Login {

    private String id;
    private String uuid;
    private long loginTime;

    public Login() {
    }

    public Login(String id, String uuid, long loginTime) {
        this.id = id;
        this.uuid = uuid;
        this.loginTime = loginTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }
}
