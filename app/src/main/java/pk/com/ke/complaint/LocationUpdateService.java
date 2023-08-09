package pk.com.ke.complaint;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import pk.com.ke.complaint.utils.DisplayDialog;
import pk.com.ke.complaint.utils.DisplayToast;

import static pk.com.ke.complaint.MainActivity.TAG;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LocationUpdateService extends IntentService {

    private FusedLocationProviderClient mFusedLocationClient; // Object used to receive location updates

    private LocationRequest locationRequest;

    FirebaseFirestore db;
    private DatabaseReference mDatabase;


    Handler mHandler;
    public static  Boolean run=true;

    public LocationUpdateService() {
        super("LocationUpdate");
        mHandler = new Handler();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
//        mHandler.post(new DisplayToast(this, "In on HandleIntent!"));
        if (intent != null) {
            final String user = intent.getStringExtra("user");
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            locationRequest = LocationRequest.create();
            locationRequest.setInterval(30000); // 5 second delay between each request
            locationRequest.setFastestInterval(30000); // 5 seconds fastest time in between each request
//            locationRequest.setSmallestDisplacement(1); // 1 meters minimum displacement for new location request
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // enables GPS high accuracy location requests

            db = FirebaseFirestore.getInstance();
            run=true;

            while(run){
                sendUpdatedLocationMessage(user);
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            stopSelf();

        }
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected static Boolean isLocationEnabled(Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
// This is new method provided in API 28
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm.isLocationEnabled();
        } else {
// This is Deprecated in API 28
            int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF);
            return  (mode != Settings.Secure.LOCATION_MODE_OFF);

        }
    }

    private void sendUpdatedLocationMessage(final String userName) {
        try {
//            LocationManager locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
//            boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            boolean isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isNetworkAvailable()){
                //Internet is not present
                mHandler.post(new DisplayToast(getApplicationContext(), "Internet unavailable! Kindly open internet."));


            }
            else if(!isLocationEnabled(getApplicationContext())){
                //Location not enabled
                mHandler.post(new DisplayToast(getApplicationContext(), "Location unavailable! Kindly open GPS location."));
            } else{
                Task<Location> task =  mFusedLocationClient.getLastLocation();

                task.addOnSuccessListener(location -> {

                    if (location != null) {
                        //update location
//                        addDataToFirestore(userName, location.getLatitude(), location.getLongitude());
                        addDataToFirebaseDB(userName, location.getLatitude(), location.getLongitude());
//                        mHandler.post(new DisplayToast(getApplicationContext(), "Location Fatechedddd!"+location.getLatitude() + ", "+location.getLongitude()));

                    }

                });
                task.addOnFailureListener(e -> {
                    mHandler.post(new DisplayToast(getApplicationContext(), ""+e.getMessage()));


                });
//                mHandler.post(new DisplayToast(this, "In on FUnction!"));
//                addDataToFirestore("test", 1, 1);
//                mFusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
//                    @Override
//                    public void onLocationResult(LocationResult locationResult) {
//
//                        Location location = locationResult.getLastLocation();
////                    LinkedHashMap<String, String> message = getNewLocationMessage(userName, location.getLatitude(), location.getLongitude());
//
//
//                        Log.e("Loc",location.getLatitude() + ", "+location.getLongitude());
//                        mHandler.post(new DisplayToast(getApplicationContext(), "Location Fatechedddd!"+location.getLatitude() + ", "+location.getLongitude()));
//
//                        //TODO: Upload to firebase
//                        addDataToFirestore(userName, location.getLatitude(), location.getLongitude());
//
//
//                    }
//                }, Looper.myLooper());
//
            }


        } catch (SecurityException e) {
            Log.e("Error","here");
            e.printStackTrace();

        }
    }

    /*
        Helper method that takes in latitude and longitude as parameter and returns a LinkedHashMap representing this data.
        This LinkedHashMap will be the message published by driver.
     */
    private LinkedHashMap<String, String> getNewLocationMessage(String user, double lat, double lng) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("user",user);
        map.put("lat", String.valueOf(lat));
        map.put("lng", String.valueOf(lng));
        return map;
    }


    private void addDataToFirebaseDB(String user, double lat, double lng){


// ...
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // Create a new user with a first and last name
        Map<String, Object> location = new HashMap<>();
        location.put("user", user);
        location.put("latitude", lat);
        location.put("longitude", lng);
        location.put("updated_at", new Date().toString());

// Add a new document with a generated ID
        mDatabase.child(Config.Firebase_DB_NAME)
                .child(user)
                .setValue(location)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(Void iVoid) {
//                        public void onSuccess(DocumentReference iVoid) {
                        Log.d("TAG", "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void addDataToFirestore(String user, double lat, double lng){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> location = new HashMap<>();
        location.put("user", user);
        location.put("latitude", lat);
        location.put("longitude", lng);
        location.put("updated_at", new Date().toString());

// Add a new document with a generated ID
        db.collection("locations")
                .document(user)
                .set(location)
//                .add(location)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    //                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(Void iVoid) {
//                        public void onSuccess(DocumentReference iVoid) {
                        Log.d("TAG", "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }



}

