package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RestartGameActivity extends AppCompatActivity {
    private Class<?> sameLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_game);

        int score = getIntent().getIntExtra("score", 0);

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText("Score: " + score);

        sameLevel = (Class<?>) getIntent().getSerializableExtra("nextLevelClass");

        Button nextLevelButton = findViewById(R.id.restartLevel);
        nextLevelButton.setOnClickListener(v -> restartLevel());
    }

    private void restartLevel() {
        if (sameLevel != null) {
            Intent intent = new Intent(RestartGameActivity.this, sameLevel);
            startActivity(intent);
        }
        finish();
    }
}