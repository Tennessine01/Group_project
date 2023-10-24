package com.ptit.englishapp.model;

import java.io.Serializable;

public class Article implements Serializable {
    private String id;
    private String title;
    private String content;
    private int type;
    private long time;
    private String url;

    private int care;

    public int getCare() {
        return care;
    }

    public void setCare(int care) {
        this.care = care;
    }

    public Article(String title, String content, int type, long date, String url) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.time = date;
        this.url = url;
    }

    public Article(String title, String content, int type, long time, String url, int care) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.time = time;
        this.url = url;
        this.care = care;
    }

    public Article() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
