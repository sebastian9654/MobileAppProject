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
            R.drawable.carrot,
            R.drawable.realegg,
            R.drawable.onion,
            R.drawable.tomato,
            R.drawable.turkey,
            R.drawable.banana,
            R.drawable.bread,
            R.drawable.corn,
            R.drawable.grapes,
            R.drawable.lettuce,
            R.drawable.peanut,
            R.drawable.pomegranate,
            R.drawable.spaghetti,
            R.drawable.yogurt,
            R.drawable.apple,
            R.drawable.beefsoup,
            R.drawable.blueberries,
            R.drawable.cauliflower,
            R.drawable.celery,
            R.drawable.cinnamon,
            R.drawable.coconut,
            R.drawable.coleslaw,
            R.drawable.fish,
            R.drawable.garlic,
            R.drawable.yogurt,
            R.drawable.ginger,
            R.drawable.greenbeans,
            R.drawable.honey,
            R.drawable.lamb,
            R.drawable.lemon,
            R.drawable.lentils,
            R.drawable.mayonnaise,
            R.drawable.milk,
            R.drawable.mushroom,
            R.drawable.nutmeg,
            R.drawable.orange,
            R.drawable.potatoes,
            R.drawable.shallot,
            R.drawable.shrimp,
            R.drawable.strawberry,
            R.drawable.tuna,













    };


    // Add this variable to your class
    private int strikes = 0;
    private static final int MAX_STRIKES = 3;

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
        foodNameMap.put(R.drawable.carrot, "Carrot");
        foodNameMap.put(R.drawable.realegg, "Egg");
        foodNameMap.put(R.drawable.onion, "Onion");
        foodNameMap.put(R.drawable.tomato, "Tomato");
        foodNameMap.put(R.drawable.turkey, "Turkey");
        foodNameMap.put(R.drawable.banana, "Banana");
        foodNameMap.put(R.drawable.bread, "Bread");
        foodNameMap.put(R.drawable.corn, "Corn");
        foodNameMap.put(R.drawable.grapes, "Grapes");
        foodNameMap.put(R.drawable.lettuce, "Lettuce");
        foodNameMap.put(R.drawable.peanut, "Peanut");
        foodNameMap.put(R.drawable.pomegranate, "Pomegranate");
        foodNameMap.put(R.drawable.spaghetti, "Spaghetti");
        foodNameMap.put(R.drawable.yogurt, "Yogurt");
        foodNameMap.put(R.drawable.apple,"Apple");
        foodNameMap.put(R.drawable.beefsoup,"Beef Soup");
        foodNameMap.put(R.drawable.blueberries,"Blueberries");
        foodNameMap.put(R.drawable.cauliflower,"Cauliflower");
        foodNameMap.put(R.drawable.celery,"Celery");
        foodNameMap.put(R.drawable.cinnamon, "Cinnamon");
        foodNameMap.put(R.drawable.coconut,"Coconut");
        foodNameMap.put(R.drawable.coleslaw,"Coleslaw");
        foodNameMap.put(R.drawable.fish,"Fish");
        foodNameMap.put(R.drawable.garlic,"Garlic");
        foodNameMap.put(R.drawable.ginger,"Ginger");
        foodNameMap.put(R.drawable.greenbeans,"Greenbeans");
        foodNameMap.put(R.drawable.honey,"Honey");
        foodNameMap.put(R.drawable.lamb,"Lamb");
        foodNameMap.put(R.drawable.lemon,"Lemon");
        foodNameMap.put(R.drawable.lentils,"Lentils");
        foodNameMap.put(R.drawable.mayonnaise,"Mayonnaise");
        foodNameMap.put(R.drawable.milk,"Milk");
        foodNameMap.put(R.drawable.mushroom,"Mushroom");
        foodNameMap.put(R.drawable.nutmeg,"Nutmeg");
        foodNameMap.put(R.drawable.orange,"Orange");
        foodNameMap.put(R.drawable.potatoes,"Potatoes");
        foodNameMap.put(R.drawable.shallot,"Shallot");
        foodNameMap.put(R.drawable.shrimp,"Shrimp");
        foodNameMap.put(R.drawable.strawberry,"Strawberry");
        foodNameMap.put(R.drawable.tuna, "Tuna");








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

    // Modify the onOptionSelected method
    public void onOptionSelected(View view) {
        Button selectedButton = (Button) view;
        String selectedFoodName = selectedButton.getText().toString();

        if (selectedFoodName.equals(correctFoodName)) {
            score++;
            scoreTextView.setText("Score: " + score);
        } else {
            // Incorrect match
            handleIncorrectMatch();
        }

        setOptionTexts(correctFoodName);
        startGame();
    }

    private void handleIncorrectMatch() {
        strikes++;

        if (strikes >= MAX_STRIKES) {
            // User has reached maximum strikes, game over
            Toast.makeText(this, "Game Over! Too many incorrect matches.", Toast.LENGTH_SHORT).show();
            restartGame();
        } else {
            // User has some strikes left
            Toast.makeText(this, "Incorrect Match! Strikes: " + strikes, Toast.LENGTH_SHORT).show();
        }
    }private void restartGame() {
        // Implement your logic to restart the game
        // For example, you can restart the activity or navigate to a game-over screen
        System.out.println("Restarting...");
        Intent intent = new Intent(FoodMatchingActivity.this, RestartGameActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("nextLevelClass", FoodMatchingActivity.class);
        startActivity(intent);
        finish(); // finish the current activity
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
