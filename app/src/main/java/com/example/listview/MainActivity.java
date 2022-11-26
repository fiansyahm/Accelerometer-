package com.example.listview;

import static java.lang.Math.sqrt;

import android.app.Activity;
import android.content.Context;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private TextView nilaiX;
    private TextView nilaiY;
    private TextView nilaiZ;
    private TextView nilaiSpeed;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        nilaiX=findViewById(R.id.textX);
        nilaiY=findViewById(R.id.textY);
        nilaiZ=findViewById(R.id.textZ);
        nilaiSpeed=findViewById(R.id.Speed);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float vx=(x-last_x)/ diffTime ;
                float vy=(x-last_y)/ diffTime ;
                float vz=(x-last_z)/ diffTime ;
                float v= (float) Math.sqrt(vx*vx + vy*vy + vz*vz);
                last_x = x;
                last_y = y;
                last_z = z;
                nilaiX.setText("vx:"+vx);
                nilaiY.setText("vy:"+vy);
                nilaiZ.setText("vz:"+vz);
                nilaiSpeed.setText("v:"+v);

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}