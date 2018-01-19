package com.example.android_painter.ui.fragment;

import android.content.Context;
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
 * 更多
 */

public class MoreFragment extends BaseFragment {
    public static final String TAG = MoreFragment.class.getName();
    private Context mContext;

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    void initParamsAndValues() {

    }

    @Override
    void initViews(View rootView) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView view = new TextView(getActivity());
        view.setText("这是更多界面！");
        view.setTextSize(18);
        view.setTextColor(Color.BLUE);
        Log.i(TAG, "onCreateView");
        return view;
    }

    @Override
    protected Object setRootLayout() {
        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_more, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
