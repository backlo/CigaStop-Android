package com.example.lenovo.cigastop.ui.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.lenovo.cigastop.R;


public class SetAlarmActivity extends AppCompatActivity {
    ImageView imageView;
    Switch aSwitch;
    Switch bSwitch;
    Switch cSwitch;
    Context context;
    AudioManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        aSwitch = (Switch)findViewById(R.id.switch1);
        bSwitch = (Switch)findViewById(R.id.switch2);
        cSwitch = (Switch)findViewById(R.id.switch3);






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
                if(isChecked == true){
                    if(am.getRingerMode()==0){
                        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    }
                    else if(am.getRingerMode()==2)
                        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                }
                else{
                    am = (AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                }
            }
        });

        cSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    if(am.getRingerMode() ==1){
                        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    }
                    else if(am.getRingerMode() ==2){
                        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    }

                }
                else{
                    am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                }
            }
        });


    }
}
