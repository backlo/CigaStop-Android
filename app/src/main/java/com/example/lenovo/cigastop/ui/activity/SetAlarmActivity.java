package com.example.lenovo.cigastop.ui.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lenovo.cigastop.R;


public class SetAlarmActivity extends AppCompatActivity {
    ImageView imageView;
    Switch aSwitch;
    Switch bSwitch;

    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        aSwitch = (Switch)findViewById(R.id.switch1);
        bSwitch = (Switch)findViewById(R.id.switch2);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(SetAlarmActivity.this)
                .setSmallIcon(R.mipmap.ciga)
                .setContentTitle("담배")
                .setContentTitle("담배");
        final NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

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

                   // NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0,mBuilder.build());

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
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Toast.makeText(getApplicationContext(), "진동모드 실행", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
