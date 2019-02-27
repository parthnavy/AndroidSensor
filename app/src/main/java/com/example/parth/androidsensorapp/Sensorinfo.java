package com.example.parth.androidsensorapp;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.ms.square.android.expandabletextview.ExpandableTextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

public class Sensorinfo extends AppCompatActivity implements SensorEventListener {

    TextView t;
    TextView t1;
    TextView t2, t3, t4, t5, t6, t7, t8;
    private float deltaX, deltaY, deltaZ;
    private float mLastX, mLastY, mLastZ;
    private boolean mInitialized;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyro;
    private Sensor mMagno, mPressure, mLight, mTemp, mHuimid, mProx,mtemp;
    FileOutputStream fout;

    private final float NOISE = (float) 2.0;
    private float Xaccel,Yaccel,Zaccel,Xgyro,Ygyro,Zgyro,Temp,Pressure,humid,illuminance,proxi;
    private long time;
    private  StringBuilder sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensorinfo);

        //FileOutputStream fout = openFileOutput("file name",Context.MODE_PRIVATE);

        t8 = (TextView) findViewById(R.id.textView13);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mInitialized = false;
        t=(TextView) findViewById(R.id.textView3);
        t1=(TextView)findViewById(R.id.textView6);
        t2=(TextView)findViewById(R.id.textView5);
        t3=(TextView)findViewById(R.id.textView7);
        t4=(TextView)findViewById(R.id.textView8);
        t5=(TextView)findViewById(R.id.textView9);
        t6=(TextView)findViewById(R.id.textView10);
        t7=(TextView)findViewById( R.id.textView13);
        t.setTextColor(Color.parseColor("#FF0000"));
        t1.setTextColor(Color.parseColor("#FF0000"));
        t2.setTextColor(Color.parseColor("#FF0000"));t.setTextColor(Color.parseColor("#FF0000"));
        t3.setTextColor(Color.parseColor("#FF0000"));
        t4.setTextColor(Color.parseColor("#FF0000"));
        t5.setTextColor(Color.parseColor("#FF0000"));
        t6.setTextColor(Color.parseColor("#FF0000"));
        t7.setTextColor(Color.parseColor("#FF0000"));





        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyro=mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mHuimid=mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mPressure=mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mMagno=mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mLight=mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mProx=mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mTemp=mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Thread thread=new Thread(){
            @Override
            public void run()
            {
                while (!isInterrupted())
                {
                    try{
                        Thread.sleep(1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                long date=System.currentTimeMillis();

                                String s=sdf.format(date);
                                //t7=(TextView)findViewById(R.id.textView13);
                                t7.setText("Current date and time: " + s);

                            }
                        });


                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        if(mAccelerometer!=null) {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t.setText(" Accelerometer not present in smartpone");

        }
        if (mGyro!=null)
        {
            mSensorManager.registerListener(this, mGyro, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            t1.setText(" Gyroscope not present");
        }
        if(mHuimid!=null)
        {
            mSensorManager.registerListener(this,mHuimid,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t2.setText(" Humidity sensor not present");
        }
        if(mPressure!=null)
        {
            mSensorManager.registerListener(this,mPressure,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t3.setText(" Pressure sensor not supported");
        }
        if(mMagno!=null)
        {
            mSensorManager.registerListener(this,mMagno,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t4.setText(" Magnetometer not supported");
        }
        if(mLight!=null)
        {
            mSensorManager.registerListener(this,mLight,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t5.setText(" Light sensor not present");
        }
        if (mProx!=null)
        {
            mSensorManager.registerListener(this,mProx,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t6.setText(" Proximity sensor not present");
        }
        if(mtemp!=null)
        {
            mSensorManager.registerListener(this,mtemp,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            t6.setText("\n Temperature sensor not present");
        }

    }



    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener( this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener( this, mtemp, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener( this, mProx, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener( this, mMagno, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener( this, mHuimid, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener( this, mGyro, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener( this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener( this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);

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
                Xaccel=deltaX;
                Yaccel=deltaY;
                Zaccel=deltaZ;
                t.setText(Html.fromHtml(" Accelerometer: <br>  X:" + deltaX + "m/s<sup>2</sup> <br>  Y:" + deltaY + "m/s<sup>2</sup> <br>  Z:" + deltaZ+"m/s<sup>2</sup>"));
            }

        }
        else if (sensor.getType()==Sensor.TYPE_GYROSCOPE)
        {
            t1.setText(" Gyroscope \n   X:" + event.values[0] + "rad/s\n    Y:" + event.values[1] +"rad/s\n     Z:" + event.values[2]);
        }
        else if(sensor.getType()==Sensor.TYPE_PRESSURE)
        {
            t2.setText(" Pressure sensor \n     Pressure:"+event.values[0] +"hPa" );

        }
        else if(sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY)
        {
            t3.setText(" Humidity sensor \n     Humidity:" + event.values[0] + "%");
        }
        else if(sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
        {
            t4.setText(" Magnetometer \n    X:" + event.values[0] + "micro-Tesla\n  Y:" + event.values[1] + "micro-Tesla\n  Z:" + event.values[2] + "micro-Tesla");
        }
        else if (sensor.getType()==Sensor.TYPE_LIGHT)
        {
            t5.setText(" Light sensor \n Illuminance:" + event.values[0] + "lx");
        }
        else if (sensor.getType()==Sensor.TYPE_PROXIMITY)
        {
            t6.setText(" Proximity sensor \n Proximity:" + event.values[0] + "cm");
        }
        else if (sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE)
        {
            t6.setText(" \nTemperature sensor \n Temperature:" + event.values[0] + "Degree celcius");
        }

    }
    public void record(View view) throws FileNotFoundException {
        jsondata j=new jsondata();

        JSONObject j1=j.makeJSONObject(time,Xaccel,Yaccel,Zaccel);
        //SharedPreferences sharedPreferences=getSharedPreferences("records",Context.);

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("record.txt", Context.MODE_APPEND));
            //outputStreamWriter.append("\n" + j1.toString());
            BufferedWriter fbw = new BufferedWriter(outputStreamWriter);
            fbw.write(j1.toString());
            fbw.close();
            //outputStreamWriter.close();
            Toast.makeText(this,"Saved data", Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    /*public void readfile()
    {

    }*/
    public void showrec(View view)
    {
        Intent intent = new Intent(this, displayrecords.class);
        // intent.putCharSequenceArrayListExtra("strings", sb);
        startActivity(intent);



    }


}
