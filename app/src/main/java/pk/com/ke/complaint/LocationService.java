//package pk.com.ke.complaint;
//
//import android.app.AlarmManager;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.location.Location;
//import android.location.LocationManager;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.PowerManager;
//import android.provider.Settings;
//import android.telephony.ServiceState;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationCompat;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import javax.annotation.Nullable;
//
//import pk.com.ke.complaint.utils.DisplayToast;
//import pk.com.ke.complaint.utils.LocationHelper;
//import pk.com.ke.complaint.utils.MyLocationListener;
//import pk.com.ke.complaint.utils.serviceBroadcastReceiver;
//
//import static android.app.NotificationManager.IMPORTANCE_LOW;
//import static pk.com.ke.complaint.MainActivity.TAG;
//
//public class LocationService extends Service {
//    private static final String TAG = "LocationService";
//    public int counter = 0;
//    private Timer timer;
//    private TimerTask timerTask;
//    public static Location mLocation = null;
//    public static boolean isServiceStarted = false;
//    private PowerManager.WakeLock wakeLock = null;
//    private String NOTIFICATION_CHANNEL_ID = "my_notification_location";
//
//    private DatabaseReference mDatabase;
//    Handler mHandler;
//
//    public LocationService() {
//
//        mHandler = new Handler();
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        isServiceStarted = true;
//
//            Notification notification = createNotification();
//            startForeground(1, notification);
//
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
////        throw new UnsupportedOperationException("Not yet implemented");
//        return null;
//    }
//
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        super.onStartCommand(intent, flags, startId);
//        if (intent != null) {
//            final String user = intent.getStringExtra("user");
//            startTimer(user);
////            val action = intent.action
////            log("using an intent with action $action")
////            when (action) {
////                Actions.START.name -> startService()
////                Actions.STOP.name -> stopService()
////                else -> log("This should never happen. No action in the received intent")
////            }
//        }
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.i(TAG, "onDestroy: Service is destroyed :( ");
////        Intent broadcastIntent = new Intent(this, serviceBroadcastReceiver.class);
//////
////        sendBroadcast(broadcastIntent);
////        stoptimertask();
//        isServiceStarted = false;
//    }
//
//    public void startTimer(String user) {
//        if(isServiceStarted){
//            return;
//        }
//
//        isServiceStarted = true;
//        setServiceState(this, ServiceState.STARTED);
//
//        wakeLock = (PowerManager)(getSystemService(Context.POWER_SERVICE)).run {
//            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
//                acquire()
//            }
//        }
//
//        //initialize the TimerTask's job
////        initialiseTimerTask();
//
//        //schedule the timer, to wake up every 1 second
////        timer.schedule(timerTask, 30000, 1000); //
//        LocationHelper.startListeningUserLocation(
//                this, new MyLocationListener() {
//                    @Override
//                    public void onLocationChanged(Location location) {
//                        mLocation = location;
//                        if(mLocation != null){
//
//                            addDataToFirebaseDB(user, location.getLatitude(), location.getLongitude());
//                            mHandler.post(new DisplayToast(getApplicationContext(), "Location Fatechedddd!"+location.getLatitude() + ", "+location.getLongitude()));
//                        }
//                    }
//
//
//                }
//        );
//    }
//
//
//
//    private Notification createNotification() {
//        String notificationChannelId = "ENDLESS SERVICE CHANNEL";
//
//        // depending on the Android API that we're dealing with we will have
//        // to use a specific method to create the notification
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE) ;
//
//
//
//                NotificationChannel notificationChannel = new NotificationChannel(
//                        notificationChannelId,
//                        "Endless Service notifications channel",
//                        NotificationManager.IMPORTANCE_HIGH
//                );
//                notificationChannel.setDescription("Endless Service channel");
//                notificationChannel.enableLights(true);
//                notificationChannel.setLightColor(Color.RED);
//                notificationChannel.enableVibration(true);
//                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//                notificationChannel.setSound(null, null);
//                notificationManager.createNotificationChannel(notificationChannel);
//
//
//        }
//
//         PendingIntent pendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
//                PendingIntent.getActivity(this, 0, notificationIntent, 0)
//        }
//
//        Notification.Builder builder;
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//             builder =
//                    new Notification.Builder(this, notificationChannelId);
//        }else {
//            builder =
//                    new Notification.Builder(this);
//        }
//
//
//
//        return builder
//                .setContentTitle("Endless Service")
//                .setContentText("This is your favorite endless service working")
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setTicker("Ticker text")
//                .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
//                .build();
//    }
//    public void initialiseTimerTask() {
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                Log.i(TAG, "Timer is running " + counter++);
//            }
//        };
//    }
//
//    public void stoptimertask() {
//        //stop the timer, if it's not already null
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
//    }
//
//    protected boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    protected static Boolean isLocationEnabled(Context context)
//    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//// This is new method provided in API 28
//            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//            return lm.isLocationEnabled();
//        } else {
//// This is Deprecated in API 28
//            int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
//                    Settings.Secure.LOCATION_MODE_OFF);
//            return  (mode != Settings.Secure.LOCATION_MODE_OFF);
//
//        }
//    }
//
//    private void addDataToFirebaseDB(String user, double lat, double lng){
//
//
//// ...
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        // Create a new user with a first and last name
//        Map<String, Object> location = new HashMap<>();
//        location.put("user", user);
//        location.put("latitude", lat);
//        location.put("longitude", lng);
//        location.put("updated_at", new Date().toString());
//
//// Add a new document with a generated ID
//        mDatabase.child(Config.Firebase_DB_NAME)
//                .child(user)
//                .setValue(location)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    //                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(Void iVoid) {
////                        public void onSuccess(DocumentReference iVoid) {
//                        Log.d("TAG", "DocumentSnapshot added");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });
//    }
//
//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        super.onTaskRemoved(rootIntent);
//        Intent restartServiceIntent = new Intent(getApplicationContext(), LocationService.class);
//        restartServiceIntent.setPackage("pk.com.ke.complaint.qa");
//        PendingIntent restartServicePendingIntent = PendingIntent.getService(this, 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
//        getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//         AlarmManager alarmService = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager;
//        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePendingIntent);
//    }
//
//
//}

package pk.com.ke.complaint;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;

import pk.com.ke.complaint.utils.DisplayToast;
import pk.com.ke.complaint.utils.LocationHelper;
import pk.com.ke.complaint.utils.MyLocationListener;
import pk.com.ke.complaint.utils.serviceBroadcastReceiver;

import static android.app.NotificationManager.IMPORTANCE_LOW;
import static pk.com.ke.complaint.MainActivity.TAG;

public class LocationService extends Service {
    private static final String TAG = "LocationService";
    public int counter = 0;
    private Timer timer;
    private TimerTask timerTask;
    public static Location mLocation = null;
    public static boolean isServiceStarted = false;
    private String NOTIFICATION_CHANNEL_ID = "my_notification_location";

    private DatabaseReference mDatabase;
    Handler mHandler;

    public LocationService() {

        mHandler = new Handler();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isServiceStarted = true;
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                        .setOngoing(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             NotificationManager notificationManager =
                     (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_ID, IMPORTANCE_LOW
            );
            notificationChannel.setDescription(NOTIFICATION_CHANNEL_ID);
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);
            startForeground(1, builder.build());
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            final String user = intent.getStringExtra("user");
            startTimer(user);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: Service is destroyed :( ");
        Intent broadcastIntent = new Intent(this, serviceBroadcastReceiver.class);
//
        sendBroadcast(broadcastIntent);
//        stoptimertask();
        isServiceStarted = false;
    }

    public void startTimer(String user) {
        timer = new Timer();

        //initialize the TimerTask's job
//        initialiseTimerTask();

        //schedule the timer, to wake up every 1 second
//        timer.schedule(timerTask, 30000, 1000); //
        LocationHelper.startListeningUserLocation(
                this, new MyLocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mLocation = location;
                        if(mLocation != null){

                            addDataToFirebaseDB(user, location.getLatitude(), location.getLongitude());
                            mHandler.post(new DisplayToast(getApplicationContext(), "Location Fatechedddd!"+location.getLatitude() + ", "+location.getLongitude()));
                        }
                    }


            }
        );
    }
    public void initialiseTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "Timer is running " + counter++);
            }
        };
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
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
}