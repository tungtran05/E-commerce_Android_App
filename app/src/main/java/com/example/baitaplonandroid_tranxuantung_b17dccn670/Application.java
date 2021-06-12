package com.example.baitaplonandroid_tranxuantung_b17dccn670;

import com.androidnetworking.AndroidNetworking;

public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
