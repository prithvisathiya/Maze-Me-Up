package com.example.prithvisathiyamoorth.mazemeup;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private int[] hrData = {9, 10, 12, 4, 6, 7, 8};
    private int[] minData = {30, 15, 45, 23, 00, 00, 15};
    private String[] repeated = {"S  M   T   W   TH   F  S",
            "S  M   T   W   TH   F  S",
            "S  M   T   W   TH   F  S",
            "S  M   T   W   TH   F  S",
            "S  M   T   W   TH   F  S",
            "S  M   T   W   TH   F  S",
            "S  M   T   W   TH   F  S"};
    private Boolean[] active = {false, true, false, false, true, true, true};

    public static ArrayList<Integer> hrDataA = new ArrayList<Integer>();
    public static ArrayList<Integer> minDataA = new ArrayList<Integer>();
    public static ArrayList<String> repeatedA = new ArrayList<String>();
    public static ArrayList<Boolean> activeA = new ArrayList<Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        AlarmListAdapter Adapter = new AlarmListAdapter(hrDataA, minDataA, repeatedA, activeA);
        ListView lv = (ListView) findViewById(R.id.alarmList);
        lv.setAdapter(Adapter);


        getSupportActionBar().setTitle("All Alarms");
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    public void onResume() {
        super.onResume();
        AlarmListAdapter Adapter = new AlarmListAdapter(hrDataA, minDataA, repeatedA, activeA);
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
//        else if (id == R.id.refresh) {
//            return refreshList();
//        }
        else if (id == R.id.newAlarmMenu) {
            return newAlarm();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean refreshList() {
        AlarmListAdapter Adapter = new AlarmListAdapter(hrDataA, minDataA, repeatedA, activeA);
        ListView lv = (ListView) findViewById(R.id.alarmList);
        lv.setAdapter(Adapter);
        return true;
    }

    private boolean newAlarm() {
        Intent intent = new Intent(this, NewAlarm.class);
        startActivity(intent);
        return true;
    }

    public class AlarmListAdapter extends BaseAdapter {
        private int[] hr;
        private int[] min;
        private String[] repeatedDays;
        private Boolean[] active;

        private ArrayList<Integer> hrA;
        private ArrayList<Integer> minA;
        private ArrayList<String> repeatedDaysA;
        private ArrayList<Boolean> activeA;
        Context context;
        public AlarmListAdapter(int[] h, int[] m, String[] rD, Boolean[] a) {
            hr = h;
            min = m;
            repeatedDays = rD;
            active = a;
        }

        public AlarmListAdapter(ArrayList<Integer> h, ArrayList<Integer> m, ArrayList<String> rD, ArrayList<Boolean> a) {
            hrA = h;
            minA = m;
            repeatedDaysA = rD;
            activeA = a;
        }

        @Override
        public int getCount() {
            return hrA.size();
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

            if (minA.get(position) != 0) {
                if (hrA.get(position) > 12)
                    timeView.setText((hrA.get(position) - 12) + ":" + minA.get(position) + " PM");
                else
                    timeView.setText(hrA.get(position) + ":" + minA.get(position) + " AM");
            }
            else
                timeView.setText(hrA.get(position) + ":00");
            days.setText(repeatedDaysA.get(position));
            cb.setChecked(activeA.get(position));
            return row;
        }
    }


}
