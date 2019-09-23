package com.bjorkgren.nusen.model.json.smhi;

import android.util.Log;

import com.bjorkgren.nusen.model.Weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class TimeSerie {
    private static String TEMPERATURE = "t";
    private static String RAIN = "pmax";
    private static String RAIN_TYPE = "pcat";
    private static String WEATHER_TYPE = "Wsymb2";

    public String validTime;
    private ArrayList<Param> parameters;

    public float getWeatherValue(){
        float type = getWeatherType();
        float ret = 0f;
        if(type > 7)
            ret = -1f;
        else if(type < 4)
            ret = 1f;

        return ret;
    }

    public float getTemp(){
        return getParamValue(TEMPERATURE);
    }

    private float getWeatherType(){
        return getParamValue(WEATHER_TYPE);
    }

    public float getRainType(){
        return getParamValue(RAIN_TYPE);
    }

    public float getRain(){
        return getParamValue(RAIN);
    }

    public Weather getWeather(){
        float type = getWeatherType();
        if(type > 7)
            return Weather.REGN;
        if(type < 3)
            return Weather.SOL;
        if(type < 5)
            return Weather.HALVKLART;

        return Weather.MULET;
    }

    private float getParamValue(String param){
        for(Param p : parameters){
            if(p.name.equals(param) && p.values.size() > 0)
                return p.values.get(0);
        }

        return 666f;
    }

    public int getHourValue(){
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // blir rätt..
        try {
            Date current = isoFormat.parse(validTime);
            return current.getHours();
       //     long now_time = new Date().getTime() / 1000 / 60 / 60; // <-- timmar
      //      long forecast_time = current.getTime() / 1000 / 60 / 60;  // <-- timmar

        //    return (int) (forecast_time - now_time);
        }catch(Exception e){
            return -1;
        }
    }

    public int getRelativeHoursFromNow(){
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // blir rätt..
        try {
            Date current = isoFormat.parse(validTime);
            long now_time = new Date().getTime() / 1000 / 60 / 60; // <-- timmar
            long forecast_time = current.getTime() / 1000 / 60 / 60;  // <-- timmar

            return (int) (forecast_time - now_time);
        }catch(Exception e){
            return -1;
        }
    }

    public int getRelativeDay(){
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // blir rätt..
        try {
            Date current = isoFormat.parse(validTime);
            long now_time = new Date().getTime() / 1000 / 60 / 60 / 24; // <-- ett dygn
            long forecast_time = current.getTime() / 1000 / 60 / 60 / 24;  // <-- ett dygn

            return (int) (forecast_time - now_time);
        }catch(Exception e){
            return -1;
        }
    }
}
