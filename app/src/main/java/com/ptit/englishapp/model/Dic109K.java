package com.ptit.englishapp.model;

public class Dic109K {
    private Long id;
    private String word;
    private String phonetic;
    private String mean;

    public Dic109K() {
    }

    public Dic109K(Long id, String word, String phonetic, String mean) {
        this.id = id;
        this.word = word;
        this.phonetic = phonetic;
        this.mean = mean;
    }

    public Dic109K(String word, String phonetic, String mean) {
        this.word = word;
        this.phonetic = phonetic;
        this.mean = mean;
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

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    @Override
    public String toString() {
        return "Dic109K{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", phonetic='" + phonetic + '\'' +
                ", mean='" + mean + '\'' +
                '}';
    }
}
