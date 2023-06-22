package com.bazzar.aratoeng.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardsController {
    private SharedPreferences sharedPreferences;
    private static final String KEY_GAME_CARDS = "game_cards";
    private List<Card> gameCards;

    public CardsController(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gameCards = loadGameCardsFromSharedPreferences();

        if (gameCards == null) {
            initializeGameCards();
            saveGameCardsToSharedPreferences();
        }
    }

    private void initializeGameCards() {
        List<Word> allCards = getAllCardsFromSharedPreferences();
        if (allCards == null || allCards.isEmpty()) {
            allCards = initializeAllCards();
        }

        gameCards = new ArrayList<>();
        Collections.shuffle(allCards);

        for (int i = 0; i < 8; i++) {
            Card arabicWord = new Card(i, allCards.get(i).getArabicWord());
            Card englishWord = new Card(i, allCards.get(i).getEnglishWord());

            gameCards.add(arabicWord);
            gameCards.add(englishWord);
        }

        Collections.shuffle(gameCards);
    }

    private List<Word> initializeAllCards() {
        List<Word> allCards = new ArrayList<>();
        allCards.add(new Word("كتاب", "book"));
        allCards.add(new Word("مدرسة", "school"));
        allCards.add(new Word("طالب", "student"));
        allCards.add(new Word("معلم", "teacher"));
        allCards.add(new Word("قلم", "pen"));
        allCards.add(new Word("أسد", "lion"));
        allCards.add(new Word("سمك", "fish"));
        allCards.add(new Word("فيل", "elephant"));
        allCards.add(new Word("شمس", "sun"));
        allCards.add(new Word("قمر", "moon"));
        allCards.add(new Word("سحابة", "cloud"));
        allCards.add(new Word("زهرة", "flower"));
        allCards.add(new Word("شجرة", "tree"));
        allCards.add(new Word("جبل", "mountain"));
        allCards.add(new Word("نهر", "river"));
        allCards.add(new Word("بحر", "sea"));
        allCards.add(new Word("فراشة", "butterfly"));
        allCards.add(new Word("نملة", "ant"));
        allCards.add(new Word("عصفور", "bird"));
        allCards.add(new Word("جمل", "camel"));
        allCards.add(new Word("فأر", "mouse"));
        allCards.add(new Word("ثعبان", "snake"));
        allCards.add(new Word("ثلج", "snow"));
        allCards.add(new Word("باب", "door"));
        allCards.add(new Word("نافذة", "window"));
        allCards.add(new Word("كرسي", "chair"));
        allCards.add(new Word("طاولة", "table"));
        allCards.add(new Word("قط", "cat"));

        saveAllCardsToSharedPreferences(allCards);
        return allCards;
    }

    private void saveAllCardsToSharedPreferences(List<Word> allCards) {
        Gson gson = new Gson();
        String json = gson.toJson(allCards);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GAME_CARDS, json);
        editor.apply();
    }

    private List<Word> getAllCardsFromSharedPreferences() {
        String json = sharedPreferences.getString(KEY_GAME_CARDS, "");

        Gson gson = new Gson();
        Type type = new TypeToken<List<Word>>() {}.getType();

        return gson.fromJson(json, type);
    }

    private void saveGameCardsToSharedPreferences() {
        Gson gson = new Gson();
        String json = gson.toJson(gameCards);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GAME_CARDS, json);
        editor.apply();
    }

    private List<Card> loadGameCardsFromSharedPreferences() {
        String json = sharedPreferences.getString(KEY_GAME_CARDS, "");

        Gson gson = new Gson();
        Type type = new TypeToken<List<Card>>() {}.getType();

        return gson.fromJson(json, type);
    }

    public List<Card> getGameCards() {
        return gameCards;
    }

    public void setCardMatched(Card card, boolean isMatched) {
        card.setMatched(isMatched);
        saveGameCardsToSharedPreferences();
    }

    public boolean areMatchPair(Card card1, Card card2) {
        return card1.getId() == card2.getId();
    }

    public boolean isCardAlreadyMatched(int id) {
        for (Card card : gameCards) {
            if (card.getId() == id) {
                return card.isMatched();
            }
        }
        return false;
    }
}
