package com.example.prithvisathiyamoorth.mazemeup;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class AlarmService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ArrayList<Boolean> days = (ArrayList<Boolean>) intent.getSerializableExtra("days");
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        if (days.size() == 1 || days.get(dayOfWeek - 1)) {
            Intent i = new Intent(context, AlarmDialog.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
