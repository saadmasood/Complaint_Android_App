package pk.com.ke.complaint.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import pk.com.ke.complaint.Config;
import pk.com.ke.complaint.model.Material;
import pk.com.ke.complaint.model.MaterialRequestBody.MaterialsSet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

@SuppressLint("SimpleDateFormat")
public class StringUtils {

    public static HashMap<String, List<Material>> materialList = new HashMap<>();
    public static final String FORMAT_DATE = "EE dd-MM-yyyy hh:mm:ss a";


    public final static String PREF_LAST_LOGIN_CREDENTIAL = "last_email";

    // LOGIN PREFS NAME
    public static final String PREF_USER_USERTYPE = "user_type";
    public static final String PREF_USER_SESSION = "sjasiwqqq";

    public static final String PREF_USER_PWD = "nclaksjdhwefio";

    public static final String PREF_USER_EMAIL = "jlashdfjhoqwe";
    public static final String PREF_USER_USER_ID = "q0fuwe8932A";
    public static final String PREF_USER_USER_NAME_AD = "oiqweyroqiweur";
    public static final String PREF_USER_FULLNAME = "uaowdvy78223";
    public static final String PREF_USER_IMAGEURL = "nc9382gyvh23";
    public static final String PREF_USER_MOBILE = "oia0sjcSs4eu129r8";
    public static final String PREF_USER_IP_PHONE = "Aa8cs7813hf12";
    // ////
    public static final String PREF_IS_LOGIN = "uASC8y31qfh12f";


    public static final String BUZZER_NUMBER_TO_CALL = "buzzer_number";


    public static final String FONT_EditTexts = "arial.ttf";

    public static final String PREFS_PC_NAME = "Pref_PC";
    public static final String FONT_TextViews = "times_bold.ttf";
    public static final String FONT_Drawer = "arial_bold.ttf";
    public static final String PREF_SHOW_NOTIFICATIONS = "pref_user_show_notifications";
    public static final String PREF_USER_CITY = "pref_user_city";
    public static final String PREF_USER_CITY_LATITUDE = "pref_user_city_latitude";
    public static final String PREF_USER_CITY_LONGITUDE = "pref_user_city_longitude";
    public static final String PREF_USER_MODE = "pref_user_mode";
    public static final String PREF_USER_FBID = "pref_user_fbid";


    public static final String PREF_LOCATION_SETTINGS_LOCATION_RADIUS = "pref_location_radius";
    public static final String PREF_LOCATION_SETTINGS_LOCATION_UNIT_VALUE = "location_unit_value";

    public static final String PREF_STICKER_LOVE_5 = "love";
    public static final String PREF_STICKER_FLIRT_3 = "flirt";
    public static final String PREF_STICKER_FUN_4 = "fun";
    public static final String PREF_STICKER_COOL_1 = "cool";
    public static final String PREF_STICKER_EMOTIONS_2 = "emotions";
    public static final String PREF_IS_AD_ALLOWED = "is_ad_allowed";
    public static final String CITY_NAME = "city";
    public static final String PREF_USER_USER_PWD = "pwd";

    public static final String PREF_USER_RELEASE_CODE = "release_code";
    public static final String PREF_USER_RELEASE_GROUP = "release_group";
    public static final String PREF_USER_STATUS = "status";
    public static final String PREF_USER_PLANT = "plant";

    public static final String PREF_COUNT_LEAVE = "askodj18123";
    public static final String PREF_COUNT_H2H = "as127dj18123";
    public static final String PREF_COUNT_SR = "AS12dj9dj18123";


    public static final String PREFS_NAME = "Pref_SMSSender";

    public static final String PREF_USER_STATIC_AD_ID = "iasgydqw8129";
    public static final String PREF_USER_STATIC_AD_PWD = "iasgydqw8229";

    private static boolean isConnected = false;

    public static final String PREF_UNREAD_NOTIFS_COUNT = "unread_notif_count";
    public static final String PREF_USER_DISTANCE = "user_distance";
    public static final String PREF_USER_METRIC = "user_distance_metric";


    public static long VIBRATE_TIME = 300;

    public static List<pk.com.ke.complaint.model.MaterialListResponse.Result> total_material_list= new ArrayList<pk.com.ke.complaint.model.MaterialListResponse.Result>();
    List<String> uniqueMaterialCat = new ArrayList<>();
    List<String> uniqueMaterialSubCat = new ArrayList<>();


    public final static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static String EncodeString(String ToEncode) {

        try {
            ToEncode = URLEncoder.encode(ToEncode, "utf-8");

        } catch (UnsupportedEncodingException e) {
            if (Config.IS_LOG_ALLOWED) Log.e("UnsupporodingException:",
                    e.getMessage());
        }

        return ToEncode;
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            if (Config.IS_LOG_ALLOWED) Log.e("SpotMe Error", e.getMessage());
        }
        return "";
    }

    public static String des(String textToBeEncrypted, String deskey) {
        try {

            // declare Cipher
            Cipher desCipher;

            // Create the cipher
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Initialize the cipher for encryption
            // desCipher.init(Cipher.ENCRYPT_MODE, deskey);

            // sensitive information
            byte[] text = "No body can see me".getBytes();

            if (Config.IS_LOG_ALLOWED) Log.e("StringUtils", "Text [Byte Format] : " + text);
            if (Config.IS_LOG_ALLOWED) Log.e("StringUtils", "Text : " + new String(text));

            // Encrypt the text
            byte[] textEncrypted = desCipher.doFinal(text);

            if (Config.IS_LOG_ALLOWED) Log.e("textEncrypted", "" + textEncrypted);

            return "" + textEncrypted;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return "";

    }

    // public static String decrypt(String str) {
    //
    // try {
    //
    // // decode with base64 to get bytes
    // byte[] dec = BASE64DecoderStream.decode(str.getBytes());
    // byte[] utf8 = dcipher.doFinal(dec);
    //
    // // create new string based on the specified charset
    // return new String(utf8, "UTF8");
    //
    // }
    //
    // catch (Exception e) {
    // e.printStackTrace();
    // }
    //
    // return null;
    //
    // }

    public static String getFormatDateFacebook(String Date, String formatStyle) {
        String formatteddate = "";

        try {
            java.util.Date date = new SimpleDateFormat("dd/mm/yyyy").parse(Date);
            if (Config.IS_LOG_ALLOWED) Log.e("DateFB", "" + date);
            DateFormat newFormat = new SimpleDateFormat(formatStyle);
            formatteddate = newFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }

        return formatteddate;
    }

    public static String getFormatedAmount(String amount) {
        return getFormatedAmount(Double.parseDouble(amount));
    }

    public static String getFormatedNumber(String quantity) {
        double d = Double.parseDouble(quantity);

        DecimalFormat dFormat = new DecimalFormat("###,###,###,###,###,###,###.##");
        String str = dFormat.format(d);

        return str;
    }

    public static String getFormatedAmount(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "PK", "PKR"));

        return formatter.format(amount);
    }

    public static String getFormatDate(String Date, String currentFormat, String formatStyle) {
        String formatteddate = "";

        try {
            java.util.Date date = new SimpleDateFormat(currentFormat).parse(Date);
            DateFormat newFormat = new SimpleDateFormat(formatStyle);
            formatteddate = newFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }

        return formatteddate;
    }

    public static String getFormatDate(String Date, String formatStyle) {
        String formatteddate = "";

        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(Date);
            DateFormat newFormat = new SimpleDateFormat(formatStyle);
            formatteddate = newFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }

        return formatteddate;

    }

    public static String getFormatDate1(String Date, String formatStyle) {
        String formatteddate = "";

        try {
            java.util.Date date = new SimpleDateFormat("MMM dd, yyyy").parse(Date);
            DateFormat newFormat = new SimpleDateFormat(formatStyle);
            formatteddate = newFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }

        return formatteddate;

    }

    public static String getFormatDate2(String Date, String formatStyle) {
        String formatteddate = "";

        try {
            java.util.Date date = new SimpleDateFormat("yyyy-M-dd").parse(Date);
            DateFormat newFormat = new SimpleDateFormat(formatStyle);
            formatteddate = newFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }

        return formatteddate;

    }

    public static String getFormatTime(String Date, String formatStyle) {
        String formatteddate = "";

        try {
            java.util.Date date = new SimpleDateFormat("HH:mm:ss").parse(Date);
            DateFormat newFormat = new SimpleDateFormat(formatStyle);
            formatteddate = newFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }

        return formatteddate;

    }

    public static String getFormatDate3(String Date, String formatStyle) {
        String formatteddate = "";

        try {
            java.util.Date date = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss").parse(Date);
            DateFormat newFormat = new SimpleDateFormat(formatStyle);
            formatteddate = newFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }

        return formatteddate;

    }

    @SuppressWarnings("unused")
    public static String getAge(String dateOfBirth) {
        String age = "";

        // /For Age Calculation:
        Date nowDate = new Date();

        int yearDOB = Integer.parseInt(dateOfBirth.substring(0, 4));
        if (Config.IS_LOG_ALLOWED) Log.e("birth year", String.valueOf(yearDOB));

        int NowYear = Calendar.getInstance().get(Calendar.YEAR);

        int new_age = 0;

        new_age = NowYear - yearDOB;
        if (Config.IS_LOG_ALLOWED) Log.e("this year", String.valueOf(NowYear));

        age = String.valueOf(new_age);
        if (Config.IS_LOG_ALLOWED) Log.e("new Age", age);

        return age;

    }

    public static String getTime(long diff) {

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String timeDiference = diffHours + ":" + diffMinutes + ":"
                + diffSeconds + ":";

        return timeDiference;

    }

    public static Typeface setTypeFace(Context ctx, TextView tv, String fontName) {
        Typeface custom_font = Typeface.createFromAsset(ctx.getAssets(),
                fontName);
        return custom_font;
    }

    public static Typeface setTypeFace_Auto(Context ctx, TextView tv) {
        String fontName = StringUtils.FONT_EditTexts;

        Typeface custom_font = Typeface.createFromAsset(ctx.getAssets(),
                fontName);
        return custom_font;
    }

    public static Typeface setTypeFace_Auto(Context ctx, TextView tv,
                                            boolean isHeading) {
        if (isHeading) {
            String fontName = "";

            fontName = StringUtils.FONT_EditTexts;

            Typeface custom_font = Typeface.createFromAsset(ctx.getAssets(),
                    fontName);
            return custom_font;
        } else
            return setTypeFace_Auto(ctx, tv);
    }


    public static boolean isNetworkConnected(Activity act) {
        ConnectivityManager cm = (ConnectivityManager) act
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

//    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
//        int targetWidth = 50;
//        int targetHeight = 50;
//        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
//                Bitmap.Config.ARGB_8888);
//
//        Canvas canvas = new Canvas(targetBitmap);
//        Path path = new Path();
//        path.addCircle(((float) targetWidth - 1) / 2,
//                ((float) targetHeight - 1) / 2,
//                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
//                Path.Direction.CCW);
//
//        canvas.clipPath(path);
//        Bitmap sourceBitmap = scaleBitmapImage;
//        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
//                sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
//                targetHeight), null);
//        return targetBitmap;
//    }

//    "MM/dd/yyyy hh:mm:ss aa"

    public static Date ConvertToDate(String dateString) {
        return ConvertToDate(dateString, "MM/dd/yyyy hh:mm:ss aa");
    }

    public static Date ConvertToDate(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    public static String getFormattedRelativeTime(Date date) {
        String createdOn = DateUtils.getRelativeTimeSpanString(
                date.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        return createdOn;
    }

    public static String getFormattedRelativeTime(Date date, Date otherTime) {
        String createdOn = DateUtils.getRelativeTimeSpanString(
                date.getTime(), otherTime.getTime(), DateUtils.SECOND_IN_MILLIS).toString();
        return createdOn;
    }

    public static Integer getDrawableFromString(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static void hideSoftKeyboard(Context ctx, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static Bitmap getBitmapFromBase64String(String base64String) {
        byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    public static String calculateTime(long minutes1) {

        long hours2 = minutes1 / 60; //since both are long, you get an long
        long minutes2 = minutes1 % 60;

        String time = "<b>"+hours2 + "</b>h, <b>" + minutes2 + "</b>m";


        return time;
    }
}

