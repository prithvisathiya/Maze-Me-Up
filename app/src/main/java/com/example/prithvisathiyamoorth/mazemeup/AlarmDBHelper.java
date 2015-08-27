package com.example.prithvisathiyamoorth.mazemeup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + ALARM_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_HOUR + " INTEGER,"
                + KEY_MIN + " INTEGER," + KEY_ACTIVE + " TEXT);";

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

        db.insert(ALARM_TABLE, null, cv);
        db.close();
    }
    public void addAlarm(int hr, int min, boolean active) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_HOUR, hr);
        cv.put(KEY_MIN, min);
        if (active)
            cv.put(KEY_ACTIVE, "true");
        else
            cv.put(KEY_ACTIVE, "false");

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
                if (cursor.getString(3) .equals("true"))
                    MainActivity.activeA.add(true);
                else
                    MainActivity.activeA.add(false);
                MainActivity.repeatedA.add("S  M   T   W   TH   F  S");
            } while (cursor.moveToNext());
        }
    }
}
