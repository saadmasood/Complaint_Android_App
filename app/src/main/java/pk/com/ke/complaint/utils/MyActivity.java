package pk.com.ke.complaint.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.bugfender.sdk.Bugfender;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import pk.com.ke.complaint.LocationService;
import pk.com.ke.complaint.LocationUpdateService;
import pk.com.ke.complaint.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.regex.Pattern;

//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import io.github.inflationx.calligraphy3.CalligraphyConfig;

import static pk.com.ke.complaint.Config.IS_LOG_ALLOWED;

public abstract class MyActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    static SecurePref myPrefSpot;
    static SecurePref.Editor myEditSpot;
    protected boolean allowSynch = true;
    static Context context;
    // For GPS
    LocationManager lm;
    Location location;
    boolean userLoggedIn = false;
    boolean isUserFromFb = false;

    protected ProgressDialog dialog;
    private int fragmentLayoutId;

//    private static final String PASSWORD_PATTERN =
//            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[`~!@#$%^&*()_+-=|\\\\[\\]\\{\\}\\'\":;/?>.<,]).{8,20})";

    public MyActivity() {
        super();
        context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

//        setRequestedOrientation(AppSettings.SCREEN_ORIENTATION);

        myPrefSpot = new SecurePref(this, getSharedPreferences(StringUtils.PREFS_PC_NAME, MODE_PRIVATE));
//        SharedPreferences sp = getSharedPreferences(StringUtils.PREFS_PC_NAME, MODE_PRIVATE);
//        myPrefSpot = new SecurePref(this, sp);
//        myEditSpot = myPrefSpot.edit();
        myEditSpot = myPrefSpot.edit();
    }

    protected boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
//                Log.e("Service ", "already running");
                return true;
            }
        }
//        Log.e("Service ", "not running");
        return false;
    }



    public void scrollMyListViewToBottom(final ListView lv) {
        try {
            lv.post(new Runnable() {
                @Override
                public void run() {
                    // Select the last row so it will scroll into view...
                    lv.setSelection(lv.getAdapter().getCount() - 1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void copyToClipBoard(String textToCopy) {
        int sdk_Version = Build.VERSION.SDK_INT;
        if (sdk_Version < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(textToCopy);
            Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Text Label", textToCopy);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        }
    }

    public String getFbPictureUrl(String fbId) {
        String url = "https://graph.facebook.com/" + fbId + "/picture";
        log("FB Image URL", url);
        return url;
    }

//    public static void writeString2(Context ctx, String key, String value) {
//
//        SharedPreferences sp = ctx.getSharedPreferences(StringUtils.PREFS_PC_NAME, MODE_PRIVATE);
//        myPrefSpot = new SecurePref(ctx, sp);
//        SecurePref.Editor myEditSpot = myPrefSpot.edit();
//
//        myEditSpot.putString(key, value);
//        myEditSpot.commit();
//    }

    public static void writeInt(String key, int value) {
        myEditSpot.putInt(key, value);
        myEditSpot.commit();
    }

    public static void writeBoolean(String key, Boolean value) {
        myEditSpot.putBoolean(key, value);
        myEditSpot.commit();
    }

//    public static String getString2(Context ctx, String key) {
//        SharedPreferences sp = ctx.getSharedPreferences(StringUtils.PREFS_PC_NAME, MODE_PRIVATE);
//        myPrefSpot = new SecurePref(ctx, sp);
//        return myPrefSpot.getString(key, "");
//    }

//    public static int getInt(String Key) {
//        return myPrefSpot.getInt(Key, 0);
//    }
//
//    public static int getInt(String Key, int val) {
//        return myPrefSpot.getInt(Key, -1);
//    }
//
//    public static Boolean getBoolean(String Key) {
//        return getBoolean(Key, false);
//    }
//
//    public static Boolean getBoolean(String Key, boolean defValue) {
//        return myPrefSpot.getBoolean(Key, defValue);
//    }

    public static void writeString(String key, String value) {
        myEditSpot.putString(key, value);
        myEditSpot.commit();
    }

    public static boolean isSDCardAvailable() {
        Boolean isSDPresent = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);

        return isSDPresent;
    }


    @SuppressLint("NewApi")
    public static void showProgress(Context context, final boolean show,
                                    final View mProgressView, final View mFormView) {
//        Log.e("show progress", "show progress " + show);
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        try {
            if (hasHoneycombMR2()) {
                int shortAnimTime = context.getResources().getInteger(
                        android.R.integer.config_shortAnimTime);

                mProgressView.setVisibility(View.VISIBLE);
                mProgressView.animate().setDuration(shortAnimTime)
                        .alpha(show ? 1 : 0)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mProgressView.setVisibility(show ? View.VISIBLE
                                        : View.GONE);
                            }
                        });

                mFormView.setVisibility(View.VISIBLE);
                mFormView.animate().setDuration(shortAnimTime)
                        .alpha(show ? 0 : 1)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mFormView.setVisibility(show ? View.GONE
                                        : View.VISIBLE);
                            }
                        });
            } else {
                // The ViewPropertyAnimator APIs are not available, so simply
                // show
                // and hide the relevant UI components.
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        } catch (Exception e) {
            // TODO: handle exception
            // e.printStackTrace();
        }
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR2() {
        return Build.VERSION.SDK_INT >= 13;
    }

    public Typeface setTypeFace(String fontName) {
        Typeface custom_font = Typeface.createFromAsset(getAssets(),
                fontName);
        return custom_font;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public String getGalleryPath() {
        return Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/Camera/";
    }

    public Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }


    public void loadFragment(int layoutId, Fragment fragToLoad, boolean addToBackStack, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (addToBackStack) {
            ft = ft.replace(layoutId, fragToLoad).addToBackStack(tag == null ? null : tag);
        } else {
            ft = ft.replace(layoutId, fragToLoad);
        }
        ft.commit();
    }

    public void loadFragment(int layoutId, Fragment fragToLoad, boolean addToBackStack) {
        loadFragment(layoutId, fragToLoad, addToBackStack, null);
    }

    public void loadFragment(int layoutId, Fragment fragToLoad) {
        loadFragment(layoutId, fragToLoad, false, null);
    }

    public void loadFragment(Fragment fragToLoad, boolean addToBackStack, String tag) {
        loadFragment(getFragmentLayoutId(), fragToLoad, addToBackStack, tag);
    }

    public void loadFragment(Fragment fragToLoad, boolean addToBackStack) {
        loadFragment(getFragmentLayoutId(), fragToLoad, addToBackStack, null);
    }

    public void loadFragment(Fragment fragToLoad) {
        loadFragment(getFragmentLayoutId(), fragToLoad, false, null);
    }


    protected void clearLoginPrefs() {
        writeBoolean(StringUtils.PREF_IS_LOGIN, false);
        //stop background service
        stopService(new Intent(this, LocationService.class));
        LocationUpdateService.run = false;


//        try {
//            writeString(StringUtils.PREF_USER_USERTYPE, String.valueOf(Constants.INT_USER_TYPE_AD));
//            writeString(StringUtils.PREF_USER_USER_ID, "");
//            writeString(StringUtils.PREF_USER_PWD, "");
//            writeString(StringUtils.PREF_USER_SESSION, "");
//
//            writeString(StringUtils.PREF_USER_USER_NAME_AD, "");
//            writeString(StringUtils.PREF_USER_FULLNAME, "");
//            writeString(StringUtils.PREF_USER_IMAGEURL, "");
//            writeString(StringUtils.PREF_USER_MOBILE, "");
//
//
//            writeString(StringUtils.PREF_USER_STATIC_AD_PWD, "");
//            writeString(StringUtils.PREF_USER_STATIC_AD_ID, "");
//            writeString(StringUtils.PREF_COUNT_SR, "");
//            writeString(StringUtils.PREF_COUNT_LEAVE, "");
//            writeString(StringUtils.PREF_COUNT_H2H, "");
//
//            writeBoolean(StringUtils.PREF_SHOW_NOTIFICATIONS, true);
//            writeString(StringUtils.PREF_USER_MODE, "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void showDialog(ProgressDialog pd) {
        try {
            if (!pd.isShowing()) {
                pd.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialog(ProgressDialog pd) {
        try {
            if (pd.isShowing()) {
                pd.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(String message) {
        try {
            dialog = new ProgressDialog(this);
            String title;
            if (!isEmpty(message))
                title = message;
            else
                title = "please wait...";

            dialog.setMessage(title);
            dialog.setCanceledOnTouchOutside(false);

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSessionID() {
        return getString(StringUtils.PREF_USER_SESSION);
    }

    public String getUserId() {
        return getString(StringUtils.PREF_USER_USER_ID);
    }

    public String getLastEmail() {
        String email = getString(StringUtils.PREF_LAST_LOGIN_CREDENTIAL);
        if (!isEmpty(email)) {
            return email;
        } else {
            return "";
        }
    }

    public void share(String textToShare) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textToShare + " ");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPasswordMatching(String pass1, String pass2) {
        if (!isEmpty(pass1) && !isEmpty(pass2)) {
            if (pass1.equals(pass2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        getPrefs();
    }

    protected void getPrefs() {
        // TODO Auto-generated method stub
//        userLoggedIn = getBoolean(StringUtils.PREF_IS_LOGIN);
        try {
            String log = getString(StringUtils.PREF_IS_LOGIN);
            if (log.equalsIgnoreCase("true")) {
                userLoggedIn = true;
            } else {
                userLoggedIn = false;
            }
        } catch (Exception e) {
            userLoggedIn = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void confirmation(String title, String message,
                                DialogInterface.OnClickListener oKListener,
                                DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_menu_help)
                .setTitle(title).setMessage(message)
                .setPositiveButton("OK", oKListener)
                .setNegativeButton("Cancel", cancelListener).show();
    }

    protected void confirmation(String title, String message,
                                DialogInterface.OnClickListener oKListener) {
        confirmation(title, message, oKListener, null);
    }

    public void warning(String title, String message,
                        String positiveButtonText,
                        DialogInterface.OnClickListener positiveButtonClickListener,
                        String negativeButtonText) {

        if (negativeButtonText == null) {
            warning(title, message, positiveButtonText,
                    positiveButtonClickListener);

        } else {
            warning(title, message, positiveButtonText,
                    positiveButtonClickListener, negativeButtonText,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
        }
    }

    public void warning(String title, String message,
                        String positiveButtonText,
                        DialogInterface.OnClickListener positiveButtonClickListener,
                        String negativeButtonText,
                        DialogInterface.OnClickListener negativeButtonClickListener) {
        // TODO Auto-generated method stub

        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText,
                        positiveButtonClickListener)
                .setNegativeButton(negativeButtonText,
                        negativeButtonClickListener).show();
    }

    public void warning(String title, String message,
                        String positiveButtonText,
                        DialogInterface.OnClickListener positiveButtonClickListener) {
        // TODO Auto-generated method stub

        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText,
                        positiveButtonClickListener)
                .show();
    }

    protected void warning(String title, String message) {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher).setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                }).show();
    }

    protected void warning(String title, String message,
                           DialogInterface.OnClickListener oKListener) {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher).setTitle(title)
                .setMessage(message).setPositiveButton("OK", oKListener).show();
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void toast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast("No working internet connection found");
            }
        });
        return false;
    }

    public boolean isEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public boolean isPhone(String phone) {
        Pattern pattern = Patterns.PHONE;
        return pattern.matcher(phone).matches();
    }

    public boolean isAge(String str_Age) {
        int age = Integer.parseInt(str_Age);
        if (age > 1 && age < 150) {
            return true;
        } else
            return false;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setCancelable(false);
        alertDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface arg0) {

                // finish();

            }
        });
        // Setting Dialog Title
        alertDialog.setTitle("Location");

        // Setting Dialog Message
        alertDialog
                .setMessage("Unable find your location. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    public void log(int text) {
        log("" + text);
    }

    public void log(long text) {
        log("" + text);
    }

    public void log(double text) {
        log("" + text);
    }

    public void log(float text) {
        log("" + text);
    }

    public void log(String text) {
        log("" + getApplicationContext().getClass().getSimpleName(), "" + text);
    }

    public void log(int key, String text) {
        log("" + key, "" + text);
    }

    public void log(long key, int text) {
        log("" + key, "" + text);
    }

    public void log(float key, int text) {
        log("" + key, "" + text);
    }

    public void log(double key, int text) {
        log("" + key, "" + text);
    }

    public void log(String key, int text) {
        log("" + key, "" + text);
    }

    public void log(String key, long text) {
        log("" + key, "" + text);
    }

    public void log(String key, double text) {
        log("" + key, "" + text);
    }

    public void log(String key, float text) {
        log("" + key, "" + text);
    }

    public static void log(String key, String text) {
        if (IS_LOG_ALLOWED) {
            Log.e(key, text);
        }

        if (text != null && text.length() > 97) {
            text = text.substring(0, 97) + "...";
        }

        if (key != null && key.length() > 97) {
            key = key.substring(0, 97) + "...";
        }

        try {
            Bugfender.e(key, text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("LOG")
                .putContentType("Log")
                .putContentId("UserID:" + getString(StringUtils.PREF_USER_USER_ID))
                .putCustomAttribute("Key", "" + key)
                .putCustomAttribute("Msg", "" + text));

    }

    public void startMyActivity(Class<?> classToLoad) {
        startMyActivity(classToLoad, false);
    }

    public void startMyActivity(Class<?> classToLoad, boolean finishCurrentAct) {
        Intent i = new Intent(getApplicationContext(), classToLoad);
        startActivity(i);
        if (finishCurrentAct) {
            finish();
        }
    }

    public void startMyActivityForResult(Class<?> classToLoad, int requestCode) {
        Intent i = new Intent(getApplicationContext(), classToLoad);
        startActivityForResult(i, requestCode);
    }

    public void startMyActivityForResult(Class<?> classToLoad, Bundle b, int requestCode) {
        Intent i = new Intent(getApplicationContext(), classToLoad);
        if (b != null) {
            i.putExtras(b);
        }
        startActivityForResult(i, requestCode);
    }

    public void startMyActivity(Class<?> classToLoad, boolean finishCurrentAct,
                                boolean clearTop) {
        Intent i = new Intent(getApplicationContext(), classToLoad);
        if (clearTop) {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(i);
        if (finishCurrentAct) {
            finish();
        }
    }

    public void startMyActivity(Class<?> classToLoad, Bundle b, boolean finishCurrentAct,
                                boolean clearTop) {
        Intent i = new Intent(getApplicationContext(), classToLoad);
        if (b != null)
            i.putExtras(b);
        if (clearTop) {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(i);
        if (finishCurrentAct) {
            finish();
        }
    }

    public void startMyActivity(Class<?> classToLoad, Bundle b) {

        if (b != null) {
            startMyActivity(classToLoad, b, false);
        } else {
            startMyActivity(classToLoad, false);
        }
    }

    public void startMyActivity(Class<?> classToLoad, Bundle b,
                                boolean finishCurrentAct) {

        if (b != null) {
            Intent i = new Intent(getApplicationContext(), classToLoad);
            i.putExtras(b);
            startActivity(i);
            if (finishCurrentAct) {
                finish();
            }
        } else {
            startMyActivity(classToLoad, finishCurrentAct);
        }
    }

    public boolean isEmpty(String str) {
        boolean temp = true;

        if (TextUtils.isEmpty(str.trim())) {
            temp = true;
        } else {
            temp = false;
        }
        return temp;
    }

    public String replace(String str, String old, String newChar) {
        if (!isEmpty(old)) {
            try {
                return str.replace(old, URLEncoder.encode(newChar, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "";
            }
        } else
            return "";
    }

    public int getFragmentLayoutId() throws NullPointerException {
        if (fragmentLayoutId == 0) {
            throw new NullPointerException("Layout ID not set");
        } else {
            return fragmentLayoutId;
        }
    }

    public void setFragmentLayoutId(int fragmentLayoutId) {
        this.fragmentLayoutId = fragmentLayoutId;
    }


    public String getDeviceId() {
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }


    public static String getDateFromMilliseconds(long milliSeconds,
                                                 String dateFormat) {
        // Create a DateFormatter object for displaying date in specified
        // format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static String dayStringFormat(long msecs) {
        GregorianCalendar cal = new GregorianCalendar();

        cal.setTime(new Date(msecs));

        int dow = cal.get(Calendar.DAY_OF_WEEK);

        switch (dow) {
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            case Calendar.SUNDAY:
                return "Sunday";
        }

        return "Unknown";
    }

    protected String friendlyTimeDiff(long timeDifferenceMilliseconds,
                                      String value) throws ArithmeticException {
        long diffSeconds = timeDifferenceMilliseconds / 1000;
        long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
        long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
        long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
        long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
        long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
        long diffYears = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 365));

        String week_of_day = dayStringFormat(Long.parseLong(value));

        String value_String = getDateFromMilliseconds(Long.parseLong(value),
                "yyyy-MM-dd HH:mm:ss");
        String[] stringarray = value_String.split(" ");
        String exacttime = stringarray[1];
        if (diffSeconds < 1) {
            return "Posted " + "less than a second ago";
        } else if (diffMinutes < 1) {
            return "Posted " + diffSeconds + " seconds ago";
        } else if (diffMinutes == 1) {
            return "Posted " + diffMinutes + " minute ago";
        } else if (diffHours < 1) {
            return "Posted " + diffMinutes + " minutes ago";
        } else if (diffHours == 1) {
            return "Posted " + diffHours + " hour ago";
        } else if (diffDays < 1) {
            return "Posted " + diffHours + " hours ago";
        } else if (diffDays >= 1 && diffDays < 2) {
            return "Posted on " + "Yesterday at " + exacttime;
        } else if (diffDays >= 2 && diffDays < 7) {
            return "Posted on " + week_of_day + " at " + exacttime;
        } else if (diffDays >= 7) {
            return "Posted on " + value_String;
        } else {
            return "Posted on " + value_String;
        }
    }

    @SuppressWarnings("unused")
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public long DateTime_to_Miliseconds(String datetime) {
        long time_in_mili = 0;

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate1 = sf.parse(datetime);

            time_in_mili = mDate1.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time_in_mili;

    }


    public void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static Bitmap getscaledBitmapWithAspectRation(Bitmap originalImage, int width, int height) {
        Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        float originalWidth = originalImage.getWidth(), originalHeight = originalImage.getHeight();
        Canvas canvas = new Canvas(background);
        float scale = width / originalWidth;
        float xTranslation = 0.0f, yTranslation = (height - originalHeight * scale) / 2.0f;
        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation, yTranslation);
        transformation.preScale(scale, scale);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(originalImage, transformation, paint);

        return background;
    }

//    protected void setPicassoProxy() {
//        final OkHttpClient client = new OkHttpClient.Builder()
//                .authenticator(new Authenticator() {
//                    @Override
//                    public Request authenticate(Route route, Response response) throws IOException {
//                        String credential = okhttp3.Credentials.basic("iqbal.nadeem", "n|Cal036");
//                        return response.request().newBuilder()
//                                .header("Authorization", credential)
//                                .build();
//                    }
//                })
//                .build();
//
//        final Picasso picasso = new Picasso.Builder(this)
////                .downloader(new OkHttp3Downloader(client))
//                .build();
//        Picasso.setSingletonInstance(picasso);
//    }

    protected Bitmap getBitmapFromBase64String(String base64String) {
        try {
            byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    protected boolean passwordCalculation(String password) {
//        // TODO Auto-generated method stub
//        String temp = password;
//
//        boolean isSmall = false, isLarge = false, isCharacter = false, isNumber = false, isLength = false;
//
//        int length = temp.length();
//
//        for (int i = 0; i < temp.length(); i++) {
//            if (Character.isUpperCase(temp.charAt(i)))
//                isLarge = true;
//
//            else if (Character.isLowerCase(temp.charAt(i)))
//                isSmall = true;
//
//            else if (Character.isDigit(temp.charAt(i)))
//                isNumber = true;
//
//            else {
//                isCharacter = true;
//            }
//        }
//
//        if (length >= Constants.MIN_PASSWORD_LIMIT) {
//            isLength = true;
//        }
//
//        log("length" + isCharacter);
//        log("uppercase" + isLarge);
//        log("lowercase" + isSmall);
//        log("digits" + isNumber);
//        log("symbols" + isLength);
//
//
//        return isCharacter && isLarge && isSmall && isNumber && isLength;
//    }


    public static String getErrorBody(retrofit2.Response response) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String finallyError = sb.toString();

        return finallyError;
    }

    public static String stackTraceToString(Throwable e) {
        try {
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement element : e.getStackTrace()) {
                sb.append(element.toString());
                sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e1) {
            e1.printStackTrace();
            return ".";
        }
    }

    public static float randomFloat(float min, float max) {

        Random rand = new Random();

        float result = rand.nextFloat() * (max - min) + min;

        return result;

    }


    public static int randomInt(int max) {
        Random rand = new Random();
//        int result = rand.nextInt() * (max - 1) + 1;

        int result = rand.nextInt(max);
        return result;
    }


    public static Boolean getBoolean(String key) {
        return myPrefSpot.getBoolean(key, false);
    }


    public static String getString(String key) {
        return myPrefSpot.getString(key, "");
    }


    protected String getVersionName() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    public boolean checkCompResult(String message) {
//        if (message.equalsIgnoreCase("f1-f1-f1")) {
        if (message.contains("f1")) {
            return true;
        }
        return false;
    }

    public boolean checkRejForwHoldResult(String message) {
//        if (message.equalsIgnoreCase("f1-f1")) {
        if (message.contains("f1")) {
            return true;
        }
        return false;
    }

    public boolean checkAcceptInpgResult(String message) {
//        if (message.equalsIgnoreCase("f1")) {
        if (message.contains("f1")) {
            return true;
        }
        return false;
    }


}
