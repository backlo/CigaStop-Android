package com.example.lenovo.cigastop.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.lenovo.cigastop.R;


public class SetTimeActivity extends AppCompatActivity {
    ImageView imageView;
    TimePicker hourPicker;
    TimePicker minutePicker;
    TimePicker SecondPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        imageView = (ImageView)findViewById(R.id.backbtn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
