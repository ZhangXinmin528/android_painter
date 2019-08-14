package com.zxm.android_painter.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxm.android_painter.R;
import com.zxm.android_painter.model.ExploreInfo;
import com.zxm.android_painter.ui.ClipActivity;
import com.zxm.android_painter.ui.adapter.ExploreRecyclerAdapter;
import com.zxm.android_painter.ui.base.BaseFragment;
import com.zxm.android_painter.ui.normal.NormalDrawActivity;
import com.zxm.android_painter.ui.paint.PaintActivity;
import com.zxm.android_painter.ui.text.TextActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/11.
 * 探索界面
 */

public class ExploreFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    private static final String TAG = ExploreFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ExploreRecyclerAdapter mExploreRecyclerAdapter;

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Override
    protected Object setRootLayout() {
        return R.layout.fragment_explore;
    }

    @Override
    public void initParamsAndValues() {
    }

    @Override
    public void initViews(View rootView) {

        mRecyclerView = rootView.findViewById(R.id.recyclerview_explore);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //init adapter
        mExploreRecyclerAdapter = new ExploreRecyclerAdapter(initExploreData());
        mRecyclerView.setAdapter(mExploreRecyclerAdapter);
        mExploreRecyclerAdapter.setOnItemClickListener(this);//点击事件
        mExploreRecyclerAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    //TODO:生成测试数据
    private List<ExploreInfo> initExploreData() {
        List<ExploreInfo> list = new ArrayList<>();
        //常规绘制
        ExploreInfo common = new ExploreInfo(
                "常规绘制",
                getString(R.string.desc_common),
                R.drawable.click_head_img_0);
        //裁剪绘制
        ExploreInfo clip = new ExploreInfo(
                "范围裁切",
                getString(R.string.desc_clip),
                R.drawable.click_head_img_1);

        //Paint相关
        ExploreInfo paint = new ExploreInfo(
                "Paint相关",
                getString(R.string.desc_paint),
                R.drawable.click_head_img_0);

        //文字绘制相关
        ExploreInfo text = new ExploreInfo(
                "文字绘制",
                getString(R.string.desc_text),
                R.drawable.click_head_img_1);

        list.add(common);
        list.add(clip);
        list.add(paint);
        list.add(text);
        return list;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                Intent canvas = new Intent(mContext, NormalDrawActivity.class);
                startActivity(canvas);
                break;
            case 1:
                Intent clip = new Intent(mContext, ClipActivity.class);
                startActivity(clip);
                break;
            case 2:
                Intent paint = new Intent(mContext, PaintActivity.class);
                startActivity(paint);
                break;
            case 3:
                Intent text = new Intent(mContext, TextActivity.class);
                startActivity(text);
                break;
        }
    }
}
