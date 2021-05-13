package com.fenixarts.nenektrivia.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * NenekTrivia
 * Created by terry0022 on 03/01/18 - 10:08.
 */

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static void setContext(Context mContext) {
        App.mContext = mContext;
    }

    public static Context getContext(){
        return mContext;
    }

}
