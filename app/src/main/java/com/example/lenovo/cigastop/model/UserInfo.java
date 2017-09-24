package com.example.lenovo.cigastop.model;

/**
 * Created by trycatch on 2017. 9. 24..
 */

public class UserInfo {
    private String id;
    private String name;
    private String email;
    private String gender;
    private int count;

    public UserInfo() {
    }

    public UserInfo(String id, String name, String email, String gender, int count) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.count = count;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
