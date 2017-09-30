package com.example.lenovo.cigastop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by trycatch on 2017. 9. 24..
 */

public class UserInfo {
    private String id;
    private String name;
    private String email;
    private String gender;
    private String picture;
    private int count;
    private int today;
    private int remind;
    private int settingciga;
    private long time;
    private long coolTime;

    public UserInfo() {
    }

    public UserInfo(String id, String name, String email, String gender, String picture, int count, int d, int remind, int settingciga, long time, long coolTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.picture = picture;
        this.count = count;
        this.today = today;
        this.remind = remind;
        this.settingciga = settingciga;
        this.time = time;
        this.coolTime = coolTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setCoolTime(long coolTime) {
        this.coolTime = coolTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public int getSettingciga() {return settingciga;}

    public void setSettingciga(int settingciga) {
        this.settingciga = settingciga;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getCoolTime() {
        return coolTime;
    }

    public void setCoolTime(int hour, int minute, int second) {
        this.coolTime = (hour * 60 * 60 * 1000) + (minute * 60 * 1000) + (second * 1000);
    }

    public class Picture{
        @SerializedName("data")
        private Data data;
        public Data getData() {
            return data;
        }
    }

    public class Data{
        @SerializedName("is_silhouette")
        private boolean is_silhouette;
        @SerializedName("url")
        private String url;

        public boolean is_silhouette() {
            return is_silhouette;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
