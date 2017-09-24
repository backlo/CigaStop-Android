package com.example.lenovo.cigastop.model;

import java.util.ArrayList;

/**
 * Created by trycatch on 2017. 9. 25..
 */

public class RankingEvent {
    private boolean result;
    private ArrayList<RankingModel> rankingModels;

    public RankingEvent(boolean result, ArrayList<RankingModel> rankingModels) {
        this.result = result;
        this.rankingModels = rankingModels;
    }

    public boolean isResult() {
        return result;
    }

    public ArrayList<RankingModel> getRankingModels() {
        return rankingModels;
    }
}
