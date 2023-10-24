package com.ptit.englishapp.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class YourWord {
    private Long id;
    private String word;
    private String phonetic;
    private String mean;
    private String description;
    private String uuid;
    private Date dateCreate;

    public YourWord(Long id, String word, String mean, String description, String uuid) {
        this.id = id;
        this.word = word;
        this.mean = mean;
        this.description = description;
        this.uuid = uuid;
    }

    public YourWord(Long id, String word, String mean, String description, String uuid, Date dateCreate) {
        this.id = id;
        this.word = word;
        this.mean = mean;
        this.description = description;
        this.uuid = uuid;
        this.dateCreate = dateCreate;
    }

    public YourWord(Long id, String word, String phonetic, String mean, String description, String uuid, Date dateCreate) {
        this.id = id;
        this.word = word;
        this.phonetic = phonetic;
        this.mean = mean;
        this.description = description;
        this.uuid = uuid;
        this.dateCreate = dateCreate;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public YourWord(String word, String mean, String description, String uuid) {
        this.word = word;
        this.mean = mean;
        this.description = description;
        this.uuid = uuid;
    }

    public YourWord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    // Chuyển đổi YourWord thành một Map
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("word", word);
        result.put("phonetic", phonetic);
        result.put("mean", mean);
        result.put("description", description);
        result.put("uuid", uuid);
        result.put("dateCreate", dateCreate);

        return result;
    }

    @Override
    public String toString() {
        return "YourWord{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", mean='" + mean + '\'' +
                ", description='" + description + '\'' +
                ", uuid='" + uuid + '\'' +
                ", dateCreate=" + dateCreate +
                '}';
    }
}
