package com.example.customview_simple.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.customview_simple.R;
import com.example.customview_simple.model.ItemInfo;

import java.util.List;

/**
 * Created by ZhangXinmin on 2017/7/13.
 * Copyright (c) 2017 . All rights reserved.
 */

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    private List<ItemInfo> mList;

    public CustomAdapter(Context mContext, List<ItemInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ItemInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, null);
            holder.item = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ItemInfo info = getItem(position);
        if (!TextUtils.isEmpty(info.getName()))
            holder.item.setText(info.getName());
        return convertView;
    }

    class ViewHolder {
        TextView item;
    }
}
