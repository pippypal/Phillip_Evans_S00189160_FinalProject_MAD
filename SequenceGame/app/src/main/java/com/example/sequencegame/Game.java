package com.example.sequencegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class Game extends AppCompatActivity implements SensorEventListener {

    private final double NORTH_MOVE_FORWARD = 9.0;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD = 7.5;
    private final double WEST_FORWARD = 4;
    private final double WEST_BACKWARD = 2;
    boolean highLimit = false;
    boolean westLimit = false;
    boolean loss = false;
    boolean win = false;
    int score = 0;
    Button bBlue, bRed, bYellow, bGreen;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        bBlue = findViewById(R.id.BtnBlue);
        bRed = findViewById(R.id.BtnRed);
        bYellow = findViewById(R.id.BtnYellow);
        bGreen = findViewById(R.id.BtnGreen);
        
    }

    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = Math.abs(event.values[0]);
        float y = Math.abs(event.values[1]);
        float z = Math.abs(event.values[2]);

        if ((x > NORTH_MOVE_FORWARD) && (highLimit == false))
        {
            highLimit = true;
        }
        if ((x < NORTH_MOVE_BACKWARD) && (highLimit == true))
        {
            highLimit = false;
            flashButton(bBlue);
        }
        if ((z > WEST_FORWARD && (westLimit == false)))
        {
            westLimit = true;

        }
        if ((z < WEST_BACKWARD) && (westLimit == true) )//&& (x < 7))
        {
            westLimit = false;
            flashButton(bYellow);
        }
        if ((z < WEST_BACKWARD) && (westLimit == true))
        {
            westLimit = false;
            flashButton(bGreen);
        }

        if (loss == false)
        {
            Intent over = new Intent(this, GameOver.class);
            Bundle b = new Bundle();
            b.putInt("score", score);
            over.putExtras(b);
            startActivity(over);
        }

        if (win == true)
        {
            Intent start = new Intent(this, MainActivity.class);
            start.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Bundle b = new Bundle();
            b.putInt("score", score);
            startActivity(start);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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