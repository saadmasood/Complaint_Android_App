package pk.com.ke.complaint.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import pk.com.ke.complaint.LocationService;

import static pk.com.ke.complaint.utils.MyActivity.getString;

public class serviceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(serviceBroadcastReceiver.class.getSimpleName(), "Service Stopped, but this is a never ending service.");
        Intent service= new Intent(context, LocationService.class);
        String strUserId = getString(StringUtils.PREF_USER_USER_ID);
        service.putExtra("user",strUserId);
//                            getApplicationContext().startService(service);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(service);
        } else {
            context.startService(service);
        }
    }
}