package com.example.lenovo.cigastop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.lenovo.cigastop.R;

public class SetCigaActivity extends AppCompatActivity {
    ImageView imageView;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ciga);

        imageView = (ImageView) findViewById(R.id.backbtn);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        numberPicker = (NumberPicker) findViewById(R.id.ciganum);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Toast.makeText(getApplicationContext(),"Selected Value is : " + newVal,Toast.LENGTH_SHORT).show();
                //값전달하면됨
                Intent intent= new Intent();
                intent.putExtra("todayciga",newVal);
            }
        });



    }

}
