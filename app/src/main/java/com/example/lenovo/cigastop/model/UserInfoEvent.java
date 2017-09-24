package com.example.lenovo.cigastop.model;

import java.util.ArrayList;

/**
 * Created by trycatch on 2017. 9. 24..
 */

public class UserInfoEvent {
    private boolean result;
    private UserInfo userInfo;

    public UserInfoEvent(boolean result, UserInfo userInfo) {
        this.result = result;
        this.userInfo = userInfo;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
