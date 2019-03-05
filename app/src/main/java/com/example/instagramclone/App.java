package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GSBzYIhPG8XDd0AtijVQnkG69xbGiFAnoobb2sD4")
                // if defined
                .clientKey("RZyBGuSOMGMMMN1QfUcch6buOZ0Ad9WU7YPUP5ID")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
