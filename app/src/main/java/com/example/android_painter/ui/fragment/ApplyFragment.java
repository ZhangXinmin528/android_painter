package com.example.android_painter.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android_painter.R;


/**
 * Created by Administrator on 2016/10/11.
 * 应用
 */

public class ApplyFragment extends BaseFragment {
    public static final String TAG = ApplyFragment.class.getName();

    public static ApplyFragment newInstance() {
        return new ApplyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        TextView view = new TextView(getActivity());
        view.setText("这是应用界面！");
        view.setTextSize(18);
        view.setTextColor(Color.BLUE);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_notice, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:

                break;
            case R.id.action_more:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void initParamsAndValues() {

    }
}
