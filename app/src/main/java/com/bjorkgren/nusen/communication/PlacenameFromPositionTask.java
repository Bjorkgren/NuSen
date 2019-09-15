package com.bjorkgren.nusen.communication;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.bjorkgren.nusen.model.PlacenameListener;
import com.bjorkgren.nusen.model.json.placename.Address;
import com.bjorkgren.nusen.model.json.placename.Place;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class PlacenameFromPositionTask extends AsyncTask<Location, Void, String> {

    private final PlacenameListener listener;

    public PlacenameFromPositionTask(final PlacenameListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Location... locations) {
        Location loc = locations[0];
        double lat = loc.getLatitude();
        double lon = loc.getLongitude();

        DecimalFormat formatet = new DecimalFormat("#.0#####", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));

        String sURL = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + formatet.format(lat)+ "&lon=" + formatet.format(lon) + "&zoom=18&addressdetails=1"; //just a string
        //vid fel, kan testa denna  https://www.latlong.net/Show-Latitude-Longitude.html
        Log.e("gps", sURL);

        String contents = Helpers.getStringDataFromUrl(sURL);
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(contents));
        reader.setLenient(true);
        Place jsonplace = gson.fromJson(reader, Place.class);
        if(null == jsonplace)
            return "";
        Address ad = jsonplace.getAddress();
        if(null == ad)
            return "";



        if(!Empty(ad.getSuburb()))
            return ad.getSuburb();
        if(!Empty(ad.getTown()))
            return ad.getTown();
        if(!Empty(ad.getNeighbourhood()))
            return ad.getNeighbourhood();
        if(!Empty(ad.getRoad()))
            return ad.getRoad();
        if(!Empty(ad.getCounty()))
            return ad.getCounty();

        if(!Empty(ad.getState()))
            return ad.getState();
        if(!Empty(ad.getCountry()))
            return ad.getCountry();

        // annars footway, suburb

        return "";
    }

    @Override
    protected void onPostExecute(String found) {
        // Download is done
        if (null != found) {
            listener.onFound(found);
        } else {
            listener.onError();
        }
    }

    private boolean Empty(String s){
        if(null == s)
            return true;
        if(s.length() < 1)
            return true;
        return false;
    }
}