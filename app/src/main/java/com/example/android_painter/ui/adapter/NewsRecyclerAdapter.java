package com.example.android_painter.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.android_painter.R;
import com.example.android_painter.app.GlideApp;
import com.example.android_painter.model.NewsInfo;
import com.example.android_painter.util.DisplayUtils;

import java.util.List;

/**
 * Created by ZhangXinmin on 2018/1/17.
 * Copyright (c) 2018 . All rights reserved.
 * 资讯适配器
 */

public class NewsRecyclerAdapter extends BaseQuickAdapter<NewsInfo, BaseViewHolder> {

    private Context mContext;

    public NewsRecyclerAdapter(@NonNull Context context, @Nullable List<NewsInfo> data) {
        super(R.layout.layout_list_news_item, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsInfo item) {
        helper.setText(R.id.tv_news_title, DisplayUtils.isEmpty(item.getDesc()));//标题

        //作者
        final TextView author = helper.getView(R.id.tv_news_author);
        if (!"null".equals(item.getWho()) && !TextUtils.isEmpty(item.getWho())) {
            author.setVisibility(View.VISIBLE);
            author.setText(mContext.getString(R.string.news_author, item.getWho()));
        } else {
            author.setVisibility(View.GONE);
        }
        final ImageView icon = helper.getView(R.id.iv_news_type);
        switch (item.getType()) {
            case "Android":
                icon.setImageResource(R.drawable.icon_android);
                break;
            case "iOS":
                icon.setImageResource(R.drawable.icon_ios);
                break;
            case "前端":
                icon.setImageResource(R.drawable.icon_h5);
                break;
            default:
                icon.setImageResource(R.drawable.icon_develop);
                break;
        }

        helper.setText(R.id.tv_news_time, mContext.getString(R.string.news_publish_time,
                DisplayUtils.getNewsString(item.getPublishedAt())));

        //图片
        final List<String> imageList = item.getImages();
        final ImageView logo = helper.getView(R.id.iv_pic);
        if (imageList != null && !imageList.isEmpty()) {
            logo.setVisibility(View.VISIBLE);
            GlideApp.with(mContext)
                    .load(imageList.get(0))
                    .into(logo);
        } else {
            logo.setVisibility(View.GONE);
        }

    }
}
