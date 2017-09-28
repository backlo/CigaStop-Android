package com.example.lenovo.cigastop.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.cigastop.model.UserInfo;
import com.example.lenovo.cigastop.model.UserInfoEvent;
import com.example.lenovo.cigastop.ui.adapter.PagerAdapter;
import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.util.DataBaseManager;
import com.facebook.Profile;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_viewpager)
    ViewPager main_viewpager;

    @BindView(R.id.profile_img)
    ImageView profile_img;

    @BindView(R.id.greeting_message)
    TextView greeting_message;

    @BindView(R.id.btn_home)
    ImageView btn_home;

    @BindView(R.id.btn_friend)
    ImageView btn_friend;

    @BindView(R.id.btn_setting)
    ImageView btn_setting;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        pagerAdapter = new PagerAdapter(getApplicationContext(), getSupportFragmentManager());
        main_viewpager.setAdapter(pagerAdapter);
        main_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        View.OnClickListener tabOnclick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btn_home){
                    main_viewpager.setCurrentItem(0);
                }
                else if(view.getId() == R.id.btn_friend){
                    main_viewpager.setCurrentItem(1);
                }
                else if(view.getId() == R.id.btn_setting){
                    main_viewpager.setCurrentItem(2);
                }
            }
        };
        btn_home.setOnClickListener(tabOnclick);
        btn_friend.setOnClickListener(tabOnclick);
        btn_setting.setOnClickListener(tabOnclick);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void UserInfoEvent(UserInfoEvent userInfoEvent){
        if(userInfoEvent.isResult()){
            UserInfo userInfo = userInfoEvent.getUserInfo();
            Log.d("img", userInfo.getPicture());
            Glide.with(this).load(userInfo.getPicture()).into(profile_img);
            greeting_message.setText(userInfo.getName() + "님 어서오세요 오늘도 금연 화이팅!");
        }
    }
}
