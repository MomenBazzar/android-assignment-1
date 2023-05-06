package com.bazzar.aratoeng.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardsController {
    private static final List<Word> ALL_CARDS = new ArrayList<Word>();
    private final  List<Card> gameCards;

    static {
        ALL_CARDS.add(new Word("كتاب", "book"));
        ALL_CARDS.add(new Word("مدرسة", "school"));
        ALL_CARDS.add(new Word("طالب", "student"));
        ALL_CARDS.add(new Word("معلم", "teacher"));
        ALL_CARDS.add(new Word("قلم", "pen"));
        ALL_CARDS.add(new Word("أسد", "lion"));
        ALL_CARDS.add(new Word("سمك", "fish"));
        ALL_CARDS.add(new Word("فيل", "elephant"));
        ALL_CARDS.add(new Word("شمس", "sun"));
        ALL_CARDS.add(new Word("قمر", "moon"));
        ALL_CARDS.add(new Word("سحابة", "cloud"));
        ALL_CARDS.add(new Word("زهرة", "flower"));
        ALL_CARDS.add(new Word("شجرة", "tree"));
        ALL_CARDS.add(new Word("جبل", "mountain"));
        ALL_CARDS.add(new Word("نهر", "river"));
        ALL_CARDS.add(new Word("بحر", "sea"));
        ALL_CARDS.add(new Word("فراشة", "butterfly"));
        ALL_CARDS.add(new Word("نملة", "ant"));
        ALL_CARDS.add(new Word("عصفور", "bird"));
        ALL_CARDS.add(new Word("جمل", "camel"));
        ALL_CARDS.add(new Word("فأر", "mouse"));
        ALL_CARDS.add(new Word("ثعبان", "snake"));
        ALL_CARDS.add(new Word("ثلج", "snow"));
        ALL_CARDS.add(new Word("باب", "door"));
        ALL_CARDS.add(new Word("نافذة", "window"));
        ALL_CARDS.add(new Word("كرسي", "chair"));
        ALL_CARDS.add(new Word("طاولة", "table"));
        ALL_CARDS.add(new Word("قط", "cat"));
    }

    public CardsController() {
        gameCards = new ArrayList<>();

        // choose 8 words randomly
        Collections.shuffle(ALL_CARDS);

        for (int i = 0; i < 8; i++) {
            Card arabicWord = new Card(i, ALL_CARDS.get(i).getArabicWord());
            Card englishWord = new Card(i, ALL_CARDS.get(i).getEnglishWord());

            gameCards.add(arabicWord);
            gameCards.add(englishWord);
        }

        Collections.shuffle(gameCards);
    }

    public List<Card> getGameCards() {
        return gameCards;
    }

    public boolean areMatchPair(Card card1, Card card2) {
        return card1.getId() == card2.getId();
    }

    public boolean isCardAlreadyMatched(int id) {
        return id < 0;
    }
}
