
// GameLevelActivity.java
package com.example.project1;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class GameLevelActivity extends AppCompatActivity {
    private int score = 0;
    private TextView scoreTextView;
    private ImageView ingredientImageView;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_level);

        // Initialize views
        scoreTextView = findViewById(R.id.scoreTextView);
        ingredientImageView = findViewById(R.id.ingredientImageView);
        timerTextView = findViewById(R.id.timerTextView);

        // Set up a click listener for the ingredient
        ingredientImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ingredient clicked, increase score
                score++;
                updateScore();

                // Randomly reposition the ingredient for the next click
                repositionIngredient();
            }
        });

        // Start the game by showing the first ingredient
        repositionIngredient();

        // Add a countdown timer
        new CountDownTimer(15000, 1000) { // 15 seconds countdown
            public void onTick(long millisUntilFinished) {
                // Update the timer on each tick
                timerTextView.setText("Time: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                // Game over when the timer finishes
                endGame();
            }
        }.start();
    }

    // Method to update the score on the screen
    private void updateScore() {
        scoreTextView.setText("Score: " + score);

        // Check if the player has reached a certain score to proceed to the next level
        if (score >= 10) {
            // You can add logic here to transition to the next level or end the game
            // For simplicity, let's finish the activity (end the game) when the score reaches 10
            endGame();
        }
    }

    // Method to end the game
    private void endGame() {
        // You can add additional logic here, such as displaying a game over message or transitioning to the next level
        finish();
    }

    // Method to reposition the ingredient to a random location on the screen
    // Method to reposition the ingredient to a random location on the screen
    private void repositionIngredient() {
        Random random = new Random();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        // Get the dimensions of the carrot image
        int carrotWidth = ingredientImageView.getWidth();
        int carrotHeight = ingredientImageView.getHeight();

        // Ensure positive bounds
        int widthBound = Math.max(screenWidth - carrotWidth, 1);
        int heightBound = Math.max(screenHeight - carrotHeight, 1);

        // Initialize variables to store the previous position
        float previousX = ingredientImageView.getX();
        float previousY = ingredientImageView.getY();

        // Set the new position of the carrot, making sure it's different from the previous one
        float newX, newY;
        do {
            newX = random.nextInt(widthBound);
            newY = random.nextInt(heightBound);
        } while (Math.abs(newX - previousX) < carrotWidth && Math.abs(newY - previousY) < carrotHeight);

        // Apply the new position
        ingredientImageView.setX(newX);
        ingredientImageView.setY(newY);
    }

}
