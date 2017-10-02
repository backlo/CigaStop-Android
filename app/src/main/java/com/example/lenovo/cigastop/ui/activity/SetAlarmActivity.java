package com.example.lenovo.cigastop.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.model.UserInfo;
import com.example.lenovo.cigastop.util.DataBaseManager;
import com.example.lenovo.cigastop.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SetAlarmActivity extends AppCompatActivity {
    ImageView imageView;
    Switch aSwitch;
    Switch bSwitch;

    @BindView(R.id.btn_home)
    ImageView home;
    //private boolean setalarm;

    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        ButterKnife.bind(this);

        home.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                /*serInfo info = Util.getInstance().getUserInfo();
                if(info != null) {
                    info.setSetalarm(setalarm);
                    DataBaseManager.getInstance().setUserInfo(info);
                }*/

            }
        });



        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        aSwitch = (Switch)findViewById(R.id.switch1);
        bSwitch = (Switch)findViewById(R.id.switch2);



        imageView = (ImageView)findViewById(R.id.backbtn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                }

                else{
                }
            }
        });

        bSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), audioManager.FLAG_PLAY_SOUND);
                    Toast.makeText(getApplicationContext(), "소리모드 실행", Toast.LENGTH_SHORT).show();
                    //setalarm = true;
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Toast.makeText(getApplicationContext(), "진동모드 실행", Toast.LENGTH_SHORT).show();
                    //setalarm  = false;
                }
            }
        });

    }
}
