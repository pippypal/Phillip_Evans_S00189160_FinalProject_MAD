package com.example.sequencegame;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class Game extends AppCompatActivity implements SensorEventListener {

    private final double NORTH_MOVE_FORWARD = 9.0;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD = 7.5;
    private final double WEST_FORWARD = 0.0;
    private final double WEST_BACKWARD = 0.0;
    boolean highLimit = false;      // detect high limit
    int counter = 0;                // step counter

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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

        // Can we get a north movement
        // you need to do your own mag calculating
        if ((x > NORTH_MOVE_FORWARD) && (highLimit == false)) {
            highLimit = true;
        }
        if ((x < NORTH_MOVE_BACKWARD) && (highLimit == true)) {
            // we have a tilt to the north
            counter++;
            highLimit = false;
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
}