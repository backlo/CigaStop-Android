package com.example.lenovo.cigastop.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.model.FriendDto;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by trycatch on 2017. 9. 22..
 */

public class FriendAddListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<FriendDto> dataList;
    private boolean checkList[];

    public FriendAddListAdapter(Context context, ArrayList<FriendDto> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        checkList = new boolean[dataList.size()];
        for(int i = 0; i < checkList.length; i++)
            checkList[i] = false;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.friend_add_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(dataList.get(position).getPicture().getData().getUrl()).into(viewHolder.profile_image);
        viewHolder.profile_name.setText(dataList.get(position).getName());
        viewHolder.profile_check.setChecked(checkList[position]);

        return convertView;
    }

    public void addData(ArrayList<FriendDto> dataList){
        this.dataList = dataList;
        checkList = new boolean[dataList.size()];
        for(int i = 0; i < checkList.length; i++)
            checkList[i] = false;
        notifyDataSetChanged();
    }

    public void clickItem(int position){
        checkList[position] = !checkList[position];
        notifyDataSetChanged();
    }

    public ArrayList<FriendDto> getClickItem(){
        ArrayList<FriendDto> friendDtos = new ArrayList<>();
        for(int i = 0; i < checkList.length; i++)
            if(checkList[i])
                friendDtos.add(dataList.get(i));
        return friendDtos;
    }

    class ViewHolder{
        @BindView(R.id.profile_image)
        CircleImageView profile_image;

        @BindView(R.id.profile_name)
        TextView profile_name;

        @BindView(R.id.profile_check)
        CheckBox profile_check;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

}
