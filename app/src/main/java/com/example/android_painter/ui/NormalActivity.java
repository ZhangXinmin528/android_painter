package com.example.android_painter.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android_painter.R;
import com.example.android_painter.ui.adapter.CustomAdapter;
import com.example.android_painter.model.ItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2017/7/13.
 * Copyright (c) 2017 . All rights reserved.
 * 常规绘制
 */

public class NormalActivity extends AppCompatActivity {
    private Context mContext;
    private ListView mListView;
    private List<ItemInfo> mDataList;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        initParamsAndValues();

        initViews();

    }

    private void initParamsAndValues() {
        mContext = this;
        mDataList = new ArrayList<>();
        //init data
        mDataList.add(new ItemInfo("drawArc", "drawArc"));
        mDataList.add(new ItemInfo("drawBitmap", "drawBitmap"));
        mDataList.add(new ItemInfo("drawColor", "drawColor"));
        mDataList.add(new ItemInfo("drawCircle", "drawCircle"));
        mDataList.add(new ItemInfo("drawLine", "drawLine"));
        mDataList.add(new ItemInfo("drawBitmapMesh", "drawBitmapMesh"));
        mDataList.add(new ItemInfo("drawOval", "drawOval"));
        mDataList.add(new ItemInfo("drawPath", "drawPath"));
        mDataList.add(new ItemInfo("drawPoint", "drawPoint"));
        mDataList.add(new ItemInfo("drawPaint", "drawPaint"));
        mDataList.add(new ItemInfo("drawPoints", "drawPoints"));
        mDataList.add(new ItemInfo("drawRect", "drawRect"));
        mDataList.add(new ItemInfo("drawRGB", "drawRGB"));
        mDataList.add(new ItemInfo("drawRoundRect", "drawRoundRect"));
        mDataList.add(new ItemInfo("drawText", "drawText"));
        mDataList.add(new ItemInfo("drawTextOnPath", "drawTextOnPath"));
        mDataList.add(new ItemInfo("drawTextRun", "drawTextRun"));
        mDataList.add(new ItemInfo("drawVertices", "drawVertices"));
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.normal_listview);
        mAdapter = new CustomAdapter(mContext, mDataList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemInfo info = mDataList.get(position);
                if (info == null) return;
                Intent intent = new Intent(mContext, NormalItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", info);
                intent.putExtra("bundle", bundle);
                startActivityForResult(intent, 100);
            }
        });
    }
}
