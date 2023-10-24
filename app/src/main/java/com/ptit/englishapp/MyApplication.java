package com.ptit.englishapp;

import android.app.Application;
import android.content.Intent;

import com.ptit.englishapp.notify.MyService;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Khởi động dịch vụ
        startService(new Intent(this, MyService.class));
    }
}