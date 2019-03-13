package com.example.parth.androidsensorapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class jsondata extends JSONObject {
    public JSONObject makeJSONObject(String time, double accx, double accy, double accz,float temp) {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("Current time",time);
                    jsonObject.put("Accelerometer X value:",accx);
                    jsonObject.put("Accelerometer Y value",accy);
                    jsonObject.put("Accelerometer Z value",accz);
                    jsonObject.put("Room temperature",temp);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
                return jsonObject;
    }
}
