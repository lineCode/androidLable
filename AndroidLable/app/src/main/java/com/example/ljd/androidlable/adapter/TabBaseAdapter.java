package com.example.ljd.androidlable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ljd.androidlable.R;

import java.util.List;

/**
 * Created by liujiandong on 16/5/31.
 */
public class TabBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mDatas;

    public TabBaseAdapter(Context context, List<String> list) {
        mContext = context;
        mDatas = list;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public String getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_layout,null) ;
            holder = new ViewHolder() ;
            holder.tab = (TextView) convertView.findViewById(R.id.tv_tab);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String text = getItem(position) ;
        holder.tab.setText(text);
        return convertView;
    }

    static class ViewHolder{
        TextView tab ;
    }
}
