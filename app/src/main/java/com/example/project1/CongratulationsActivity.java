// CongratulationsActivity.java
package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CongratulationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations);

        // Get credits information from the intent
        String credits = getIntent().getStringExtra("credits");

        // Display credits in the TextView
        TextView creditsTextView = findViewById(R.id.congratsMessage);
        creditsTextView.setText(credits);

        // Add a click listener for the Main Menu button
        Button mainMenuButton = findViewById(R.id.mainMenuButton);

        mainMenuButton.setOnClickListener(v -> {
            // Start the main activity
            Intent intent = new Intent(CongratulationsActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });
    }
}
