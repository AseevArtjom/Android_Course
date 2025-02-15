package com.example.game_15;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.os.Handler;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Game_15 game = new Game_15();
    private TextView movesTextView;
    private TextView timeTextView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        movesTextView = findViewById(R.id.moves_text);
        timeTextView = findViewById(R.id.time_text);

        CreateButtons();
        startTimer();
    }

    private void ResetGame(){
        game = new Game_15();
    }

    private void CreateButtons() {
        GridLayout gridLayout = findViewById(R.id.game_grid);
        gridLayout.removeAllViews();

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                int value = game.getValue(j, i);
                if (0 == value) continue;

                Button button = new Button(this);
                button.setText(Integer.toString(value));
                button.setHeight(300);

                GridLayout.Spec row = GridLayout.spec(i);
                GridLayout.Spec col = GridLayout.spec(j);
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row, col);
                lp.setMargins(10, 10, 10, 10);
                gridLayout.addView(button, lp);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        String text = (String) btn.getText();
                        int val = Integer.parseInt(text);

                        Game_15.Coord coord = game.go(val);

                        if (coord.isValid()) {
                            GridLayout.Spec row = GridLayout.spec(coord.x);
                            GridLayout.Spec col = GridLayout.spec(coord.y);
                            GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row, col);
                            btn.setLayoutParams(lp);
                        }

                        updateGameStats();
                        if (game.checkWin()) {
                            showWinDialog();
                        }
                    }
                });
            }
        }
    }

    private void updateGameStats() {
        movesTextView.setText("Moves: " + game.getMoveCount());

        long elapsedTime = game.getElapsedTime();
        long minutes = elapsedTime / 60000;
        long seconds = (elapsedTime % 60000) / 1000;
        timeTextView.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    private void startTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateGameStats();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void showWinDialog() {
        int moveCount = game.getMoveCount();
        long elapsedTime = game.getElapsedTime();

        Intent intent = new Intent(MainActivity.this, GameInfoActivity.class);
        intent.putExtra("elapsedTime", elapsedTime);
        intent.putExtra("moveCount", moveCount);

        startActivity(intent);
    }


}
