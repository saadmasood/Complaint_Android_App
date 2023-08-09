package pk.com.ke.complaint;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.bugfender.sdk.Bugfender;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;

import io.fabric.sdk.android.Fabric;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created by Nadeem Iqbal on 10/18/17.
 */

public class MyApplication extends /*MaaS360Secure*/Application /*implements IMaaS360SDKListener, IMaaS360SDKPolicyAutoEnforceInfo*/ {

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics(), new Answers());

        Bugfender.init(this, "LvyOCgENNLOT2LdGmNbo5ybeLgQoAZ1d", true);
        Bugfender.enableLogcatLogging();
        Bugfender.enableUIEventLogging(this);
        Bugfender.enableCrashReporting();

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/" + Config.FONT_NAME)
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/" + Config.FONT_NAME)
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(base));
        MultiDex.install(this);
    }

}
