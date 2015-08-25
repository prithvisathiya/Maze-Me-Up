package com.example.prithvisathiyamoorth.mazemeup;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ListActivity {

    private String[] listData = {"Test", "Test","Test","Test","Test"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        ListView lv = (ListView) findViewById(R.id.alarmList);
        lv.setAdapter(listAdapter);
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

}
