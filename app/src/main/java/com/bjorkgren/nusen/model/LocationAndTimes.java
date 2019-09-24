package com.bjorkgren.nusen.model;

import android.location.Location;

public class LocationAndTimes {
    private Location loc;
    private int nuHour;
    private int senHour;

    public LocationAndTimes(Location loc, int nu, int sen){
        setLoc(loc);
        setNuHour(nu);
        setSenHour(sen);
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public int getNuHour() {
        return nuHour;
    }

    public void setNuHour(int nuHour) {
        this.nuHour = nuHour;
    }

    public int getSenHour() {
        return senHour;
    }

    public void setSenHour(int senHour) {
        this.senHour = senHour;
    }
}
