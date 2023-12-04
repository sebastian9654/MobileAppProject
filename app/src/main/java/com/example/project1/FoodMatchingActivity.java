package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FoodMatchingActivity extends AppCompatActivity {

    private ImageView foodToMatchView;
    private Button option1Button, option2Button, option3Button;
    private String correctFoodName;
    private Random random = new Random();
    private int score = 0;
    private TextView scoreTextView, timerTextView;
    private CountDownTimer countDownTimer;
    private static final long TIMER_DURATION = 60000; // 60 seconds

    private Map<Integer, String> foodNameMap = new HashMap<>();
    private Class<?> nextLevelClass = BroccoliRunActivity.class; // Replace with your next level class
    private final int MINIMUM_SCORE = 20;
    private int[] ingredients = {
            R.drawable.carrot_jigsaw,
            R.drawable.egg_jigsaw,
            R.drawable.onion_jigsaw,
            R.drawable.tomato_jigsaw
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_match);

        foodToMatchView = findViewById(R.id.foodToMatchView);

        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);

        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);

        prepareFoodNameMap();
        scoreTextView.setText("Score: " + score);

        startTimer();
        startGame();
    }

    private void prepareFoodNameMap() {
        // Associate image resources with food names
        foodNameMap.put(R.drawable.carrot_jigsaw, "Carrot");
        foodNameMap.put(R.drawable.egg_jigsaw, "Egg");
        foodNameMap.put(R.drawable.onion_jigsaw, "Onion");
        foodNameMap.put(R.drawable.tomato_jigsaw, "Tomato");
    }

    private void setOptionTexts(String correctFoodName) {
        List<String> foodNames = new ArrayList<>(foodNameMap.values());
        List<String> options = new ArrayList<>();

        // Add the correct food name to options
        options.add(correctFoodName);

        // Remove the correct food name from the list of food names
        foodNames.remove(correctFoodName);

        // Shuffle the remaining food names and pick two random names
        Collections.shuffle(foodNames);
        options.add(foodNames.get(0));
        options.add(foodNames.get(1));

        // Shuffle the options to randomize their order
        Collections.shuffle(options);
        // Set the text for the buttons
        option1Button.setText(options.get(0));
        option2Button.setText(options.get(1));
        option3Button.setText(options.get(2));
    }

    private void startGame() {
        int randomIngredientIndex = random.nextInt(ingredients.length);
        int selectedIngredient = ingredients[randomIngredientIndex];
        foodToMatchView.setImageResource(selectedIngredient);
        correctFoodName = foodNameMap.get(selectedIngredient);
        setOptionTexts(correctFoodName);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                timerTextView.setText("Time: " + secondsLeft);
            }

            public void onFinish() {
                // Handle end of game
                // For example, display final score or navigate to the result screen
                Toast.makeText(FoodMatchingActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                if (score >= MINIMUM_SCORE) {
                    System.out.println("Go next");
                    goNext(); // go to next activity
                    finish();
                }
                else {
                    System.out.println("Restarting...");
                    restartThis();
                    finish();
                }
            }
        }.start();
    }

    public void onOptionSelected(View view) {
        Button selectedButton = (Button) view;
        String selectedFoodName = selectedButton.getText().toString();

        if (selectedFoodName.equals(correctFoodName)) {
            score++;
            scoreTextView.setText("Score: " + score);
        } else {
            Toast.makeText(this, "Incorrect Match!", Toast.LENGTH_SHORT).show();
        }
        setOptionTexts(correctFoodName);
        startGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void goNext() {
        // Start the GameOverActivity and pass the score
        Intent intent = new Intent(FoodMatchingActivity.this, NextLevelActivity.class);
        intent.putExtra("score", score);

        // Set the next level class before starting GameOverActivity
        intent.putExtra("nextLevelClass", nextLevelClass);

        startActivity(intent);
    }

    private void restartThis() {
        // Start the GameOverActivity and pass the score
        Intent intent = new Intent(FoodMatchingActivity.this, RestartGameActivity.class);
        intent.putExtra("score", score);

        // Set the next level class before starting GameOverActivity
        intent.putExtra("nextLevelClass", FoodMatchingActivity.class);

        startActivity(intent);
    }



}
