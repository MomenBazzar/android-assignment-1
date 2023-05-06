package com.bazzar.aratoeng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;

import com.bazzar.aratoeng.models.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardsController cardsController;
    private List<Card> gameCards;
    private int numCardsSelected;
    private CardView firstCardSelected;
    private CardView secondCardSelected;
    boolean isAnimating = false;
    private boolean[] matchedPair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        // Initialize the cards controller and get the game cards
        cardsController = new CardsController();
        gameCards = cardsController.getGameCards();

        matchedPair = new boolean[8];

        // Get a reference to the GridLayout
        GridLayout gridLayout = findViewById(R.id.grid_layout);

        // Loop through the game cards and add them to the GridLayout
        for (Card gameCard : gameCards) {
            // Create a new CardView for the game card
            CardView cardView = new CardView(this);
            cardView.setTag(gameCard);
            cardView.setText(gameCard.getWord());

            // Set the background of the CardView
            cardView.setBackgroundResource(R.drawable.card_bg);

            // Set the layout parameters for the CardView
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = getResources().getDisplayMetrics().widthPixels / 5;
            params.height = getResources().getDisplayMetrics().heightPixels / 5;
            params.setMargins(5, 5, 5, 5);
            cardView.setLayoutParams(params);

            // Set an OnClickListener for the CardView
            cardView.setOnClickListener(v -> onCardClicked((CardView) v));

            // Add the CardView to the GridLayout
            gridLayout.addView(cardView);
            flip(cardView);
        }
    }

    private void onCardClicked(CardView cardView) {
        Card card = (Card) cardView.getTag();
        if (!isAnimating && !card.isMatched() && cardView.isFlipped()) {
            // Prevent multiple clicks and matching of already matched cards

            flip(cardView);

            if (firstCardSelected == null) {
                // This is the first card selected
                firstCardSelected = cardView;
            } else {
                // This is the second card selected
                secondCardSelected = cardView;
                checkForMatch();
            }
        }
    }

    private void checkForMatch() {
        Card firstCard = (Card) firstCardSelected.getTag();
        Card secondCard = (Card) secondCardSelected.getTag();

        if (firstCard.getId() == secondCard.getId()) {
            // The cards match
            matchedPair[firstCard.getId()] = true;
            firstCard.setMatched(true);
            secondCard.setMatched(true);
            firstCardSelected = null;
            secondCardSelected = null;
        } else {
            // The cards don't match
            isAnimating = true;
            new Handler().postDelayed((Runnable) () -> {
                flip(firstCardSelected);
                flip(secondCardSelected);
                firstCardSelected = null;
                secondCardSelected = null;
                isAnimating = false;
            }, 500);
        }
    }


    private void flip(CardView cardView) {
        isAnimating = true;
        cardView.animate()
                .rotationYBy(180)
                .setDuration(250)
                .withEndAction(() -> {
                    isAnimating = false;
                })
                .start();
        cardView.flip();

    }

}
