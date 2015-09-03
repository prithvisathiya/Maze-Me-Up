package com.example.prithvisathiyamoorth.mazemeup;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by prithvisathiyamoorth on 9/1/15.
 */
public class AlarmListAdapter extends BaseAdapter {
    private ArrayList<Integer> hrA;
    private ArrayList<Integer> minA;
    private ArrayList<ArrayList<Boolean>> repeatedDaysA;
    private ArrayList<Boolean> activeA;
    private Context mContext;

    public AlarmListAdapter(Context context, ArrayList<Integer> h, ArrayList<Integer> m, ArrayList<ArrayList<Boolean>> rD, ArrayList<Boolean> a) {
        hrA = h;
        minA = m;
        repeatedDaysA = rD;
        activeA = a;
        mContext = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView timeView, sun, mon, tue, wed, thu, fri, sat;
        final CheckBox cb;

        if(convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.alarm_list_item, parent, false);
        }
        timeView = (TextView) convertView.findViewById(R.id.time);
        sun = (TextView) convertView.findViewById(R.id.mainActivity_sun);mon = (TextView) convertView.findViewById(R.id.mainActivity_mon);
        tue = (TextView) convertView.findViewById(R.id.mainActivity_tue);wed = (TextView) convertView.findViewById(R.id.mainActivity_wed);
        thu = (TextView) convertView.findViewById(R.id.mainActivity_thu);fri = (TextView) convertView.findViewById(R.id.mainActivity_fri);
        sat = (TextView) convertView.findViewById(R.id.mainActivity_sat);
        cb = (CheckBox) convertView.findViewById(R.id.alarmActivated);

        //SET TIME IN TIMEVIEW
        if (minA.get(position) == 0) {
            timeView.setText(hrA.get(position) + ":00");

        } else if (minA.get(position) < 10)
            timeView.setText(hrA.get(position) + ":0" + minA.get(position));
        else {
            if (hrA.get(position) > 12)
                timeView.setText((hrA.get(position) - 12) + ":" + minA.get(position) + " PM");
            else
                timeView.setText(hrA.get(position) + ":" + minA.get(position) + " AM");
        }
        //SET COLOR OF REPEATED DAYS IN LV
        if(repeatedDaysA.get(position).get(0)){sun.setTextColor(Color.parseColor("#EFE82406"));}else {sun.setTextColor(Color.parseColor("#FF797979"));}
        if(repeatedDaysA.get(position).get(1)){mon.setTextColor(Color.parseColor("#EFE82406"));}else {mon.setTextColor(Color.parseColor("#FF797979"));}
        if(repeatedDaysA.get(position).get(2)){tue.setTextColor(Color.parseColor("#EFE82406"));}else {tue.setTextColor(Color.parseColor("#FF797979"));}
        if(repeatedDaysA.get(position).get(3)){wed.setTextColor(Color.parseColor("#EFE82406"));}else {wed.setTextColor(Color.parseColor("#FF797979"));}
        if(repeatedDaysA.get(position).get(4)){thu.setTextColor(Color.parseColor("#EFE82406"));}else {thu.setTextColor(Color.parseColor("#FF797979"));}
        if(repeatedDaysA.get(position).get(5)){fri.setTextColor(Color.parseColor("#EFE82406"));}else {fri.setTextColor(Color.parseColor("#FF797979"));}
        if(repeatedDaysA.get(position).get(6)){sat.setTextColor(Color.parseColor("#EFE82406"));}else {sat.setTextColor(Color.parseColor("#FF797979"));}

        cb.setChecked(activeA.get(position));
        cb.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                AlarmDBHelper helper = new AlarmDBHelper(v.getContext());
                int pid = helper.updateActive(position);
                Intent myIntent = new Intent(mContext, AlarmService.class);
                PendingIntent pi = PendingIntent.getBroadcast(mContext, pid, myIntent, 0);
                AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

                if (!cb.isChecked()) {
                    alarmManager.cancel(pi);
                    Toast.makeText(mContext, "Alarm canceled", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar calendar = helper.getCalendar(position);
                    //IF REPEATED DAYS
                    if (repeatedDaysA.get(position).contains(true)) {
                        myIntent.putExtra("days", repeatedDaysA.get(position));
                        pi = PendingIntent.getBroadcast(mContext, pid, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pi);
                        Toast.makeText(mContext, "Alarm set repeating", Toast.LENGTH_SHORT).show();
                    }
                    //IF NOT REPEATING
                    else {
                        ArrayList<Boolean> alt = new ArrayList<Boolean>(); alt.add(true);
                        myIntent.putExtra("days", alt);
                        pi = PendingIntent.getBroadcast(mContext, pid, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
                        Toast.makeText(mContext, "Alarm set Once", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return convertView;
    }
}
