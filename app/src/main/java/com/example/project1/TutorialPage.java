package com.example.project1;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class TutorialPage extends AppCompatActivity {
    // creating a variable for our button.
    private Button settingsBtn;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_page);

        // initializing buttons
        settingsBtn = findViewById(R.id.btnSetting);
        buttonNext = findViewById(R.id.buttonNext);

        // setting click listeners
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new intent to open SettingsActivity.
                Intent settingsIntent = new Intent(TutorialPage.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new intent to open Level00 activity.
                Intent level00Intent = new Intent(TutorialPage.this, Level00.class);
                startActivity(level00Intent);
                finish();
            }
        });
    }
}