package com.example.lenovo.cigastop.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.model.FriendAddEvent;
import com.example.lenovo.cigastop.model.RankingModel;
import com.example.lenovo.cigastop.model.UserInfo;
import com.example.lenovo.cigastop.model.UserInfoArrayEvent;
import com.example.lenovo.cigastop.ui.activity.FriendAddActivity;
import com.example.lenovo.cigastop.ui.adapter.FriendListAdapter;
import com.example.lenovo.cigastop.util.DataBaseManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendFragment extends Fragment {
    @BindView(R.id.ranking_list)
    ListView ranking_list;

    @BindView(R.id.friend_add_btn)
    ImageView friend_add_btn;

    private FriendListAdapter adapter;
    private ArrayList<ArrayList<UserInfo>> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friend, container, false);

        ButterKnife.bind(this, v);

        EventBus.getDefault().register(this);

        dataList = new ArrayList<>();

        friend_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FriendAddActivity.class));
            }
        });

        adapter = new FriendListAdapter(getActivity(), dataList);
        ranking_list.setAdapter(adapter);

        //DataBaseManager.getInstance().getRanking(Profile.getCurrentProfile().getId());

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void FriendAddEvent(FriendAddEvent friendAddEvent){
        if(friendAddEvent.isResult()){
            if(friendAddEvent.getDataList() != null){
                DataBaseManager.getInstance().getUserInfo(friendAddEvent.getDataList());
            }
        }
    }

    @Subscribe
    public void UserInfoArrayEvent(UserInfoArrayEvent userInfoEvent){
        if(userInfoEvent.isResult()){
            dataList.add(userInfoEvent.getDataList());
            adapter.setData(dataList);
            DataBaseManager.getInstance().setRanking(new RankingModel(userInfoEvent.getDataList()));
        }
    }
}