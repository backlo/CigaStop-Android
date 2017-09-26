package com.example.lenovo.cigastop.ui.activity;

import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lenovo.cigastop.R;


public class SetAlarmActivity extends AppCompatActivity {
    ImageView imageView;

    AudioManager audioManager;
    private final Context context = this;

    Switch aSwitch;
    Switch bSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        aSwitch = (Switch) findViewById(R.id.switch1);
        bSwitch = (Switch) findViewById(R.id.switch2);

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(SetAlarmActivity.this)
                .setSmallIcon(R.mipmap.ciga)
                .setContentTitle("담배")
                .setContentTitle("담배");
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        imageView = (ImageView) findViewById(R.id.backbtn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {

                    // NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0, mBuilder.build());

                } else {

                }
            }
        });

        bSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Toast.makeText(getApplicationContext(), "진동모드 실행", Toast.LENGTH_SHORT).show();
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, 3, audioManager.FLAG_PLAY_SOUND);
                    Toast.makeText(getApplicationContext(), "진동모드 끄기", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
