package com.example.parth.androidsensorapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class displayrecords extends AppCompatActivity {
    TextView t;
    StringBuilder sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayrecords);
        t=(TextView)findViewById(R.id.textView2);
        try {

            Context context = this;
            FileInputStream fis = context.openFileInput("record.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
              sb.append(line);

            }

        }

        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        //sb = new StringBuilder();
        String[] lines = sb.toString().split("\n");
        for(String s: lines)
        {
            t.setText("\n" + s);
        }

    }

}
