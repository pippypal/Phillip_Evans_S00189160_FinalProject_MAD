package com.example.sequencegame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    int score = 0;
    TextView userScore;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        name = findViewById(R.id.etName);
        userScore = findViewById(R.id.tvGameScore);

    }

    public void showScore(View view)
    {

        String username = name.getText().toString();
        score = Integer.parseInt(userScore.getText().toString());
        Intent scores = new Intent(this, Scores.class);
        Bundle b = new Bundle();
        b.putInt("userScore", score);
        b.putString("username", username);
        scores.putExtras(b);
        startActivity(scores);
    }
}