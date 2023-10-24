package com.ptit.englishapp.oxford;

public class Phonetics {
    String text = "";
    String audio = "";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public String toString() {
        return "Phonetics{" +
                "text='" + text + '\'' +
                ", audio='" + audio + '\'' +
                '}';
    }
}
