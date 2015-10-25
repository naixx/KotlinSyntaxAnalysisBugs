package me.packbag.android;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.facebook.FacebookSdk;
import com.github.naixx.L;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

public class App extends Application {

    private volatile AppComponent appComponent;

    @Override
    public void onCreate() {
        long start = System.currentTimeMillis();
        super.onCreate();
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll()
                    .penaltyLog()
                    .penaltyFlashScreen()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
            L.plant(new L.DebugTree());
            FlowLog.setMinimumLoggingLevel(FlowLog.Level.D);
        }
        FlowManager.init(this);
        FacebookSdk.sdkInitialize(this);
        long end = System.currentTimeMillis() - start;
        L.e("startup = " + end);
    }

    @NonNull
    public static App get(@NonNull Context context) {
        return ((App) context.getApplicationContext());
    }

    @NonNull
    public AppComponent component() {
        if (appComponent == null) {
            synchronized (App.class) {
                if (appComponent == null) {
                    appComponent = createAppComponent();
                }
            }
        }

        //noinspection ConstantConditions
        return appComponent;
    }

    @NonNull
    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
