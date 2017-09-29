package com.example.lenovo.cigastop.util;

import android.util.Log;

import com.example.lenovo.cigastop.model.FriendDto;
import com.example.lenovo.cigastop.model.RankingEvent;
import com.example.lenovo.cigastop.model.RankingModel;
import com.example.lenovo.cigastop.model.UserInfo;
import com.example.lenovo.cigastop.model.UserInfoArrayEvent;
import com.example.lenovo.cigastop.model.UserInfoEvent;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by trycatch on 2017. 9. 24..
 */

public class DataBaseManager {
    public static DataBaseManager instance = new DataBaseManager();
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference rankingRef;

    public DataBaseManager() {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        rankingRef = database.getReference("ranking");
    }

    public void setUserInfo(UserInfo userInfo){
        userRef.child(userInfo.getId()).setValue(userInfo);
        Util.getInstance().setUserInfo(userInfo);
        EventBus.getDefault().post(new UserInfoEvent(true, userInfo));
    }

    public void getUserInfo(final ArrayList<FriendDto> dataList){
        final ArrayList<UserInfo> infoArrayList = new ArrayList<>();
        for (FriendDto data : dataList) {
            userRef.child(data.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("userInfo", dataSnapshot.toString());
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    infoArrayList.add(userInfo);
                    if(infoArrayList.size() == dataList.size())
                        EventBus.getDefault().post(new UserInfoArrayEvent(true, infoArrayList));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    EventBus.getDefault().post(new UserInfoArrayEvent(false, infoArrayList));
                }
            });
        }
    }

    public void getUserInfo(String id){
        userRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    EventBus.getDefault().post(new UserInfoEvent(true, userInfo));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    EventBus.getDefault().post(new UserInfoEvent(false, new UserInfo()));
                }
            });

    }

    public void setRanking(RankingModel ranking){
        ArrayList<UserInfo> infoArrayList = ranking.getRankingData();
        String id = "";
        for(int i = 0; i < infoArrayList.size(); i++)
            id += infoArrayList.get(i).getId();
        rankingRef.child(Profile.getCurrentProfile().getId()).child(id).setValue(ranking);
        getRanking(Profile.getCurrentProfile().getId());
    }

    public void getRanking(String id){
        rankingRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<RankingModel> rankingModels = new ArrayList
                        <RankingModel>();
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()) {
                    DataSnapshot data = iterator.next();
                    rankingModels.add(new RankingModel(data.getValue(RankingModel.class).getRankingData()));
                }
                EventBus.getDefault().post(new RankingEvent(true, rankingModels));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static DataBaseManager getInstance() {
        return instance;
    }
}
