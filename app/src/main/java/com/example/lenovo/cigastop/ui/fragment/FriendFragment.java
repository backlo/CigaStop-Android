package com.example.lenovo.cigastop.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.model.FriendAddEvent;
import com.example.lenovo.cigastop.ui.activity.FriendAddActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendFragment extends Fragment {
    @BindView(R.id.friend_add_btn)
    ImageView friend_add_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friend, container, false);

        ButterKnife.bind(this, v);

        EventBus.getDefault().register(this);

        friend_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FriendAddActivity.class));
            }
        });

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
                Log.i("name", friendAddEvent.getDataList().get(0).getName());
            }
        }
    }
}