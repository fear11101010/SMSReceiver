package com.example.arafathossain.smsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("boot", "complete");
        Intent app = new Intent(context, MainActivity.class);
        context.startActivity(app);
    }

}
