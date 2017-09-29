package com.example.lenovo.cigastop.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.ui.activity.SetAlarmActivity;
import com.example.lenovo.cigastop.ui.activity.SetCigaActivity;
import com.example.lenovo.cigastop.ui.activity.SetLogoutActivity;
import com.example.lenovo.cigastop.ui.activity.SetTimeActivity;
import com.example.lenovo.cigastop.ui.activity.SetVersionActivity;
import com.example.lenovo.cigastop.ui.adapter.SettingListAdapter;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    SettingListAdapter adapter;
    private ListView listView;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, v);

        adapter = new SettingListAdapter();
        listView = (ListView)v.findViewById(R.id.setting);

        adapter.addItem("담배 케이스 시간 설정");
        adapter.addItem("오늘 필 담배개수 설정");
        adapter.addItem("알림 설정");
        adapter.addItem("버전정보");
        adapter.addItem("로그아웃");

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position){
                    case 0:
                        Intent intent1 = new Intent(getActivity(), SetTimeActivity.class);
                        startActivity(intent1);
                        Toast.makeText(getContext(),"담배 케이스 시간 설정",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Intent intent2 = new Intent(getActivity(), SetCigaActivity.class);
                        startActivity(intent2);
                        Toast.makeText(getContext(),"오늘 필 담배갯수 설정",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Intent intent3 = new Intent(getActivity(), SetAlarmActivity.class);
                        startActivity(intent3);
                        Toast.makeText(getContext(),"알림 설정",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Intent intent4 = new Intent(getActivity(), SetVersionActivity.class);
                        startActivity(intent4);
                        Toast.makeText(getContext(),"버전 정보",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Intent intent5 = new Intent(getActivity(), SetLogoutActivity.class);
                        startActivity(intent5);
                        Toast.makeText(getContext(),"로그 아웃",Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        });

        listView.setAdapter(adapter);

        return v;
    }


}
