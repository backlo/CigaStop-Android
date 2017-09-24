package com.example.lenovo.cigastop.ui.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lenovo.cigastop.ui.adapter.PagerAdapter;
import com.example.lenovo.cigastop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_viewpager)
    ViewPager main_viewpager;

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
}
