package com.ptit.englishapp.oxford;

import java.util.List;

public class Meanings {
    String partOfSpeech = "";
    List<Definitions> definitions = null;
    List<String> synonyms = null;

    List<String> antonyms = null;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Definitions> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definitions> definitions) {
        this.definitions = definitions;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    @Override
    public String toString() {
        return "Meanings{" +
                "partOfSpeech='" + partOfSpeech + '\'' +
                ", definitions=" + definitions +
                ", synonyms=" + synonyms +
                ", antonyms=" + antonyms +
                '}';
    }
}
