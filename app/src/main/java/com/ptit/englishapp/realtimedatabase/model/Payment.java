package com.ptit.englishapp.realtimedatabase.model;

import java.util.Date;

public class Payment {
    private String id;
    private boolean success;
    private String uid;
    private double amount;
    private Long date;

    public Payment() {
    }

    public Payment(boolean success, String uid, double amount, Long date) {
        this.success = success;
        this.uid = uid;
        this.amount = amount;
        this.date = date;
    }

    public Payment(String id, boolean success, String uid, double amount, Long date) {
        this.id = id;
        this.success = success;
        this.uid = uid;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", success=" + success +
                ", uid='" + uid + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
