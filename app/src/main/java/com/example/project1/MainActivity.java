// MainActivity.java
package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button startGameButton;
    private Button startJigsawLevelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        startGameButton = findViewById(R.id.startGameButton);
        startJigsawLevelButton = findViewById(R.id.startJigsawLevelButton);

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

        startJigsawLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the game level activity
                Intent intent = new Intent(MainActivity.this, FoodMatch.class);
                startActivity(intent);
            }
        });
    }
}