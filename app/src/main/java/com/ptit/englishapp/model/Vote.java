package com.ptit.englishapp.model;

public class Vote {
    private String id;
    private String title;
    private float rate;
    private String author;
    private String content;
    private String uid;
    private String dateCreate;

    public Vote() {
    }

    public Vote(String id, String title, float rate, String author, String content, String uid, String dateCreate) {
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.author = author;
        this.content = content;
        this.uid = uid;
        this.dateCreate = dateCreate;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Vote(String id, String title, float rate, String author, String content, String uid) {
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.author = author;
        this.content = content;
        this.uid = uid;
    }

    public Vote(String title, float rate, String author, String content, String uid) {
        this.title = title;
        this.rate = rate;
        this.author = author;
        this.content = content;
        this.uid = uid;
    }

    public Vote(String title, float rate, String author, String content, String uid, String dateCreate) {
        this.title = title;
        this.rate = rate;
        this.author = author;
        this.content = content;
        this.uid = uid;
        this.dateCreate = dateCreate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
