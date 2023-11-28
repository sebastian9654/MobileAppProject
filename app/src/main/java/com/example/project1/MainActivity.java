// MainActivity.java
package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button1;
    Button startGameButton; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        startGameButton = findViewById(R.id.startGameButton); // Add this line

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the tutorial activity
                Intent intent = new Intent(MainActivity.this, TutorialPage.class);
                startActivity(intent);
            }
        });

        // Add a click listener for the start game button
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the game level activity
                Intent intent = new Intent(MainActivity.this, GameLevelActivity.class);
                startActivity(intent);
            }
        });
    }
}