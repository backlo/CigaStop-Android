package com.example.lenovo.cigastop.model;

import java.util.ArrayList;

/**
 * Created by trycatch on 2017. 9. 24..
 */

public class UserInfoEvent {
    private boolean result;
    private ArrayList<UserInfo> dataList;

    public UserInfoEvent(boolean result, ArrayList<UserInfo> dataList) {
        this.result = result;
        this.dataList = dataList;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ArrayList<UserInfo> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<UserInfo> dataList) {
        this.dataList = dataList;
    }
}
