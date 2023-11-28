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
    private boolean isGameActive = false; // Added variable to track game state
    private int[] ingredientImages = {R.drawable.carrot, R.drawable.egg, R.drawable.onion, R.drawable.tomato};
    private Random random = new Random();

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
                if (isGameActive) {
                    score++;
                    updateScore();

                    // Randomly reposition and switch the ingredient for the next click
                    repositionAndSwitchIngredient();
                }
            }
        });

        // Start the game by showing the first ingredient
        ingredientImageView.post(new Runnable() {
            @Override
            public void run() {
                startGame();
            }
        });
    }

    // Method to start or restart the game
    private void startGame() {
        // Reset game state
        score = 0;
        updateScore();

        // Reset the timer text
        timerTextView.setText("Time: 15");

        // Set isGameActive to true
        isGameActive = true;

        // Start a new countdown timer
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

        // Show the first ingredient
        repositionAndSwitchIngredient();
    }

    // Method to update the score on the screen
    private void updateScore() {
        scoreTextView.setText("Score: " + score);

        // Check if the player has reached a certain score to proceed to the next level
        if (score >= 10) {
            // For simplicity, let's finish the activity (end the game) when the score reaches 10
            endGame();
        }
    }

    // Method to end the game
    private void endGame() {
        // Set isGameActive to false
        isGameActive = false;

        // You can add additional logic here, such as displaying a game over message
        // or transitioning to the next level

        // For now, let's finish the activity
        finish();
    }

    // Method to reposition and switch the ingredient to a random location on the screen
    private void repositionAndSwitchIngredient() {
        // Randomly select a new ingredient image
        int randomIngredientIndex = random.nextInt(ingredientImages.length);
        int newIngredientImage = ingredientImages[randomIngredientIndex];

        // Set the new ingredient image
        ingredientImageView.setImageResource(newIngredientImage);

        // Call the existing repositionIngredient() logic to set the new position
        ingredientImageView.post(new Runnable() {
            @Override
            public void run() {
                repositionIngredient();
            }
        });
    }

    // Method to reposition the ingredient to a random location on the screen
    private void repositionIngredient() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        // Get the dimensions of the ingredient image
        int ingredientWidth = ingredientImageView.getWidth();
        int ingredientHeight = ingredientImageView.getHeight();

        // Ensure positive bounds
        int widthBound = Math.max(screenWidth - ingredientWidth, 1);
        int heightBound = Math.max(screenHeight - ingredientHeight, 1);

        // Initialize variables to store the previous position
        float previousX = ingredientImageView.getX();
        float previousY = ingredientImageView.getY();

        // Set the new position of the ingredient, making sure it's different from the previous one
        float newX, newY;
        do {
            newX = random.nextInt(widthBound);
            newY = random.nextInt(heightBound);
        } while (Math.abs(newX - previousX) < ingredientWidth && Math.abs(newY - previousY) < ingredientHeight);

        // Apply the new position
        ingredientImageView.setX(newX);
        ingredientImageView.setY(newY);
    }
}
