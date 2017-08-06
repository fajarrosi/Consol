package com.example.fajarir.consol;

import android.app.Application;

import com.qiscus.sdk.Qiscus;

/**
 * Created by fajarir on 7/29/2017.
 */

public class SampleApps extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Qiscus.init(this, "dragongo");
    }
}
