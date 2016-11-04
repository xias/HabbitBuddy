package com.example.android.effectivenavigation;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Xu on 2016-11-04.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
