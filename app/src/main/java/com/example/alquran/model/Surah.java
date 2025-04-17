package com.example.alquran.model;

public class Surah {
    private int number;
    private String name;
    private String englishName;
    private int numberOfAyahs;

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getNumberOfAyahs() {
        return String.valueOf(numberOfAyahs);
    }
}