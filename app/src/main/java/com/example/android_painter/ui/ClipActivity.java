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
 * Created by ZhangXinmin on 2017/7/18.
 * Copyright (c) 2017 . All rights reserved.
 * 画布裁剪
 */

public class ClipActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        mContext = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clip, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clip_path:
                Intent path = new Intent(mContext, ClipDisplayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("drawType", "clipPath");
                path.putExtra("bundle", bundle);
                startActivityForResult(path, 100);
                break;
            case R.id.clip_rect:
                Intent rect = new Intent(mContext, ClipDisplayActivity.class);
                Bundle b = new Bundle();
                b.putString("drawType", "clipRect");
                rect.putExtra("bundle", b);
                startActivityForResult(rect, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
