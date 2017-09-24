package com.example.lenovo.cigastop.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.model.FriendDto;
import com.example.lenovo.cigastop.model.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by trycatch on 2017. 9. 22..
 */

public class FriendListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ArrayList<UserInfo>> dataList;

    public FriendListAdapter(Context context, ArrayList<ArrayList<UserInfo>> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sort();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(ArrayList<ArrayList<UserInfo>> dataList){
        this.dataList = dataList;
        sort();
        notifyDataSetChanged();
    }

    public void addData(ArrayList<UserInfo> data){
        this.dataList.add(data);
        sort();
        notifyDataSetChanged();
    }

    public void sort(){
        for(int i = 0; i < dataList.size(); i++) {
            Collections.sort(dataList.get(i), new Comparator<UserInfo>() {
                @Override
                public int compare(UserInfo o1, UserInfo o2) {
                    if (o1.getCount() < o2.getCount())
                        return 1;
                    else if (o1.getCount() > o2.getCount())
                        return -1;
                    else
                        return 0;
                }
            });
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.friend_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        for(int i = 0; i < dataList.get(position).size(); i++){
            switch (i) {
                case 0:
                    viewHolder.the_first_name.setText(dataList.get(position).get(i).getName());
                    viewHolder.the_first_count.setText(dataList.get(position).get(i).getCount() + "개피");
                    break;
                case 1:
                    viewHolder.the_second_name.setText(dataList.get(position).get(i).getName());
                    viewHolder.the_second_count.setText(dataList.get(position).get(i).getCount() + "개피");
                    break;
                case 2:
                    viewHolder.the_third_name.setText(dataList.get(position).get(i).getName());
                    viewHolder.the_third_count.setText(dataList.get(position).get(i).getCount() + "개피");
                    break;
                default:
                    break;
            }
        }

        return convertView;
    }

    class ViewHolder{
        @BindView(R.id.the_first_name)
        TextView the_first_name;

        @BindView(R.id.the_first_count)
        TextView the_first_count;

        @BindView(R.id.the_second_name)
        TextView the_second_name;

        @BindView(R.id.the_second_count)
        TextView the_second_count;

        @BindView(R.id.the_third_name)
        TextView the_third_name;

        @BindView(R.id.the_third_count)
        TextView the_third_count;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

}
