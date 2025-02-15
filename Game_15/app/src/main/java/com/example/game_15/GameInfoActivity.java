package com.example.game_15;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        long elapsedTime = getIntent().getLongExtra("elapsedTime", 0);
        int moveCount = getIntent().getIntExtra("moveCount", 0);

        long minutes = elapsedTime / 60000;
        long seconds = (elapsedTime % 60000) / 1000;

        TextView infoTextView = findViewById(R.id.info_text);
        infoTextView.setText(String.format("Congratulations! You win!\nTime: %02d:%02d\nMoves: %d", minutes, seconds, moveCount));
    }

    public void onBackToGameClick(View view) {
        Intent intent = new Intent(GameInfoActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
