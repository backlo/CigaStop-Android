package com.example.lenovo.cigastop.util;

import android.util.Log;

import com.example.lenovo.cigastop.model.Time;
import com.example.lenovo.cigastop.model.UserInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lenovo on 2017-09-26.
 */

public class Util {
    public static Util instance = new Util();
    private UserInfo userInfo;
    private SimpleDateFormat simpleDateFormat;

    public Util() {
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
    }

    public static Util getInstance() {
        return instance;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getCurDate(){
        String date = "";
        try {
            date = simpleDateFormat.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public long getCurTime(){
        String date = "";
        long time = 0;
        try {
            date = simpleDateFormat.format(Calendar.getInstance().getTime());
            time = simpleDateFormat.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public Time getRemainTime(){
        Time time = new Time();
        try {
            Date date = simpleDateFormat.parse(getCurDate());
            long hour, minute, second;
            long rTime = userInfo.getTime() - date.getTime();
            Log.d("time", userInfo.getTime() + ", " + date.getTime());
            Log.d("rtime", rTime + "");
            hour = rTime / (60 * 60 * 1000);
            minute = rTime % (60 * 60 * 1000) / (60 * 1000) ;
            second = rTime % (60 * 60 * 1000) % (60 * 1000) / 1000;
            Log.d("date", hour + ", " + minute + ", " + second);
            if(rTime > 0)
                time.setClear(false);
            else
                time.setClear(true);
            time.setTime(rTime);
            time.setHour(hour);
            time.setMinute(minute);
            time.setSecond(second);
        }catch (Exception e){
            e.printStackTrace();
        }

        return time;
    }
}
