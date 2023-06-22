package com.bazzar.aratoeng;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultTextView = findViewById(R.id.result_text_view);

        // Retrieve the elapsed time from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int elapsedTime = preferences.getInt("elapsedTime", 0);

        // Display the elapsed time
        resultTextView.setText(String.format("Elapsed Time: %d seconds", elapsedTime));
    }
}
