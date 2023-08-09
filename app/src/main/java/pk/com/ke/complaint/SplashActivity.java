package pk.com.ke.complaint;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

import io.fabric.sdk.android.Fabric;
import okhttp3.ResponseBody;
import pk.com.ke.complaint.model.Material;
import pk.com.ke.complaint.model.MaterialListResponse.MaterialList;
import pk.com.ke.complaint.model.MaterialListResponse.Result;
import pk.com.ke.complaint.model.NotificationDetails;
import pk.com.ke.complaint.model.SingleTicket.SingleTicketDetail;
import pk.com.ke.complaint.rest.ApiClient;
import pk.com.ke.complaint.rest.ApiInterface;
import pk.com.ke.complaint.utils.MyActivity;
import pk.com.ke.complaint.utils.SecurePref;
import pk.com.ke.complaint.utils.StringUtils;
import pk.com.ke.complaint.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Nadeem Iqbal on 10/23/17.
 */

public class SplashActivity extends MyActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    static SecurePref myPrefSpot;
    static SecurePref.Editor myEditSpot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash);


        TextView tvVersion = (TextView) findViewById(R.id.version);
        tvVersion.setText("Version: " + getVersionName());

//        getMaterialList();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getBoolean(StringUtils.PREF_IS_LOGIN)) {
                    Utils.slideInRightAnim(SplashActivity.this, MainActivity.class);
                    //check if tracking service is running else start it
                    if(!isServiceRunning(LocationService.class)){
                        Intent service= new Intent(getApplicationContext(), LocationService.class);
                        String strUserId = getString(StringUtils.PREF_USER_USER_ID);
                        service.putExtra("user",strUserId);
//                            getApplicationContext().startService(service);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            getApplicationContext().startForegroundService(service);
                        } else {
                            getApplicationContext().startService(service);
                        }
//                        Intent service= new Intent(getApplicationContext(), LocationUpdateService.class);
//                        service.putExtra("user",getString(StringUtils.PREF_USER_USER_ID));
//                        getApplicationContext().startService(service);
                    }

                    logUser();
                    finish();

                } else {
                    Utils.slideInRightAnim(SplashActivity.this, LoginActivity.class);
                    requestLocationPermission();
                    finish();

                }
            }
        }, 1500);
    }

    private void requestLocationPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        100);
            }

        }
        else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    warning("KE - Complaint", "We need this permission for real-time tracking", "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                           requestLocationPermission();
                        }
                    });

                }
                return;
            }

        }
    }

    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        String strUserId = getString(StringUtils.PREF_USER_USER_ID);
        Crashlytics.setUserIdentifier(strUserId);
        Crashlytics.setUserEmail(getString(StringUtils.PREF_USER_PWD));
        Crashlytics.setUserName("User:" + strUserId);
    }






}




