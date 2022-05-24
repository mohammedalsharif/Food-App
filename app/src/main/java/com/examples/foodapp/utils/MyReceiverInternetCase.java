package com.examples.foodapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyReceiverInternetCase extends BroadcastReceiver {
    OnInternetCaseChange internetCaseChange;

    public MyReceiverInternetCase(OnInternetCaseChange internetCaseChange) {
        this.internetCaseChange = internetCaseChange;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isConnection(context)){
            internetCaseChange.InternetCaseChange(true);
        }else {
            internetCaseChange.InternetCaseChange(false);
        }

    }

    private boolean isConnection(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

    }}