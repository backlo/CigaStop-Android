package com.example.lenovo.cigastop.util;

import android.util.Log;

import com.example.lenovo.cigastop.model.FriendDto;
import com.example.lenovo.cigastop.model.RankingModel;
import com.example.lenovo.cigastop.model.UserInfo;
import com.example.lenovo.cigastop.model.UserInfoEvent;
import com.facebook.Profile;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

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
    }

    public void getUserInfo(final ArrayList<FriendDto> dataList){
        final ArrayList<UserInfo> infoArrayList = new ArrayList<>();
        for (FriendDto data : dataList) {
            userRef.child(data.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    infoArrayList.add(userInfo);
                    if(infoArrayList.size() == dataList.size())
                        EventBus.getDefault().post(new UserInfoEvent(true, infoArrayList));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void getUserInfo(String id){
        final ArrayList<UserInfo> infoArrayList = new ArrayList<>();
        userRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                    Log.d("name", userInfo.getName());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }

    public void setRanking(RankingModel ranking){
        rankingRef.child(Profile.getCurrentProfile().getId()).push().setValue(ranking);
    }

    public void getRanking(String id){
        rankingRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ranking", dataSnapshot.getValue(RankingModel.class).getRankingData().get(0).getName());
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
