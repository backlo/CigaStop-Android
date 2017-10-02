package com.example.lenovo.cigastop.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.model.UserInfo;
import com.example.lenovo.cigastop.util.DataBaseManager;
import com.example.lenovo.cigastop.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SetTimeActivity extends AppCompatActivity {

    @BindView(R.id.backbtn)
    ImageView backbtn;

    @BindView(R.id.hours)
    NumberPicker hourPicker;

    @BindView(R.id.minutes)
    NumberPicker minutePicker;

    @BindView(R.id.seconds)
    NumberPicker secondPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        ButterKnife.bind(this);

        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);

        minutePicker.setMaxValue(60);
        minutePicker.setMinValue(0);

        secondPicker.setMaxValue(60);
        secondPicker.setMinValue(0);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserInfo info = Util.getInstance().getUserInfo();

                    if(info !=null) {
                        int hour = hourPicker.getValue();
                        int minute = minutePicker.getValue();
                        int second = secondPicker.getValue();
                        info.setCoolTime(hour, minute, second);
                        DataBaseManager.getInstance().setUserInfo(info);
                    }
                finish();
            }
        });

    }
}
