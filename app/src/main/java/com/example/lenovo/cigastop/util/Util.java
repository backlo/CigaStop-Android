package com.example.lenovo.cigastop.util;

import com.example.lenovo.cigastop.model.UserInfo;

/**
 * Created by lenovo on 2017-09-26.
 */

public class Util {
    public static Util instance = new Util();
    private UserInfo userInfo;

    public static Util getInstance() {
        return instance;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
