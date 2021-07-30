package com.fenixarts.nenektrivia.utils;

import androidx.annotation.NonNull;
import android.util.Log;

import com.fenixarts.nenektrivia.BuildConfig;

/**
 * QuinielaPicante
 * Created by terry0022 on 21/09/17 - 16:35.
 */

@SuppressWarnings("unused")
public class Print {

    private static final String TAG = "Print";

    public static void i(@NonNull String message){
        if (BuildConfig.DEBUG){
            Log.i(TAG, message);
        }
    }

    public static void i(boolean message){
        if (BuildConfig.DEBUG){
            Log.i(TAG, String.valueOf(message));
        }
    }

    public static void i(int message) {
        if (BuildConfig.DEBUG){
            Log.i(TAG, String.valueOf(message));
        }
    }

    public static void i(long message) {
        if (BuildConfig.DEBUG){
            Log.i(TAG, String.valueOf(message));
        }
    }

    public static void i(float message) {
        if (BuildConfig.DEBUG){
            Log.i(TAG, String.valueOf(message));
        }
    }

    public static void i(@NonNull String tag, @NonNull String message){
        if (BuildConfig.DEBUG){
            Log.i(tag, message);
        }
    }

    public static void i(@NonNull String tag, boolean message){
        if (BuildConfig.DEBUG){
            Log.i(tag, String.valueOf(message));
        }
    }

    public static void i(@NonNull String tag, int message){
        if (BuildConfig.DEBUG){
            Log.i(tag, String.valueOf(message));
        }
    }

    public static void i(@NonNull String tag, long message){
        if (BuildConfig.DEBUG){
            Log.i(tag, String.valueOf(message));
        }
    }

    public static void i(@NonNull String tag, float message){
        if (BuildConfig.DEBUG){
            Log.i(tag, String.valueOf(message));
        }
    }

    public static void d(@NonNull String message){
        if (BuildConfig.DEBUG){
            Log.d(TAG, message);
        }
    }

    public static void d(boolean message){
        if (BuildConfig.DEBUG){
            Log.d(TAG, String.valueOf(message));
        }
    }

    public static void d(int message) {
        if (BuildConfig.DEBUG){
            Log.d(TAG, String.valueOf(message));
        }
    }

    public static void d(long message) {
        if (BuildConfig.DEBUG){
            Log.d(TAG, String.valueOf(message));
        }
    }

    public static void d(float message) {
        if (BuildConfig.DEBUG){
            Log.d(TAG, String.valueOf(message));
        }
    }

    public static void d(@NonNull String tag, @NonNull String message){
        if (BuildConfig.DEBUG){
            Log.d(tag, message);
        }
    }

    public static void d(@NonNull String tag, boolean message){
        if (BuildConfig.DEBUG){
            Log.d(tag, String.valueOf(message));
        }
    }

    public static void d(@NonNull String tag, int message){
        if (BuildConfig.DEBUG){
            Log.d(tag, String.valueOf(message));
        }
    }

    public static void d(@NonNull String tag, long message){
        if (BuildConfig.DEBUG){
            Log.d(tag, String.valueOf(message));
        }
    }

    public static void d(@NonNull String tag, float message){
        if (BuildConfig.DEBUG){
            Log.d(tag, String.valueOf(message));
        }
    }

    public static void d(@NonNull String message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.d(TAG, message, throwable);
        }
    }

    public static void d(boolean message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.d(TAG, String.valueOf(message), throwable);
        }
    }

    public static void d(int message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.d(TAG, String.valueOf(message), throwable);
        }
    }

    public static void d(long message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.d(TAG, String.valueOf(message), throwable);
        }
    }

    public static void d(float message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.d(TAG, String.valueOf(message), throwable);
        }
    }

    public static void d(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.d(tag, message, throwable);
        }
    }

    public static void d(@NonNull String tag, boolean message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.d(tag, String.valueOf(message), throwable);
        }
    }

    public static void d(@NonNull String tag, int message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.d(tag, String.valueOf(message), throwable);
        }
    }

    public static void d(@NonNull String tag, long message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.d(tag, String.valueOf(message), throwable);
        }
    }

    public static void d(@NonNull String tag, float message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.d(tag, String.valueOf(message), throwable);
        }
    }

    public static void v(@NonNull String message){
        if (BuildConfig.DEBUG){
            Log.v(TAG, message);
        }
    }

    public static void v(boolean message){
        if (BuildConfig.DEBUG){
            Log.v(TAG, String.valueOf(message));
        }
    }

    public static void v(int message) {
        if (BuildConfig.DEBUG){
            Log.v(TAG, String.valueOf(message));
        }
    }

    public static void v(long message) {
        if (BuildConfig.DEBUG){
            Log.v(TAG, String.valueOf(message));
        }
    }

    public static void v(float message) {
        if (BuildConfig.DEBUG){
            Log.v(TAG, String.valueOf(message));
        }
    }

    public static void v(@NonNull String tag, @NonNull String message){
        if (BuildConfig.DEBUG){
            Log.v(tag, message);
        }
    }

    public static void v(@NonNull String tag, boolean message){
        if (BuildConfig.DEBUG){
            Log.v(tag, String.valueOf(message));
        }
    }

    public static void v(@NonNull String tag, int message){
        if (BuildConfig.DEBUG){
            Log.v(tag, String.valueOf(message));
        }
    }

    public static void v(@NonNull String tag, long message){
        if (BuildConfig.DEBUG){
            Log.v(tag, String.valueOf(message));
        }
    }

    public static void v(@NonNull String tag, float message){
        if (BuildConfig.DEBUG){
            Log.v(tag, String.valueOf(message));
        }
    }

    public static void v(@NonNull String message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.v(TAG, message, throwable);
        }
    }

    public static void v(boolean message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.v(TAG, String.valueOf(message), throwable);
        }
    }

    public static void v(int message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.v(TAG, String.valueOf(message), throwable);
        }
    }

    public static void v(long message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.v(TAG, String.valueOf(message), throwable);
        }
    }

    public static void v(float message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.v(TAG, String.valueOf(message), throwable);
        }
    }

    public static void v(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.v(tag, message, throwable);
        }
    }

    public static void v(@NonNull String tag, boolean message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.v(tag, String.valueOf(message), throwable);
        }
    }

    public static void v(@NonNull String tag, int message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.v(tag, String.valueOf(message), throwable);
        }
    }

    public static void v(@NonNull String tag, long message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.v(tag, String.valueOf(message), throwable);
        }
    }

    public static void v(@NonNull String tag, float message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.v(tag, String.valueOf(message), throwable);
        }
    }

    public static void e(@NonNull String message){
        if (BuildConfig.DEBUG){
            Log.e(TAG, message);
        }
    }

    public static void e(boolean message){
        if (BuildConfig.DEBUG){
            Log.e(TAG, String.valueOf(message));
        }
    }

    public static void e(int message) {
        if (BuildConfig.DEBUG){
            Log.e(TAG, String.valueOf(message));
        }
    }

    public static void e(long message) {
        if (BuildConfig.DEBUG){
            Log.e(TAG, String.valueOf(message));
        }
    }

    public static void e(float message) {
        if (BuildConfig.DEBUG){
            Log.e(TAG, String.valueOf(message));
        }
    }

    public static void e(@NonNull String tag, @NonNull String message){
        if (BuildConfig.DEBUG){
            Log.e(tag, message);
        }
    }

    public static void e(@NonNull String tag, boolean message){
        if (BuildConfig.DEBUG){
            Log.e(tag, String.valueOf(message));
        }
    }

    public static void e(@NonNull String tag, int message){
        if (BuildConfig.DEBUG){
            Log.e(tag, String.valueOf(message));
        }
    }

    public static void e(@NonNull String tag, long message){
        if (BuildConfig.DEBUG){
            Log.e(tag, String.valueOf(message));
        }
    }

    public static void e(@NonNull String tag, float message){
        if (BuildConfig.DEBUG){
            Log.e(tag, String.valueOf(message));
        }
    }

    public static void e(@NonNull String message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.e(TAG, message, throwable);
        }
    }

    public static void e(boolean message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.e(TAG, String.valueOf(message), throwable);
        }
    }

    public static void e(int message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.e(TAG, String.valueOf(message), throwable);
        }
    }

    public static void e(long message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.e(TAG, String.valueOf(message), throwable);
        }
    }

    public static void e(float message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.e(TAG, String.valueOf(message), throwable);
        }
    }

    public static void e(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.e(tag, message, throwable);
        }
    }

    public static void e(@NonNull String tag, boolean message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.e(tag,  String.valueOf(message), throwable);
        }
    }

    public static void e(@NonNull String tag, int message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.e(tag,  String.valueOf(message), throwable);
        }
    }

    public static void e(@NonNull String tag, long message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.e(tag,  String.valueOf(message), throwable);
        }
    }

    public static void e(@NonNull String tag, float message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.e(tag,  String.valueOf(message), throwable);
        }
    }

    public static void w(@NonNull String message){
        if (BuildConfig.DEBUG){
            Log.w(TAG, message);
        }
    }

    public static void w(boolean message){
        if (BuildConfig.DEBUG){
            Log.w(TAG, String.valueOf(message));
        }
    }

    public static void w(int message) {
        if (BuildConfig.DEBUG){
            Log.w(TAG, String.valueOf(message));
        }
    }

    public static void w(long message) {
        if (BuildConfig.DEBUG){
            Log.w(TAG, String.valueOf(message));
        }
    }

    public static void w(float message) {
        if (BuildConfig.DEBUG){
            Log.w(TAG, String.valueOf(message));
        }
    }

    public static void w(@NonNull String tag, @NonNull String message){
        if (BuildConfig.DEBUG){
            Log.w(tag, message);
        }
    }

    public static void w(@NonNull String tag, boolean message){
        if (BuildConfig.DEBUG){
            Log.w(tag, String.valueOf(message));
        }
    }

    public static void w(@NonNull String tag, int message){
        if (BuildConfig.DEBUG){
            Log.w(tag, String.valueOf(message));
        }
    }

    public static void w(@NonNull String tag, long message){
        if (BuildConfig.DEBUG){
            Log.w(tag, String.valueOf(message));
        }
    }

    public static void w(@NonNull String tag, float message){
        if (BuildConfig.DEBUG){
            Log.w(tag, String.valueOf(message));
        }
    }

    public static void w(@NonNull String message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.w(TAG, message, throwable);
        }
    }

    public static void w(boolean message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.w(TAG, String.valueOf(message), throwable);
        }
    }

    public static void w(int message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.w(TAG, String.valueOf(message), throwable);
        }
    }

    public static void w(long message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.w(TAG, String.valueOf(message), throwable);
        }
    }

    public static void w(float message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.w(TAG, String.valueOf(message), throwable);
        }
    }

    public static void w(@NonNull String tag, @NonNull String message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.w(tag, message, throwable);
        }
    }

    public static void w(@NonNull String tag, boolean message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.w(tag,  String.valueOf(message), throwable);
        }
    }

    public static void w(@NonNull String tag, int message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.w(tag,  String.valueOf(message), throwable);
        }
    }

    public static void w(@NonNull String tag, long message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.w(tag,  String.valueOf(message), throwable);
        }
    }

    public static void w(@NonNull String tag, float message, @NonNull Throwable throwable){
        if (BuildConfig.DEBUG){
            Log.w(tag,  String.valueOf(message), throwable);
        }
    }

}
