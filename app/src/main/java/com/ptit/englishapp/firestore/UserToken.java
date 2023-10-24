package com.ptit.englishapp.firestore;

public class UserToken {
    private String userId;
    private String fcmToken;
    private boolean register;
    private int hour;
    private int minute;

    public UserToken() {
    }

    public UserToken(String userId, String fcmToken, int hour, int minute) {
        this.userId = userId;
        this.fcmToken = fcmToken;
        this.hour = hour;
        this.minute = minute;
    }

    public UserToken(String userId, String fcmToken, boolean register, int hour, int minute) {
        this.userId = userId;
        this.fcmToken = fcmToken;
        this.register = register;
        this.hour = hour;
        this.minute = minute;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "userId='" + userId + '\'' +
                ", fcmToken='" + fcmToken + '\'' +
                ", register=" + register +
                ", hour=" + hour +
                ", minute=" + minute +
                '}';
    }
}
