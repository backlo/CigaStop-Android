package com.example.lenovo.cigastop.model;

import java.util.ArrayList;

/**
 * Created by trycatch on 2017. 9. 24..
 */

public class FriendAddEvent {
    private boolean result;
    private ArrayList<FriendDto> dataList;

    public FriendAddEvent(boolean result, ArrayList<FriendDto> dataList) {
        this.result = result;
        this.dataList = dataList;
    }

    public boolean isResult() {
        return result;
    }

    public ArrayList<FriendDto> getDataList() {
        return dataList;
    }
}
