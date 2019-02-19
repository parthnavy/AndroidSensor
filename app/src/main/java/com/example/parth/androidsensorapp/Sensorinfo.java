package com.example.parth.androidsensorapp;


import android.content.SharedPreferences;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Sensorinfo extends AppCompatActivity implements SensorEventListener {
    TextView t;
    TextView t1;
    TextView t2,t3,t4,t5;
    private float deltaX, deltaY, deltaZ;
    private float mLastX, mLastY, mLastZ;
    private boolean mInitialized;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyro;
    private Sensor mMagno,mPressure,mLight,mTemp,mHuimid;

    private final float NOISE = (float) 2.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensorinfo);
        mInitialized = false;
        t=(TextView)findViewById(R.id.textView2);
        t1=(TextView)findViewById(R.id.textView3);
        t2=(TextView)findViewById(R.id.textView4);
        t3=(TextView)findViewById(R.id.textView5);
        t4=(TextView)findViewById(R.id.textView6);
        //t4=(TextView)findViewById(R.id.textV)
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyro=mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mHuimid=mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mPressure=mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mMagno=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(mAccelerometer!=null) {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t.setText("Accelerometer not present in smartpone");

        }
        if (mGyro!=null)
        {
            mSensorManager.registerListener(this, mGyro, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            t1.setText("Gyroscope not present");
        }
        if(mHuimid!=null)
        {
            mSensorManager.registerListener(this,mHuimid,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t2.setText("Humidity sensor not present");
        }
        if(mPressure!=null)
        {
            mSensorManager.registerListener(this,mPressure,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t3.setText("Pressure sensor not supported");
        }
        if(mMagno!=null)
        {
            mSensorManager.registerListener(this,mMagno,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t4.setText("Magnetometer not supported");
        }

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener( this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    //@Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// can be safely ignored for this demo
    }

    public void onSensorChanged(SensorEvent event) {
        Sensor sensor=event.sensor;
        if(sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;

            mInitialized = true;
        } else {
            deltaX = Math.abs(mLastX - x);
            deltaY = Math.abs(mLastY - y);
            deltaZ = Math.abs(mLastZ - z);

            if (deltaX < NOISE) deltaX = (float) 0.0;
            if (deltaY < NOISE) deltaY = (float) 0.0;
            if (deltaZ < NOISE) deltaZ = (float) 0.0;
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            t.setText("Accelerometer values: X:" + deltaX + "Y:" + deltaY + "Z:" + deltaZ);
        }

        }
        else if (sensor.getType()==Sensor.TYPE_GYROSCOPE)
        {
            t1.setText("Gyroscope values are: X:" + event.values[0] + "Y:" + event.values[1] +"Z:" + event.values[2]);
        }
        else if(sensor.getType()==Sensor.TYPE_PRESSURE)
        {
            t2.setText("Pressure sensor values: Pressure:"+event.values[0] );

        }
        else if(sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY)
        {
            t3.setText("Humidity sensor value: Humidity:" + event.values[0]);
        }
        else if(sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
        {
            t4.setText("Magnetometer values: X:" + event.values[0] + "Y:" + event.values[1] + "Z:" + event.values[2]);
        }

    }
}
