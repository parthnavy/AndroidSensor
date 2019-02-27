package com.example.parth.androidsensorapp;

import org.json.JSONException;
import org.json.JSONObject;

public class jsondata extends JSONObject {
    public JSONObject makeJSONObject(long time, float accx, float accy, float accz) {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("Current time",time);
                    jsonObject.put("Accelerometer X value:",accx);
                    jsonObject.put("Accelerometer Y value",accy);
                    jsonObject.put("Accelerometer Z value",accz);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
                return jsonObject;
    }
}
