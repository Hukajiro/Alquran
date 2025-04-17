package com.example.alquran.model;

public class Ayah {
    private int numberInSurah;
    private String text;
    private String transliteration;
    private String translation;

    public int getNumberInSurah() {
        return numberInSurah;
    }

    public String getText() {
        return text;
    }

    public String getTransliteration() {
        return transliteration != null ? transliteration : "";
    }

    public String getTranslation() {
        return translation;
    }

    public void setNumberInSurah(int numberInSurah) {
        this.numberInSurah = numberInSurah;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTransliteration(String transliteration) {
        this.transliteration = transliteration;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}