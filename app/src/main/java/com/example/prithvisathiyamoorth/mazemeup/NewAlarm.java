package com.example.prithvisathiyamoorth.mazemeup;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.security.KeyChain;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class NewAlarm extends ActionBarActivity {
    TimePicker tp;
    EditText et;
    CheckBox sun;
    CheckBox mon;
    CheckBox tue;
    CheckBox wed;
    CheckBox thu;
    CheckBox fri;
    CheckBox sat;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_new);

        tp = (TimePicker) findViewById(R.id.time);
        et = (EditText) findViewById(R.id.alarmName);
        sun = (CheckBox) findViewById(R.id.sunRepeat);
        mon = (CheckBox) findViewById(R.id.monRepeat);
        tue = (CheckBox) findViewById(R.id.tueRepeat);
        wed = (CheckBox) findViewById(R.id.wedRepeat);
        thu = (CheckBox) findViewById(R.id.thuRepeat);
        fri = (CheckBox) findViewById(R.id.friRepeat);
        sat = (CheckBox) findViewById(R.id.satRepeat);
        alarmManager =  (AlarmManager)getSystemService(ALARM_SERVICE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_alarm, menu);
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
        Toast.makeText(this, cal.getTime() + "", Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void addAlarm(View v) {
        //GET TIME FORM TIME PICKER AND SET TO CAL
        int hour = tp.getCurrentHour();
        int min = tp.getCurrentMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        if(calendar.compareTo(Calendar.getInstance()) <= 0)
            calendar.add(Calendar.DATE, 1);

        //GET AND SET NAME
        String name = et.getText().toString();

        //GET AND SET THE REPEATING DAYS
        ArrayList<Boolean> days = new ArrayList<Boolean>();
        days.add(sun.isChecked());days.add(mon.isChecked());days.add(tue.isChecked());
        days.add(wed.isChecked());days.add(thu.isChecked());days.add(fri.isChecked());days.add(sat.isChecked());

        //ADD TO DATABASE
        AlarmDBHelper helper = new AlarmDBHelper(this);
        int lastpi = helper.getNextPI();
        helper.addAlarm(hour, min, true, lastpi, name, days);

        //SET ALARMM
        Intent myIntent =  new Intent(NewAlarm.this, AlarmService.class);
        //if repeating
        if(days.contains(true)) {
            myIntent.putExtra("days", days);
            pendingIntent = PendingIntent.getBroadcast(NewAlarm.this, lastpi, myIntent, 0);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        //if not repeating
        else {
            ArrayList<Boolean> alt = new ArrayList<Boolean>(); alt.add(true);
            myIntent.putExtra("days", alt);
            pendingIntent = PendingIntent.getBroadcast(NewAlarm.this, lastpi, myIntent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        finish();
    }

    public void cancelAlarm(View v) {
        finish();
    }
}
