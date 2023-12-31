package com.akhgupta.easylocation;

import android.location.Location;

public interface EasyLocationListener {
    void onLocationPermissionGranted();
    void onLocationPermissionDenied();
    void onLocationReceived(Location location);
    void noLocationReceived();
    void onLocationProviderEnabled();
    void onLocationProviderDisabled();
}