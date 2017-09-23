package com.example.lenovo.cigastop;

import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trycatch on 2017. 9. 23..
 */

public class FriendAddActivity extends AppCompatActivity {
    @BindView(R.id.add_friend_list)
    ListView add_friend_list;

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
    }
}

