package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.CountDownTimer;
import org.w3c.dom.Text;

import java.util.Random;

public class JigsawPuzzleActivity extends AppCompatActivity {
    private int[] ingredients = {R.drawable.carrot, R.drawable.egg, R.drawable.onion, R.drawable.tomato};
    private Random random = new Random();
    private TextView timeView;
    public boolean levelCompleted = false;
    private ImageView puzzleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jigsaw_puzzle);

        puzzleImageView = findViewById(R.id.puzzleImageView);

        // Choose a random image from our ingredients array to be the puzzle
        int randomIngredientIndex = random.nextInt(ingredients.length);
        int selectedIngredient = ingredients[randomIngredientIndex];
        puzzleImageView.setImageResource(selectedIngredient);

        timeView = findViewById(R.id.timerView);

    }
}