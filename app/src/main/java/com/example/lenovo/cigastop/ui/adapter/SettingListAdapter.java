package com.example.lenovo.cigastop.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.cigastop.R;

import java.util.ArrayList;

/**
 * Created by parkjunghun on 2017. 9. 24..
 */

public class SettingListAdapter extends BaseAdapter {
    private ArrayList<MySetting> settinglist = new ArrayList<>();

    @Override
    public int getCount() {
        return settinglist.size();
    }

    @Override
    public Object getItem(int position) {
        return settinglist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_list_item, null, false);

        }
        TextView textView = (TextView)convertView.findViewById(R.id.msetting);
        MySetting mysetting = settinglist.get(position);

        textView.setText(mysetting.getmSetting());

        return convertView;
    }
    public void addItem(String set){
        MySetting mysetting = new MySetting();
        mysetting.setmSetting(set);

        settinglist.add(mysetting);
    }
    public class MySetting {
        String mSetting;


        public String getmSetting() {
            return mSetting;
        }

        public void setmSetting(String mSetting) {
            this.mSetting = mSetting;
        }
    }

}
