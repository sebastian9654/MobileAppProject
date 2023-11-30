package com.example.project1;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameLevelActivity extends AppCompatActivity {

    private RelativeLayout gameLayout;
    private ImageView basket;
    private TextView scoreTextView;
    private TextView strikesTextView;
    private Handler handler = new Handler();
    private Runnable runnable;

    private float lastX;
    private int score = 0;
    private int strikes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one);

        gameLayout = findViewById(R.id.gameLayout);
        basket = findViewById(R.id.basket);
        scoreTextView = findViewById(R.id.scoreTextView);
        strikesTextView = findViewById(R.id.strikesTextView);

        // Start the falling vegetables when the activity starts
        startFallingVegetables();

        // Add touch listener to the basket for dragging
        basket.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float x = event.getRawX();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float dx = x - lastX;
                        float newX = view.getX() + dx;

                        // Ensure the basket stays within the screen boundaries
                        if (newX > 0 && newX + view.getWidth() < gameLayout.getWidth()) {
                            view.setX(newX);
                        }

                        lastX = x;
                        break;
                }
                return true;
            }
        });
    }

    private void startFallingVegetables() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                // Create a new ImageView for the falling vegetable
                ImageView vegetable = new ImageView(getApplicationContext());
                Random random = new Random();

                // Set a random vegetable image
                int[] vegetables = {R.drawable.egg, R.drawable.carrot, R.drawable.onion, R.drawable.tomato};
                vegetable.setImageResource(vegetables[random.nextInt(vegetables.length)]);

                // Set the layout parameters for the ImageView
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
                layoutParams.leftMargin = random.nextInt(gameLayout.getWidth() - 150); // Random x-coordinate
                layoutParams.topMargin = 0; // Start from the top
                vegetable.setLayoutParams(layoutParams);

                // Add the ImageView to the layout
                gameLayout.addView(vegetable);

                // Make the vegetable fall by animating its Y-coordinate
                vegetable.animate().translationY(gameLayout.getHeight()).setDuration(3000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Check for collision with the basket
                        if (isViewOverlapping(vegetable, basket)) {
                            // Increment the score when there is a collision
                            score++;
                            scoreTextView.setText("Score: " + score);
                        } else {
                            // Increment strikes when the vegetable falls to the bottom
                            strikes++;
                            strikesTextView.setText("Strikes: " + strikes);

                            // Check for game over (3 strikes)
                            if (strikes == 3) {
                                gameOver();
                                return; // Stop spawning vegetables when the game is over
                            }
                        }

                        // Remove the vegetable from the layout after it falls
                        gameLayout.removeView(vegetable);
                    }
                });

                // Continue spawning vegetables
                handler.postDelayed(this, 2000);
            }
        }, 2000); // Initial delay
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    // Check if two views overlap
    private boolean isViewOverlapping(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        int firstViewLeft = firstPosition[0];
        int firstViewRight = firstViewLeft + firstView.getWidth();
        int firstViewTop = firstPosition[1];
        int firstViewBottom = firstViewTop + firstView.getHeight();

        int secondViewLeft = secondPosition[0];
        int secondViewRight = secondViewLeft + secondView.getWidth();
        int secondViewTop = secondPosition[1];
        int secondViewBottom = secondViewTop + secondView.getHeight();

        return !(firstViewLeft > secondViewRight || firstViewRight < secondViewLeft || firstViewTop > secondViewBottom || firstViewBottom < secondViewTop);
    }

    // Handle game over
    private void gameOver() {
        Toast.makeText(this, "Game Over!", Toast.LENGTH_SHORT).show();
        // Perform any additional actions for game over, e.g., show a dialog, navigate to another activity, etc.
        finish(); // Close the current activity
    }
}
