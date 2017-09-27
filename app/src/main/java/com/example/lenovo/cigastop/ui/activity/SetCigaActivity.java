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

public class SetCigaActivity extends AppCompatActivity {

    @BindView(R.id.backbtn)
    ImageView backbtn;

    @BindView(R.id.ciganum)
    NumberPicker numberPicker;

    int setCiga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ciga);

        ButterKnife.bind(this);

//        HomeFragment fragment = new HomeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("setciga",setCiga);
//        fragment.setArguments(bundle);
//        Log.d("setciga"+setCiga,"setciga");

        backbtn = (ImageView)findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo info = Util.getInstance().getUserInfo();
                if(info != null) {
                    info.setSettingciga(setCiga);
                    DataBaseManager.getInstance().setUserInfo(info);

                }
                finish();
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
            }
        });

    }
}
