package com.example.prithvisathiyamoorth.mazemeup;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<Integer> hrDataA = new ArrayList<Integer>();
    public static ArrayList<Integer> minDataA = new ArrayList<Integer>();
    public static ArrayList<ArrayList<Boolean>> repeatedA = new ArrayList<ArrayList<Boolean>>();
    public static ArrayList<Boolean> activeA = new ArrayList<Boolean>();


    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("All Alarms");

        AlarmDBHelper helper = new AlarmDBHelper(this);
        hrDataA.clear();minDataA.clear();repeatedA.clear();activeA.clear();
        helper.getAllContacts();
        AlarmListAdapter Adapter = new AlarmListAdapter(this, hrDataA, minDataA, repeatedA, activeA);
        lv = (ListView) findViewById(R.id.alarmList);
        lv.setAdapter(Adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditAlarm.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //get all the alarms
        AlarmDBHelper helper = new AlarmDBHelper(this);
        hrDataA.clear();minDataA.clear();repeatedA.clear();activeA.clear();
        helper.getAllContacts();
        //show it in listview
        AlarmListAdapter Adapter = new AlarmListAdapter(this, hrDataA, minDataA, repeatedA, activeA);
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
            Toast.makeText(this, hrDataA.size()+"", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.refresh) {
            //Toast.makeText(this, piList.size()+"", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.newAlarmMenu) {
            return newAlarmActivity();
        }

        return super.onOptionsItemSelected(item);
    }

//    private boolean refreshList() {
//        AlarmListAdapter Adapter = new AlarmListAdapter(hrDataA, minDataA, repeatedA, activeA);
//        ListView lv = (ListView) findViewById(R.id.alarmList);
//        lv.setAdapter(Adapter);
//        return true;
//    }

    private boolean newAlarmActivity() {
        Intent intent = new Intent(this, NewAlarm.class);
        startActivity(intent);
        return true;
    }
}
