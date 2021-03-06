package com.example.lenovo.cigastop.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.lenovo.cigastop.ui.fragment.FriendFragment;
import com.example.lenovo.cigastop.ui.fragment.HomeFragment;
import com.example.lenovo.cigastop.ui.fragment.SettingFragment;

/**
 * Created by trycatch on 2017. 9. 20..
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private Bundle bundle;
    private HomeFragment homeFragment;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new FriendFragment();
            case 2:
                return new SettingFragment();
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(position != 0)
            super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
