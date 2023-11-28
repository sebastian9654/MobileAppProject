package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.CountDownTimer;
import java.util.Random;

public class JigsawPuzzleActivity extends AppCompatActivity {
    private int[] ingredients = {R.drawable.carrot, R.drawable.egg, R.drawable.onion, R.drawable.tomato};
    private Random random = new Random();
    private TextView timeView;
    public boolean levelCompleted = false;
    public boolean gameActive = false;
    private ImageView puzzleImageView;
    private Button startJigsawGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jigsaw_puzzle);

        this.gameActive = true;

        timeView = findViewById(R.id.timerView);
        puzzleImageView = findViewById(R.id.puzzleImageView);

        // Choose a random image from our ingredients array to be the puzzle
        int randomIngredientIndex = random.nextInt(ingredients.length);
        int selectedIngredient = ingredients[randomIngredientIndex];

        puzzleImageView.setImageResource(selectedIngredient);
        startJigsawGame = findViewById(R.id.startJigsawGame);

        startJigsawGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLevel();
            }
        });
    }

    private void startLevel() {;
        timeView.setText("Time Remaining: 60");
        new CountDownTimer(60000, 1000) { // count from a minute
            public void onTick(long millisUntilFinished) {
                // Update the timer on each tick
                timeView.setText("Time Remaining: " + millisUntilFinished / 1000);
            }
            @Override
            public void onFinish() {
                levelCompleted = false; // keep as false since time limit ran out.
                endLevel();
            }

        }.start();
    }

    private void endLevel() {
        this.gameActive = false;
        finish();
    }
}