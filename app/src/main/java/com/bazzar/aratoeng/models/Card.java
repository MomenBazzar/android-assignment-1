package com.bazzar.aratoeng.models;

public class Card {
    private final int id;
    private final String word;
    private boolean isMatched = false;

    public Card(int id, String word) {
        this.id = id;
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }
}
