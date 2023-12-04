package com.example.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {
    private Class<?> nextLevelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int score = getIntent().getIntExtra("score", 0);

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + score);

        // Retrieve the next level class from intent extras
        nextLevelClass = (Class<?>) getIntent().getSerializableExtra("nextLevelClass");

        Button nextLevelButton = findViewById(R.id.nextLevelButton);
        nextLevelButton.setOnClickListener(v -> startNextLevel());
    }

    private void startNextLevel() {
        if (nextLevelClass != null) {
            Intent intent = new Intent(GameOverActivity.this, nextLevelClass);
            startActivity(intent);
        }
        finish();
    }
}
