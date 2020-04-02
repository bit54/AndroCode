package com.example.samacharapp;

import android.app.Application;

import com.android.volley.manager.RequestManager;

public class SamacharApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.getInstance().init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
