package pk.com.ke.complaint.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationHelper {
    public static long LOCATION_REFRESH_TIME = 20000; // 30 seconds. The Minimum Time to get location update
    public static float LOCATION_REFRESH_DISTANCE =
            0;
    @SuppressLint("MissingPermission")
    public static void startListeningUserLocation(Context context,  MyLocationListener myListener) {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener =  new LocationListener()  {
            @Override
            public void onLocationChanged(Location location) {
                myListener.onLocationChanged(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE,
                locationListener
        );
    }
}

