package com.example.lenovo.cigastop.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.lenovo.cigastop.R;

import butterknife.BindView;


public class SetTimeActivity extends AppCompatActivity {

    @BindView(R.id.backbtn)
    ImageView backbtn;

    @BindView(R.id.hours)
    TimePicker hourPicker;

    @BindView(R.id.minutes)
    TimePicker minutePicker;

    @BindView(R.id.seconds)
    TimePicker SecondPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
