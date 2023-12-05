// ShoppingCartActivity.java
package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ShoppingCartActivity extends AppCompatActivity {
    private Class<?> nextLevelClass = FoodMatchingActivity.class;

    private RelativeLayout gameLayout;
    private ImageView basket;
    private TextView scoreTextView;
    private TextView strikesTextView;
    private Handler handler = new Handler();
    private Runnable runnable;

    private float lastX;
    private int score = 0;
    private int strikes = 0;

    private static final int WINNING_SCORE = 10; // Set the winning score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_one);

        gameLayout = findViewById(R.id.gameLayout);

        // Set the background of the gameLayout to the grocery.png image
        gameLayout.setBackgroundResource(R.drawable.store_1);
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
                if (strikes == 3 || score == WINNING_SCORE) {
                    handler.removeCallbacks(this); // Stop spawning items
                    if (strikes == 3) {
                        finish();
                        restartThis();
                    } else if (score == WINNING_SCORE) {
                        finish();
                        goNext();
                    }
                    return;
                }
                // Create a new ImageView for the falling item
                ImageView item = new ImageView(getApplicationContext());
                Random random = new Random();

                // Set a random item image (including the bomb)
                final int[] items = {R.drawable.egg, R.drawable.carrot, R.drawable.onion, R.drawable.tomato, R.drawable.bomb};
                int randomItem = random.nextInt(items.length);

                // Debug print for random item selection
                System.out.println("Random item selected: " + items[randomItem]);

                item.setImageResource(items[randomItem]);

                // Set the layout parameters for the ImageView
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
                layoutParams.leftMargin = random.nextInt(gameLayout.getWidth() - 150); // Random x-coordinate
                layoutParams.topMargin = 0; // Start from the top
                item.setLayoutParams(layoutParams);

                // Add the ImageView to the layout
                gameLayout.addView(item);

                // Make the item fall by animating its Y-coordinate
                // Inside the withEndAction callback in startFallingVegetables method
                item.animate().translationY(gameLayout.getHeight()).setDuration(3000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Check for collision with the basket
                        if (isViewOverlapping(item, basket)) {
                            // Check if the caught item is a bomb
                            if (items[randomItem] == R.drawable.bomb) {
                                // If it's a bomb, increment strikes
                                strikes++;
                                strikesTextView.setText("Strikes: " + strikes);
                                System.out.println("Caught a bomb! Strikes: " + strikes);
                            } else {
                                // If it's a vegetable, increment score
                                score++;
                                scoreTextView.setText("Score: " + score);
                            }
                        } else if (items[randomItem] != R.drawable.bomb) {
                            // Increment strikes only when the item falls to the bottom and it's not a bomb,
                            // and it hasn't been caught by the user
                            strikes++;
                            strikesTextView.setText("Strikes: " + strikes);
                            System.out.println("Strikes: " + strikes + " - Score: " + score);
                        }
                    }
                });

                // Continue spawning items
                handler.postDelayed(this, 2000);
            }
        }, 2000); // Initial delay
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

    private void goNext() {
        // Start the NextLevelActivity and pass the score
        Intent intent = new Intent(ShoppingCartActivity.this, NextLevelActivity.class);
        intent.putExtra("score", score);
        // Set the next level class before starting GameOverActivity
        intent.putExtra("nextLevelClass", nextLevelClass);

        startActivity(intent);
        finish();
    }

    private void restartThis() {
        // Restart this level and pass the score
        Intent intent = new Intent(ShoppingCartActivity.this, RestartGameActivity.class);
        intent.putExtra("score", score);

        //
        intent.putExtra("nextLevelClass", ShoppingCartActivity.class);

        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
