package com.example.prithvisathiyamoorth.mazemeup;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    private AlarmManager alarmManager;// =  (AlarmManager)getSystemService(ALARM_SERVICE);
    private PendingIntent pendingIntent;
    private ArrayList<PendingIntent> piList = new ArrayList<PendingIntent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager =  (AlarmManager)getSystemService(ALARM_SERVICE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getTime(View v) {
        Calendar cal  = Calendar.getInstance();
        Toast.makeText(this, cal.getTime()+"", Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void addAlarm(View v) {
        TimePicker tp = (TimePicker) findViewById(R.id.time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
        calendar.set(Calendar.MINUTE, tp.getCurrentMinute());
        calendar.set(Calendar.SECOND, 0);

        //alarmManager =  (AlarmManager)getSystemService(ALARM_SERVICE);

        Intent myIntent =  new Intent(MainActivity.this, AlarmService.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, piList.size(), myIntent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        piList.add(pendingIntent);
        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 15*1000, pendingIntent);
        Toast.makeText(this, calendar.getTime()+"", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(View v) {
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show();
        }
    }
}
