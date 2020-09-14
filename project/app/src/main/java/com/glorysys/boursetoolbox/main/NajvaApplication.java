package com.glorysys.boursetoolbox.main;

import android.app.Application;

import com.najva.sdk.NajvaClient;

public class NajvaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(NajvaClient.getInstance(this,null));

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        NajvaClient.getInstance().onAppTerminated();
    }
}
