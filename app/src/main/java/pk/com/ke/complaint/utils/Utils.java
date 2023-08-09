package pk.com.ke.complaint.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import pk.com.ke.complaint.R;

/**
 * Created by iqbal.nadeem on 2/13/2017.
 */

public class Utils {


    @SuppressWarnings("rawtypes")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void slideInRightAnim(Activity activity, Class targetClass) {
        Intent intent = new Intent(activity, targetClass);
        if (hasLollipop()) {
            /*
             * Created by Zain for lollipop Android 5.0 OS from make the transitions work on 15_1_15
			 */
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else if (hasJellyBean()) {
            Bundle translateBundle = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_left,
                    R.anim.slide_out_left).toBundle();
            activity.startActivity(intent, translateBundle);
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    @SuppressWarnings("rawtypes")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void slideInRightAnim_ForResults(Activity activity, Intent intent, int requestCode) {
        if (hasLollipop()) {
            /*
             * Created by Zain for lollipop Android 5.0 OS from make the transitions work on 15_1_15
			 */
            activity.startActivityForResult(intent, requestCode);
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else if (hasJellyBean()) {
            Bundle translateBundle = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_left,
                    R.anim.slide_out_left).toBundle();
            activity.startActivityForResult(intent, requestCode, translateBundle);
        } else {
            activity.startActivityForResult(intent, requestCode);
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    @SuppressWarnings("rawtypes")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void slideInRightAnim(Activity activity, Intent intent) {
        if (hasLollipop()) {
            /*
             * Created by Zain for lollipop Android 5.0 OS from make the transitions work on 15_1_15
			 */
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else if (hasJellyBean()) {
            Bundle translateBundle = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_left,
                    R.anim.slide_out_left).toBundle();
            activity.startActivity(intent, translateBundle);
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    @SuppressWarnings("rawtypes")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void slideInRightAnim(Activity activity, Class targetClass, Bundle b) {
        Intent intent = new Intent(activity, targetClass);
        if (b != null) {
            intent.putExtras(b);
        }

        if (hasLollipop()) {
            /*
             * Created by Zain for lollipop Android 5.0 OS from make the transitions work on 15_1_15
			 */
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        } else if (hasJellyBean()) {
            Bundle translateBundle = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_left,
                    R.anim.slide_out_left).toBundle();
            activity.startActivity(intent, translateBundle);
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    @SuppressWarnings("rawtypes")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void slideOutRightAnim(Activity activity, Class targetClass) {
        Intent intent = new Intent(activity, targetClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (hasJellyBean()) {
            Bundle translateBundle = ActivityOptions.makeCustomAnimation(activity, R.anim.slide_in_left,
                    R.anim.slide_out_left).toBundle();
            activity.startActivity(intent, translateBundle);
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    public static void slideOutRightAnim(Activity activity) {
//        activity.finish();
        if (hasLollipop()) {
            /*
             * Created by Zain for lollipop Android 5.0 OS from make the transitions work on 15_1_15
			 */
            activity.overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
        } else {
            activity.overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
        }
    }

    public static void slideOutTopAnim(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed
        // behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasLollipop() {
//		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean hasHoneycombMR1() {
        // TODO Auto-generated method stub
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

//    Then Call from the activities this:
//            Utils.slideInRightAnim(ActivityGroups.this, ActivityGroupCreate.class);
//
//    Utils.slideOutRightAnim(this);


}
