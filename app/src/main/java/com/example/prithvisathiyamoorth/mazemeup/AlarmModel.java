package com.example.prithvisathiyamoorth.mazemeup;

/**
 * Created by prithvisathiyamoorth on 8/27/15.
 */
public class AlarmModel {
    private int hour;
    private int min;
    private boolean active;
    private int pid;

    public AlarmModel(int hour, int min, boolean active) {
        this.hour = hour;
        this.min = min;
        this.active = active;
    }

    public int getHour() {
        return hour;
    }

    public boolean isActive() {
        return active;
    }

    public int getPid() {
        return pid;
    }

    public int getMin() {
        return min;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
