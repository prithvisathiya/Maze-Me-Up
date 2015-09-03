package com.example.prithvisathiyamoorth.mazemeup;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class EditAlarm extends ActionBarActivity {

    private int position;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    public static int hour;
    public static int min;
    public static String name;
    public static String sunday;
    public static String monday;
    public static String tuesday;
    public static String wednesday;
    public static String thursday;
    public static String friday;
    public static String saturday;
    public static int piId;
    public static boolean active;

    TimePicker tp;
    EditText et;
    CheckBox sun;
    CheckBox mon;
    CheckBox tue;
    CheckBox wed;
    CheckBox thu;
    CheckBox fri;
    CheckBox sat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);

        alarmManager =  (AlarmManager)getSystemService(ALARM_SERVICE);
        position = getIntent().getIntExtra("position", -1);

        tp = (TimePicker) findViewById(R.id.editAlarm_time);
        et = (EditText) findViewById(R.id.editAlarm_alarmName);
        sun = (CheckBox) findViewById(R.id.editAlarm_sunRepeat);
        mon = (CheckBox) findViewById(R.id.editAlarm_monRepeat);
        tue = (CheckBox) findViewById(R.id.editAlarm_tueRepeat);
        wed = (CheckBox) findViewById(R.id.editAlarm_wedRepeat);
        thu = (CheckBox) findViewById(R.id.editAlarm_thuRepeat);
        fri = (CheckBox) findViewById(R.id.editAlarm_friRepeat);
        sat = (CheckBox) findViewById(R.id.editAlarm_satRepeat);


        AlarmDBHelper helper = new AlarmDBHelper(this);
        helper.getAlarmInfo(position);
        Toast.makeText(this, piId+"", Toast.LENGTH_SHORT).show();
        populateAlarmLayout();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_alarm, menu);
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

    private void populateAlarmLayout() {
        tp.setCurrentHour(hour);tp.setCurrentMinute(min);
        et.setText(name);
        sun.setChecked(Boolean.parseBoolean(sunday));
        mon.setChecked(Boolean.parseBoolean(monday));
        tue.setChecked(Boolean.parseBoolean(tuesday));
        wed.setChecked(Boolean.parseBoolean(wednesday));
        thu.setChecked(Boolean.parseBoolean(thursday));
        fri.setChecked(Boolean.parseBoolean(friday));
        sat.setChecked(Boolean.parseBoolean(saturday));
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void editAlarm(View v) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
        calendar.set(Calendar.MINUTE, tp.getCurrentMinute());
        calendar.set(Calendar.SECOND, 0);
        if(calendar.compareTo(Calendar.getInstance()) <= 0)
            calendar.add(Calendar.DATE, 1);

        String name = et.getText().toString();

        ArrayList<Boolean> days = new ArrayList<>();
        days.add(sun.isChecked());days.add(mon.isChecked());days.add(tue.isChecked());
        days.add(wed.isChecked());days.add(thu.isChecked());days.add(fri.isChecked());days.add(sat.isChecked());

        AlarmDBHelper helper = new AlarmDBHelper(this);
        helper.updateAlarm(tp.getCurrentHour(), tp.getCurrentMinute(), active, piId, name, days);

        if (active) {
            Intent myIntent = new Intent(EditAlarm.this, AlarmService.class);
            //if repeating
            if(days.contains(true)){
                myIntent.putExtra("days", days);
                pendingIntent = PendingIntent.getBroadcast(EditAlarm.this, piId, myIntent, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
            }
            //if not repeating
            else {
                ArrayList<Boolean> alt = new ArrayList<Boolean>(); alt.add(true);
                myIntent.putExtra("days", alt);
                pendingIntent = PendingIntent.getBroadcast(EditAlarm.this, piId, myIntent, 0);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
        Toast.makeText(this, "Alarm Changed", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void deleteAlarm(View v) {
        //DELETE FROM DATABASE
        AlarmDBHelper helper = new AlarmDBHelper(this);
        helper.deleteAlarm(piId);

        //DELETE ACTUAL ALARM
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, piId, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}
