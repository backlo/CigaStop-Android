package com.example.lenovo.cigastop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.model.UserInfo;
import com.example.lenovo.cigastop.model.UserInfoEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

public class SetCigaActivity extends AppCompatActivity {

    //@BindView(R.id.backbtn)
    ImageView backbtn;

    //@BindView(R.id.ciganum)
    NumberPicker numberPicker;

    @BindView(R.id.settingciga)
    TextView settingciga;

    int setCiga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ciga);

        backbtn = (ImageView)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        numberPicker = (NumberPicker)findViewById(R.id.ciganum) ;

        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
               /* Toast.makeText(getApplicationContext(),"Selected Value is : " + newVal,Toast.LENGTH_SHORT).show();
                //값전달하면됨
                Intent intent= new Intent();
                intent.putExtra("todayciga",newVal);*/
                setCiga = newVal;
                Toast.makeText(getApplicationContext(), setCiga, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
