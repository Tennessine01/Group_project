package com.ptit.englishapp.model;

import java.util.Date;
import java.util.Objects;

public class WordRecent {
    private String recent;

    private Integer typeDictionary;

    private String uid;

    private Date createDate;


    public WordRecent(String recent) {
        this.recent = recent;
    }

    public WordRecent(String recent, Integer typeDictionary, String uid) {
        this.recent = recent;
        this.typeDictionary = typeDictionary;
        this.uid = uid;
    }

    public WordRecent(String recent, Integer typeDictionary, String uid, Date createDate) {
        this.recent = recent;
        this.typeDictionary = typeDictionary;
        this.uid = uid;
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getTypeDictionary() {
        return typeDictionary;
    }

    public void setTypeDictionary(Integer typeDictionary) {
        this.typeDictionary = typeDictionary;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public WordRecent() {
    }

    public String getRecent() {
        return recent;
    }

    public void setRecent(String recent) {
        this.recent = recent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordRecent that = (WordRecent) o;
        return Objects.equals(recent, that.recent) && Objects.equals(typeDictionary, that.typeDictionary) && Objects.equals(uid, that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recent, typeDictionary, uid);
    }

    @Override
    public String toString() {
        return "WordRecent{" +
                "recent='" + recent + '\'' +
                ", typeDictionary=" + typeDictionary +
                ", uid='" + uid + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
