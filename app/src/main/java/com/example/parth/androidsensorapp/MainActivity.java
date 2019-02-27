package com.example.parth.androidsensorapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t=(TextView) findViewById(R.id.textView);
        t.setTextColor(Color.parseColor("#FF0000"));


    }
    public  void Senseinfo(View view)
    {
        Intent intent = new Intent(this, Sensorinfo.class);
        startActivity(intent);
    }
}
