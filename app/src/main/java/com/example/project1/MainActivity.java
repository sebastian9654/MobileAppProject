// MainActivity.java
package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button tutorial;
    private Button startGameButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tutorial = findViewById(R.id.button1);
        startGameButton = findViewById(R.id.startGameButton);

        tutorial.setOnClickListener(v -> {
            // Start the tutorial activity
            Intent intent = new Intent(MainActivity.this, TutorialPage.class);
            startActivity(intent);
        });

        // Add a click listener for the start game button
        startGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShoppingCartActivity.class);
            startActivity(intent);
        });

    }
}