package com.example.prithvisathiyamoorth.mazemeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;


public class AlarmService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("hello", "hlkajd");
        Toast.makeText(context, "Alarm works at " + Calendar.getInstance().getTime(), Toast.LENGTH_SHORT).show();
    }
}
