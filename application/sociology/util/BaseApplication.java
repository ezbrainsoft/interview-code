package com.jlesoft.civilservice.koreanhistoryexam9.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jlesoft.civilservice.koreanhistoryexam9.BuildConfig;
import com.jlesoft.civilservice.koreanhistoryexam9.IntroActivity;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.Map;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

public class BaseApplication extends Application {
    public static boolean DEBUG = false;
    public static OkHttpClient okHttpClient;
    public static int custom_text_size;
//    public static final String AF_DEV_KEY = "<your-appsflyer-dev-key>";
    public static AppStatus mAppStatus;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());

        Fabric.with(this, new Crashlytics());

        this.DEBUG = isDebuggable(this);

//        Typekit.getInstance()
//                .addNormal(Typekit.createFromAsset(this, "NotoSansCJKkr-Regular.ttf"))
//                .addBold(Typekit.createFromAsset(this, "NotoSansCJKkr-Bold.ttf"))
//                .addCustom1(Typekit.createFromAsset(this, "NotoSansCJKkr-Medium.ttf"));

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("koreanhistory9.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor()).build();

        FirebaseApp.initializeApp(this);
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Boolean isChannel = PrefHelper.getBoolean(this, PrefConsts.PUSH_CHANNEL, false);
            if(!isChannel){
                NotificationManager.createChannel(this);
            }
        }*/

        //appsFlyer 전환데이터에 엑세스
       /* AppsFlyerConversionListener conversionDataListener =  new AppsFlyerConversionListener() {

            @Override
            public void onInstallConversionDataLoaded(Map<String, String> map) {

            }

            @Override
            public void onInstallConversionFailure(String s) {

            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {

            }

            @Override
            public void onAttributionFailure(String s) {

            }
        };
        AppsFlyerLib.getInstance().init(BuildConfig.AF_DEV_KEY, conversionDataListener, getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(this);*/


    }

    private boolean isDebuggable(Context context) {
        boolean debuggable = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            /* debuggable variable will remain false */
        }

        return debuggable;
    }

    // Get app is foreground
    public AppStatus getAppStatus() {
        return mAppStatus;
    }

    // check if app is return foreground
    public boolean isReturnedForground() {
        return mAppStatus.ordinal() == AppStatus.RETURNED_TO_FOREGROUND.ordinal();
    }

    public enum AppStatus{
        BACKGROUND, // app is background
        RETURNED_TO_FOREGROUND, // app returned to foreground(or first launch)
        FOREGROUND; // app is foreground
    }

    public class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        private int running = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (++running == 1) {
            // running activity is 1,
            // app must be returned from background just now (or first launch)
                mAppStatus = AppStatus.RETURNED_TO_FOREGROUND;
            } else if (running > 1) {
            // 2 or more running activities,
            // should be foreground already.
                mAppStatus = AppStatus.FOREGROUND;
            }

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (--running == 0) {
            // no active activity
            // app goes to background
                mAppStatus = AppStatus.BACKGROUND;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
