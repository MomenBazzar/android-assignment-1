package com.bazzar.aratoeng.models;

public class Word {
    private final String arabicWord;
    private final String englishWord;

    public Word(String arabicWord, String englishWord) {
        this.arabicWord = arabicWord;
        this.englishWord = englishWord;
    }

    public String getArabicWord() {
        return arabicWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }
}