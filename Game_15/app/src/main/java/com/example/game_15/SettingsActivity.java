package com.example.game_15;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Button backButton;
    private Switch debugModeSwitch;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        debugModeSwitch = findViewById(R.id.debug_mode_switch);
        sharedPreferences = getSharedPreferences("GameSettings", MODE_PRIVATE);

        boolean isDebugMode = sharedPreferences.getBoolean("debug_mode", false);
        debugModeSwitch.setChecked(isDebugMode);

        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        debugModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("debug_mode", isChecked);
            editor.apply();
        });
    }
}
