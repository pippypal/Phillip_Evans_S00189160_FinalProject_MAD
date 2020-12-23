package com.example.sequencegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int BLUE = 1;
    private final int RED = 4;
    private final int YELLOW = 2;
    private final int GREEN = 3;

    Button bBlue, bRed, bGreen, bYellow;
    int sequenceCount = 4, rand = 0;
    int[] gameSequence = new int[120];
    int arrayIndex = 0;
    int time = 6000;
    int interval = 1500;

    CountDownTimer ct = new CountDownTimer(time,  interval)
    {

        public void onTick(long millisUntilFinished) {
            oneButton();

        }

        @Override
        public void onFinish() {

            for (int i = 0; i < arrayIndex; i++)
                Log.d("game sequence", String.valueOf(gameSequence[i]));
            Intent game = new Intent(MainActivity.this, Game.class);
            //i.putExtra("numbers", array);
            startActivity(game);

            // int[] arrayB = extras.getIntArray("numbers");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bBlue = findViewById(R.id.BtnBlue);
        bRed = findViewById(R.id.BtnRed);
        bGreen = findViewById(R.id.BtnGreen);
        bYellow = findViewById(R.id.BtnYellow);
    }

    public void doPlay(View view)
    {
        ct.start();
//        Bundle b = getIntent().getExtras();
//        interval  += b.getInt("interval");
//        int score = b.getInt("score");
    }

    private void oneButton() {
        rand = getRandom(sequenceCount);

        switch (rand) {
            case 1:
                flashButton(bBlue);
                gameSequence[arrayIndex++] = BLUE;
                break;
            case 2:
                flashButton(bYellow);
                gameSequence[arrayIndex++] = YELLOW;
                break;
            case 3:
                flashButton(bGreen);
                gameSequence[arrayIndex++] = GREEN;
                break;
            case 4:
                flashButton(bRed);
                gameSequence[arrayIndex++] = RED;
                break;
            default:
                break;
        }   // end switch
    }

    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }

    public void flashButton(Button btn)
    {
        Handler handler = new Handler();
        Runnable r = () -> {
            btn.setPressed(true);
            btn.invalidate();
            btn.performClick();
            Handler handler1 = new Handler();
            Runnable r1 = () -> {
                btn.setPressed(false);
                btn.invalidate();
            };
            handler1.postDelayed(r1, 400);
        };
        handler.postDelayed(r, 400);
    }
}