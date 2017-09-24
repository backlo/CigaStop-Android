package com.example.lenovo.cigastop.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.lenovo.cigastop.model.FriendAddEvent;
import com.example.lenovo.cigastop.ui.adapter.FriendAddListAdapter;
import com.example.lenovo.cigastop.model.FriendDto;
import com.example.lenovo.cigastop.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trycatch on 2017. 9. 23..
 */

public class FriendAddActivity extends AppCompatActivity {
    @BindView(R.id.add_friend_list)
    ListView add_friend_list;

    @BindView(R.id.add_friend_btn)
    Button add_friend_btn;

    private FriendAddListAdapter adapter;
    private ArrayList<FriendDto> dataList;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);

        gson = new GsonBuilder().create();
        dataList = new ArrayList<>();

        GraphRequest request = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
            @Override
            public void onCompleted(JSONArray objects, GraphResponse response) {
                for(int i = 0; i < objects.length(); i++) {
                    try {
                        dataList.add(gson.fromJson(objects.get(i).toString(), FriendDto.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                adapter.addData(dataList);
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "picture,name");
        request.setParameters(parameters);
        request.executeAsync();

        adapter = new FriendAddListAdapter(getApplicationContext(), dataList);
        add_friend_list.setAdapter(adapter);
        add_friend_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.clickItem(position);
            }
        });

        add_friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new FriendAddEvent(true, adapter.getClickItem()));
                finish();
            }
        });
    }
}

