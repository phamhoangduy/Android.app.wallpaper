package com.development.duyph.androidappwallpaper.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkUtil {

    /**
     * Check if the current user's device has a internet connection
     *
     * @param context the context
     * @return true means the internet connection is available, false otherwise
     */
    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static boolean isOnWifi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI
                && info.isConnected();
    }
}
