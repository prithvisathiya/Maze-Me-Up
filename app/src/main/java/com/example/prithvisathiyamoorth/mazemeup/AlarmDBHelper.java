package com.example.prithvisathiyamoorth.mazemeup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by prithvisathiyamoorth on 8/27/15.
 */
public class AlarmDBHelper extends SQLiteOpenHelper {

    private static int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "alarmManager";
    private static String ALARM_TABLE = "alarms";

    private static String KEY_ID="id";
    private static String KEY_HOUR="hour";
    private static String KEY_MIN="min";
    private static String KEY_ACTIVE="active";
    private static String KEY_PID="pid";
    private static String KEY_NAME="name";
    private static String KEY_SUN="sun";
    private static String KEY_MON="mon";
    private static String KEY_TUE="tue";
    private static String KEY_WED="wed";
    private static String KEY_THU="thu";
    private static String KEY_FRI="fri";
    private static String KEY_SAT="sat";


    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + ALARM_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_HOUR + " INTEGER,"
                + KEY_MIN + " INTEGER," + KEY_ACTIVE + " TEXT," + KEY_PID + " INTEGER,"
                + KEY_NAME + " TEXT," + KEY_SUN + " TEXT," + KEY_MON + " TEXT,"
                + KEY_TUE + " TEXT," + KEY_WED + " TEXT," + KEY_THU + " TEXT,"
                + KEY_FRI + " TEXT," + KEY_SAT + " TEXT" + ");";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ALARM_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void addAlarm(AlarmModel alarm) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_HOUR, alarm.getHour());
        cv.put(KEY_MIN, alarm.getMin());
        if (alarm.isActive())
            cv.put(KEY_ACTIVE, "true");
        else
            cv.put(KEY_ACTIVE,"false");
        cv.put(KEY_PID, alarm.getPid());
        db.insert(ALARM_TABLE, null, cv);
        db.close();
    }
    public void addAlarm(int hr, int min, boolean active, int pid, String name, ArrayList<Boolean> days) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_HOUR, hr);
        cv.put(KEY_MIN, min);
        if (active)
            cv.put(KEY_ACTIVE, "true");
        else
            cv.put(KEY_ACTIVE, "false");
        cv.put(KEY_PID, pid);
        cv.put(KEY_NAME, name);
        cv.put(KEY_SUN, days.get(0).toString());
        cv.put(KEY_MON, days.get(1).toString());
        cv.put(KEY_TUE, days.get(2).toString());
        cv.put(KEY_WED, days.get(3).toString());
        cv.put(KEY_THU, days.get(4).toString());
        cv.put(KEY_FRI, days.get(5).toString());
        cv.put(KEY_SAT, days.get(6).toString());
        db.insert(ALARM_TABLE, null, cv);
        db.close();

    }

    public void getAllContacts() {
        String query = "SELECT * FROM " + ALARM_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                //Contact contact = new Contact();
                MainActivity.hrDataA.add(cursor.getInt(1));
                MainActivity.minDataA.add(cursor.getInt(2));
                if (cursor.getString(3).equals("true"))
                    MainActivity.activeA.add(true);
                else
                    MainActivity.activeA.add(false);
                ArrayList<Boolean> list = new ArrayList<Boolean>();
                list.add(Boolean.parseBoolean(cursor.getString(6)));
                list.add(Boolean.parseBoolean(cursor.getString(7)));
                list.add(Boolean.parseBoolean(cursor.getString(8)));
                list.add(Boolean.parseBoolean(cursor.getString(9)));
                list.add(Boolean.parseBoolean(cursor.getString(10)));
                list.add(Boolean.parseBoolean(cursor.getString(11)));
                list.add(Boolean.parseBoolean(cursor.getString(12)));
                MainActivity.repeatedA.add(list);
            } while (cursor.moveToNext());
        }
    }

    public void getAlarmInfo(int position) {
        String query = "SELECT * FROM " + ALARM_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToPosition(position);

        EditAlarm.hour = cursor.getInt(1);
        EditAlarm.min = cursor.getInt(2);
        EditAlarm.active = Boolean.parseBoolean(cursor.getString(3));
        EditAlarm.name = cursor.getString(5);
        EditAlarm.piId = cursor.getInt(4);
        EditAlarm.sunday = cursor.getString(6);
        EditAlarm.monday = cursor.getString(7);
        EditAlarm.tuesday = cursor.getString(8);
        EditAlarm.wednesday = cursor.getString(9);
        EditAlarm.thursday = cursor.getString(10);
        EditAlarm.friday = cursor.getString(11);
        EditAlarm.saturday = cursor.getString(12);

    }

    public Calendar getCalendar(int position) {
        String query = "SELECT * FROM " + ALARM_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToPosition(position);

        int hour = cursor.getInt(1);
        int min = cursor.getInt(2);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);

        //ADD CALENDER.DATE + 1 IF TIME < NOW, IN THE FUTURE.
        return calendar;
    }

    public void updateAlarm(int hr, int min, boolean active, int pid, String name, ArrayList<Boolean> days) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_HOUR, hr);
        cv.put(KEY_MIN, min);
        if (active)
            cv.put(KEY_ACTIVE, "true");
        else
            cv.put(KEY_ACTIVE, "false");
        cv.put(KEY_PID, pid);
        cv.put(KEY_NAME, name);
        cv.put(KEY_SUN, days.get(0).toString());
        cv.put(KEY_MON, days.get(1).toString());
        cv.put(KEY_TUE, days.get(2).toString());
        cv.put(KEY_WED, days.get(3).toString());
        cv.put(KEY_THU, days.get(4).toString());
        cv.put(KEY_FRI, days.get(5).toString());
        cv.put(KEY_SAT, days.get(6).toString());
        db.update(ALARM_TABLE, cv, KEY_PID + " = " + pid, null);

    }

    public int updateActive(int position) {
        String query = "SELECT * FROM " + ALARM_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToPosition(position);
        int pid = cursor.getInt(4);
        String active = cursor.getString(3);

        ContentValues cv = new ContentValues();
        if (active.equals("false"))
            cv.put(KEY_ACTIVE, "true");
        else
            cv.put(KEY_ACTIVE, "false");
        db.update(ALARM_TABLE, cv, KEY_PID + " = " + pid, null);
        return pid;
    }

    public void deleteAlarm(int pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ALARM_TABLE, KEY_PID + " = " + pid, null);
        db.close();
    }

    public int getNextPI() {
        String query = "SELECT * FROM " + ALARM_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            return cursor.getInt(4) + 1;
        }
        return 0;
    }
}
