package com.example.khor_000.testapp;


/**
 * Store information about a saved location
 */
public class LocItem {
    private String locName;
    private double latitude;
    private double longtitude;

    public LocItem() {
        super();
    }

    public LocItem(String name, double lati, double logti) {
        super();
        this.locName = name;
        this.latitude = lati;
        this.longtitude = logti;
    }

    public String getName() { return locName; }
    public double getLatitude() { return latitude; }
    public double getLongtitude(){ return longtitude; }

}