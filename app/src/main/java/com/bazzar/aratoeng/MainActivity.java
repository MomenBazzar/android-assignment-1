package com.bazzar.aratoeng;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.TextView;

import com.bazzar.aratoeng.models.*;

import java.util.List;
public class MainActivity extends AppCompatActivity {

    private CardsController cardsController;
    private List<Card> gameCards;
    private CardView firstCardSelected;
    private CardView secondCardSelected;
    private boolean isAnimating = false;
    private boolean[] matchedPair;
    private int elapsedTimeInSeconds;
    private Handler timerHandler;
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // Initialize the cards controller and get the game cards
        cardsController = new CardsController(MainActivity.this);
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

        // Start the timer
        startTimer();
    }

    private void startTimer() {
        elapsedTimeInSeconds = 0;
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                elapsedTimeInSeconds++;
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.postDelayed(timerRunnable, 1000);
    }

    private void stopTimer() {
        if (timerHandler != null && timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
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

            // Check if all pairs are matched
            if (areAllPairsMatched()) {
                stopTimer();
                int timeInSeconds = elapsedTimeInSeconds;

                // Save the elapsed time in SharedPreferences
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("elapsedTime", timeInSeconds);
                editor.apply();

                // Start the ResultActivity
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            // The cards don't match
            isAnimating = true;
            new Handler().postDelayed(() -> {
                flip(firstCardSelected);
                flip(secondCardSelected);
                firstCardSelected = null;
                secondCardSelected = null;
                isAnimating = false;
            }, 500);
        }
    }

    private boolean areAllPairsMatched() {
        for (boolean matched : matchedPair) {
            if (!matched) {
                return false;
            }
        }
        return true;
    }

    private void flip(CardView cardView) {
        isAnimating = true;
        cardView.animate()
                .rotationYBy(180)
                .setDuration(250)
                .withEndAction(() -> isAnimating = false)
                .start();
        cardView.flip();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
