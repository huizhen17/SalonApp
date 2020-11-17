package com.example.salonapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Geocoding {

    private static final String TAG = "GeocodingLocation";
    public static double latitude,longitude;

    public static void getAddressFromLocation(final String locationAddress,
                                              final Context context) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocationName(locationAddress+"&key=AIzaSyBoqAjZUOfqUWr5GielbKUqQufkXR1Wr1U", 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        latitude = address.getLatitude();
                        longitude= address.getLongitude();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                }

            }
        };
        thread.start();
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        Geocoding.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        Geocoding.longitude = longitude;
    }
}
