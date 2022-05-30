package com.example.yuval;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Connectivty extends BroadcastReceiver {
    private static boolean connectedLast = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = wifi != null && wifi.isConnectedOrConnecting() || mobile != null && mobile.isConnectedOrConnecting();
        if(isConnected)
        {
            if (!connectedLast) {
                Toast.makeText(context, "connected to the internet.", Toast.LENGTH_LONG).show();
                connectedLast = true;
            }
        }
        else if(connectedLast)
        {
            Toast.makeText(context, "Warning: No internet connection! Accounts and leaderboards will not work!", Toast.LENGTH_LONG).show();
            connectedLast = false;
        }
    }
}