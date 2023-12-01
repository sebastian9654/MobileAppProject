package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.CountDownTimer;

import java.util.Arrays;
import java.util.Random;

public class JigsawPuzzleActivity extends AppCompatActivity {
    private int[] ingredients = {R.drawable.carrot_jigsaw, R.drawable.egg_jigsaw, R.drawable.onion_jigsaw, R.drawable.tomato_jigsaw};
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

        // Initially display the original ingredient image
        int randomIngredientIndex = random.nextInt(ingredients.length);
        final int selectedIngredient = ingredients[randomIngredientIndex];
        puzzleImageView.setImageResource(selectedIngredient);

        final int[] jigsawVersions = {R.drawable.carrot_jigsaw, R.drawable.egg_jigsaw, R.drawable.onion_jigsaw, R.drawable.tomato_jigsaw};

        startJigsawGame = findViewById(R.id.startJigsawGame);
        startJigsawGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLevel();
                startJigsawGame.setVisibility(View.INVISIBLE);

                // Find the index of the displayed ingredient in the ingredients array
                int currentIngredientIndex = -1;
                for (int i = 0; i < ingredients.length; i++) {
                    if (ingredients[i] == selectedIngredient) {
                        currentIngredientIndex = i;
                        break;
                    }
                }

                if (currentIngredientIndex != -1 && currentIngredientIndex < jigsawVersions.length) {
                    // Replace the original ingredient with its jigsaw version
                    int jigsawVersion = jigsawVersions[currentIngredientIndex];
                    puzzleImageView.setImageResource(jigsawVersion);

                    // Wait a bit before splitting the image into pieces to ensure it's properly set
                    puzzleImageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            splitImageIntoPieces();
                        }
                    }, 100); // Adjust the delay time as needed
                }
            }
        });

        // Hide the button initially until the user clicks "Start Level"
        startJigsawGame.setVisibility(View.VISIBLE);
    }

    private void splitImageIntoPieces() {
        // Split the image into 9 pieces
        BitmapDrawable drawable = (BitmapDrawable) puzzleImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        int pieceWidth = bitmap.getWidth() / 3;
        int pieceHeight = bitmap.getHeight() / 3;

        // Create a 3x3 grid of ImageViews to hold the puzzle pieces
        ImageView[][] puzzlePieces = new ImageView[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Bitmap pieceBitmap = Bitmap.createBitmap(bitmap, j * pieceWidth, i * pieceHeight, pieceWidth, pieceHeight);
                puzzlePieces[i][j] = new ImageView(this);
                puzzlePieces[i][j].setImageBitmap(pieceBitmap);
            }
        }

        // Shuffle the puzzle pieces
        shufflePieces(puzzlePieces);

        // Display the shuffled puzzle pieces in a 3x3 grid
        GridLayout puzzleGridLayout = findViewById(R.id.puzzleGridLayout);
        puzzleGridLayout.removeAllViews(); // Clear any existing views in the layout

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                puzzleGridLayout.addView(puzzlePieces[i][j]);
            }
        }
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
    private void shufflePieces(ImageView[][] puzzlePieces) {
        int rows = puzzlePieces.length;
        int cols = puzzlePieces[0].length;
        Random random = new Random();

        for (int i = rows - 1; i > 0; i--) {
            for (int j = cols - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                // Swap puzzle pieces at (i, j) and (m, n)
                ImageView temp = puzzlePieces[i][j];
                puzzlePieces[i][j] = puzzlePieces[m][n];
                puzzlePieces[m][n] = temp;
            }
        }
    }

    private void endLevel() {
        this.gameActive = false;
        finish();
    }
}