package com.example.arafathossain.smsreceiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SMSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] sms = new SmsMessage[pdus.length];
        for (int i = 0; i < sms.length; i++) {
            sms[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String from = sms[i].getOriginatingAddress();
            String body = sms[i].getDisplayMessageBody();
            String time = new SimpleDateFormat("dd/MM/yyyy  hh:mm a").format(new Date(sms[i].getTimestampMillis()));
            if (isNetworkAvailable(context)) {
                SMSUpload upload = new SMSUpload(context,null);
                upload.makeRequest(new SMS(from, body, time));
            } else {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_FROM, from);
                values.put(DatabaseHelper.COLUMN_BODY, body);
                values.put(DatabaseHelper.COLUMN_TIME, time);
                new DatabaseHelper(context).insertSMS(values);
            }
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected() || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
    }

}
