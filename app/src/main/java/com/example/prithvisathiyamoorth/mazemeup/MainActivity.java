package com.example.prithvisathiyamoorth.mazemeup;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    private String[] listData = {"9:00", "10:00","1:30","5:30","6:15"};
    private String[] repeated = {"S  M   T   W   TH   F  S",
            "S  M   T   W   TH   F  S",
            "S  M   T   W   TH   F  S",
            "S  M   T   W   TH   F  S",
            "S  M   T   W   TH   F  S"};
    private Boolean[] active = {false, false, true, true, true};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        AlarmListAdapter Adapter = new AlarmListAdapter(listData, repeated, active);
        ListView lv = (ListView) findViewById(R.id.alarmList);
        lv.setAdapter(Adapter);
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
        else if (id == R.id.newAlarmMenu) {
            return newAlarmFrag();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean newAlarmFrag() {
        Intent intent = new Intent(this, NewAlarm.class);
        startActivity(intent);
        return true;
    }

    public class AlarmListAdapter extends BaseAdapter {
        private String[] time;
        private String[] repeatedDays;
        private Boolean[] active;
        Context context;
        public AlarmListAdapter(String[] t, String[] rD, Boolean[] a) {
            time = t;
            repeatedDays = rD;
            active = a;
        }

        @Override
        public int getCount() {
            return time.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row;
            TextView timeView, days;
            CheckBox cb;
            LayoutInflater inflater = getLayoutInflater();

            row = inflater.inflate(R.layout.alarm_list_item, parent, false);
            timeView = (TextView) row.findViewById(R.id.time);
            days = (TextView) row.findViewById(R.id.repeat);
            cb = (CheckBox) row.findViewById(R.id.alarmActivated);

            timeView.setText(time[position]);
            days.setText(repeatedDays[position]);
            cb.setChecked(active[position]);
            return row;
        }
    }


}
