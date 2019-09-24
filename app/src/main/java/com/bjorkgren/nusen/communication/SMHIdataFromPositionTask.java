package com.bjorkgren.nusen.communication;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.bjorkgren.nusen.model.LocationAndTimes;
import com.bjorkgren.nusen.model.Weather;
import com.bjorkgren.nusen.model.WeatherdataListener;

import com.bjorkgren.nusen.model.json.smhi.Smhi;
import com.bjorkgren.nusen.model.json.smhi.TimeSerie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SMHIdataFromPositionTask extends AsyncTask<LocationAndTimes, Void, String> {

    private final WeatherdataListener listener;
    private int nowTemp, laterTemp;
    private Weather wNow, wLater;

    public SMHIdataFromPositionTask(final WeatherdataListener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(LocationAndTimes... locationAndTimes) {
        LocationAndTimes lats = locationAndTimes[0];
        DecimalFormat formatet = new DecimalFormat("#.0#####", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        String lat = formatet.format(lats.getLoc().getLatitude());
        String lon = formatet.format(lats.getLoc().getLongitude());

        nowTemp = -666;
        laterTemp = -666;
        wNow = Weather.HALVKLART;
        wLater = Weather.REGN;

        String sURL = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/" + lon + "/lat/" + lat +"/data.json";
        String contents = Helpers.getStringDataFromUrl(sURL);
        Log.e("contents", contents);
        //Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(contents));
        //reader.setLenient(true);  <-- endast om det är ful json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Smhi smhi = gson.fromJson(reader, Smhi.class);


        for(TimeSerie ts : smhi.timeSeries) {
            //Log.e("temp", "temp at " + ts.validTime + " is " + ts.getWeatherType() + ". Rain is " + ts.getRain() + ".  Rel day " + ts.getRelativeDay());
            int hr = ts.getHourValue();
            if(lats.getNuHour() == hr && nowTemp == -666)
                nowTemp = (int)(ts.getTemp() + 0.5f);
            if(lats.getSenHour() == hr && laterTemp == -666)
                laterTemp = (int)(ts.getTemp() + 0.5f);
            //Log.e("from", "rel hour: " + hr + "  ; temp " + ts.getTemp());
        }

        //data.get(2).setHigh(10);
        //data.get(2).setLow(5);
        //data.get(2).setWeather(Weather.RAIN);


        return "x";
    }

    @Override
    protected void onPostExecute(String res) {
        // Download is done
        if (res.length() > 0) {
            listener.onResult(nowTemp, laterTemp, wNow, wLater);
        } else {
            listener.onError();
        }
    }
}

/*
https://docs.google.com/spreadsheets/d/14g0wNlTvbRP2aGjSU2BJE0hT0jkyqlJFme0ujUsXKVc/

1. YR
    https://api.met.no/weatherapi/locationforecast/1.9/?lat=60.10&lon=9.58
    https://api.met.no/weatherapi/locationforecast/1.9/documentation

2. SMHI
    https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/16.158/lat/58.5812/data.json
    http://opendata.smhi.se/apidocs/metfcst/examples.html
    https://kundo.se/org/smhi/d/hur-far-jag-fram-vaderdata-for-hassela-mha-ert-api/

3. FMI
    http://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=getFeature&storedquery_id=fmi::forecast::hirlam::surface::point::simple&latlon=53,13
    http://opendata.fmi.fi/wfs?service=WFS&version=2.0.0&request=describeStoredQueries&
    https://en.ilmatieteenlaitos.fi/open-data-manual

4. USA
    https://api.weather.gov/points/39.7456,-97.0892
    https://api.weather.gov/gridpoints/TOP/31,80/forecast/hourly
    https://www.weather.gov/documentation/services-web-api

 */