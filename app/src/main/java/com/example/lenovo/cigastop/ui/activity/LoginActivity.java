package com.example.lenovo.cigastop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.model.UserInfo;
import com.example.lenovo.cigastop.util.DataBaseManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by trycatch on 2017. 9. 20..
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_button)
    LoginButton login_button;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();

        login_button.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));
        login_button.setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY);
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());
                        try {
                            UserInfo userInfo = new UserInfo("", "", "", "", "", 0, 0, 0, 0, 0, 0);
                            if(object.has("id"))
                                userInfo.setId(object.getString("id"));
                            if(object.has("name"))
                                userInfo.setName(object.getString("name"));
                            if (object.has("email"))
                                userInfo.setEmail(object.getString("email"));
                            if (object.has("gender"))
                                userInfo.setGender(object.getString("gender"));
                            if (object.has("picture") && object.getJSONObject("picture").has("data") && object.getJSONObject("picture").getJSONObject("data").has("url"))
                                userInfo.setPicture(object.getJSONObject("picture").getJSONObject("data").getString("url"));
                            DataBaseManager.getInstance().setUserInfo(userInfo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr",error.toString());
            }
        });

        if(Profile.getCurrentProfile() != null)
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
