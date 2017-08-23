package com.example.android_painter.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android_painter.R;

/**
 * Created by ZhangXinmin on 2017/7/12.
 * Copyright (c) 2017 . All rights reserved.
 * Canvas讲解界面
 */

public class CanvasActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        initParamsAndValues();
    }

    private void initParamsAndValues() {
        mContext = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_canvas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.canvas_draw://常规绘制
                Intent normal = new Intent(mContext, NormalActivity.class);
                startActivity(normal);
                break;
            case R.id.canvas_clip://范围裁切
                Intent clip = new Intent(mContext, ClipActivity.class);
                startActivity(clip);
                break;
            case R.id.canvas_matrix://矩阵变换

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
