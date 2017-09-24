package com.example.lenovo.cigastop.model;

import java.util.ArrayList;

/**
 * Created by trycatch on 2017. 9. 24..
 */

public class RankingModel {
    private ArrayList<UserInfo> rankingData;

    public RankingModel() {
    }

    public RankingModel(ArrayList<UserInfo> rankingData) {
        this.rankingData = rankingData;
    }

    public ArrayList<UserInfo> getRankingData() {
        return rankingData;
    }

    public void setRankingData(ArrayList<UserInfo> rankingData) {
        this.rankingData = rankingData;
    }
}
