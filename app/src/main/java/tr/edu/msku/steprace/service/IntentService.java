package tr.edu.msku.steprace.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import androidx.annotation.Nullable;

public class IntentService extends android.app.IntentService implements SensorEventListener {

    private int numOfSteps;
    private String userid;
    private SharedPreferences mPref;
    private SharedPreferences.Editor editor;
    public IntentService(String name) {
        super("Intent-Service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPref = getSharedPreferences("numOfStep", Context.MODE_PRIVATE);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        numOfSteps = 0 ;
        userid = intent.getStringExtra("userid");

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        editor = mPref.edit();
        editor.putInt("numOfSteps",numOfSteps++);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
