package com.example.alquran.model;

public class Ayah {
    private int number;
    private int numberInSurah;
    private String text;
    private String transliteration;
    private String translation;
    private String audio;

    public int getNumber() {
        return number;
    }
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
    public String getAudio() {
        return "https://cdn.islamic.network/quran/audio/128/ar.alafasy/" + number + ".mp3";
    }

    public void setNumber(int number) {
        this.number = number;
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
    public void setAudio(String audio) {
        this.audio = audio;
    }
}