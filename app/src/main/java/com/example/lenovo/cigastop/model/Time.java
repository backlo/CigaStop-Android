package com.example.lenovo.cigastop.model;

/**
 * Created by trycatch on 2017. 9. 26..
 */

public class Time {
    boolean clear;
    long time;
    long hour;
    long minute;
    long second;

    public Time() {
    }

    public Time(boolean clear, long time, long hour, long minute, long second) {
        this.clear = clear;
        this.time = time;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public boolean isClear() {
        return clear;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMinute() {
        return minute;
    }

    public void setMinute(long minute) {
        this.minute = minute;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }
}
