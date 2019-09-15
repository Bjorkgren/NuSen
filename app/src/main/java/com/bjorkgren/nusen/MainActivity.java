package com.bjorkgren.nusen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjorkgren.nusen.communication.PlacenameFromPositionTask;
import com.bjorkgren.nusen.model.PlacenameListener;
import com.bjorkgren.nusen.model.Weather;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    final Looper looper = null;
    Criteria criteria;
    LocationListener locationListener;

    TextView skylt;
    ConstraintLayout mainLayout;
    int colorSun, colorRain, colorCloudy;
    int colorPrimary;

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }else{
            requestLocationOnce();
        }



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ( Build.VERSION.SDK_INT >= 23)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        colorCloudy = getResources().getColor(R.color.gradientCloudy);
        colorSun = getResources().getColor(R.color.gradientSun);
        colorRain = getResources().getColor(R.color.gradientRain);
        colorPrimary = getResources().getColor(R.color.colorPrimary);



        skylt = findViewById(R.id.skylt);
        skylt.post(new Runnable() {
            @Override
            public void run() {
                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(skylt.getTextSize());

                float width = textPaint.measureText(skylt.getText().toString());

                ViewGroup.LayoutParams layoutParams = skylt.getLayoutParams();
                layoutParams.width = (int)width;
                skylt.setLayoutParams(layoutParams);
            }
        });

        mainLayout = findViewById(R.id.main_layout);
        setWeatherSen(Weather.CLOUDY);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.e("Location Changes", location.toString());
                getPlacename(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("Status Changed", String.valueOf(status));
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Provider Enabled", provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Provider Disabled", provider);
            }
        };

        // Now first make a criteria with your requirements
        // this is done to save the battery life of the device
        // there are various other other criteria you can search for..
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_LOW);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_LOW);

        // Now create a location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // This is the Best And IMPORTANT part
        //final Looper looper = null;


    }

    private void requestLocationOnce(){
        try {
            locationManager.requestSingleUpdate(criteria, locationListener, looper);
        }catch(SecurityException se){
            Toast.makeText(getApplicationContext(),
                    "Application will not run without location services! " + se.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getPlacename(Location loc){

        new PlacenameFromPositionTask(new PlacenameListener() {

            @Override
            public void onFound(String p) {
                skylt.setText("\u00A0" + p.toUpperCase() + "\u00A0");
                //setWeatherSen(Weather.CLOUDY);
                //preferences.edit().putString(PLACENAME_SAVED, p).apply();
            }

            @Override
            public void onError() {

            }
        }).executeOnExecutor(THREAD_POOL_EXECUTOR, loc );
    }

    private void setWeatherSen(Weather w){
        int end;
        switch (w){
            case SUN:
                end = colorSun;
                break;
            case RAIN:
                end = colorRain;
                break;
            case CLOUDY:
                end = colorCloudy;
                break;
            default:
                return;
        }
        int colors[] = {colorPrimary, end};
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);

        gd.setCornerRadius(3f);
        mainLayout.setBackground(gd);
    }
/*
    private void updateSkyltSize(){
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(skylt.getTextSize());

        float width = textPaint.measureText((skylt.getText()).toString());

        ViewGroup.LayoutParams layoutParams = skylt.getLayoutParams();
        layoutParams.width = (int)width;
        skylt.setLayoutParams(layoutParams);
    } */

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),
                        "Application will not run without location services!", Toast.LENGTH_SHORT).show();
            }
            else {
                requestLocationOnce();
            }
        }
    }

    public void showSettings(View v){
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

}
