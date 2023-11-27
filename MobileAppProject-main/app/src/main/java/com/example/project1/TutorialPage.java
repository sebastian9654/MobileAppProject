package com.example.project1;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class TutorialPage extends AppCompatActivity {
    // creating a variable for our button.
    private Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_page);
        settingsBtn = findViewById(R.id.btnSetting);

        // adding on click listener for our button.
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new intent to open settings activity.
                Intent i = new Intent(TutorialPage.this, SettingsActivity.class);
                startActivity(i);
            }
        });
    }
}
