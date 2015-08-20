package com.example.prithvisathiyamoorth.mazemeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class AlarmService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("hello", "hlkajd");
        Toast.makeText(context, "Alarm works", Toast.LENGTH_SHORT).show();
    }
}
