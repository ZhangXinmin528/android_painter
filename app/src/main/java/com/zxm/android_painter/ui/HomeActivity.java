package com.zxm.android_painter.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.zxm.android_painter.R;
import com.zxm.android_painter.ui.base.BaseActivity;
import com.zxm.android_painter.ui.fragment.ExploreFragment;
import com.zxm.android_painter.ui.fragment.HomeFragment;
import com.zxm.android_painter.ui.fragment.MoreFragment;
import com.zxm.android_painter.ui.fragment.QuotationsFragment;
import com.zxm.android_painter.util.FragmentTag;
import com.zxm.android_painter.util.StatusBarCompat;

/**
 * 首页
 */
public class HomeActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mFragmentManager;
    private Fragment mHomeFragment, mExploreFragment,
            mQuotationsFragment, mMoreFragment, mCurrFragment;

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected Object setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initParamsAndViews() {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void initViews() {
        //设置状态栏
        StatusBarCompat.setColor(this, getResources().getColor(R.color.color_status_bar));

        mBottomNavigationView = findViewById(R.id.navigation_bottom_home);
        //切换底部导航栏
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        //显示首页
        mBottomNavigationView.setSelectedItemId(R.id.navigation_home);

//        LocationManager.getInstance(getApplicationContext())
//                .startLocation();

    }

    /**
     * 切换Fragment
     *
     * @param tag
     */
    private void switchFragment(FragmentTag tag) {
        switch (tag) {
            case HOME:
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                }
                showSelectedFragment(mHomeFragment, tag);
                break;
            case EXPLORE:
                if (mExploreFragment == null) {
                    mExploreFragment = ExploreFragment.newInstance();
                }
                showSelectedFragment(mExploreFragment, tag);
                break;
            case QUOTATION:
                if (mQuotationsFragment == null) {
                    mQuotationsFragment = QuotationsFragment.newInstance();
                }
                showSelectedFragment(mQuotationsFragment, tag);
                break;
            case MORE:
                if (mMoreFragment == null) {
                    mMoreFragment = MoreFragment.newInstance();
                }
                showSelectedFragment(mMoreFragment, tag);
                break;
            default:
                break;
        }
    }

    /**
     * fragment显示隐藏的逻辑
     *
     * @param fragment
     * @param tag
     */
    private void showSelectedFragment(Fragment fragment, FragmentTag tag) {
        if (fragment == null)
            return;

        if (fragment.isAdded()) {
            if (!fragment.isVisible()) {
                mFragmentManager.beginTransaction()
                        .show(fragment)
                        .hide(mCurrFragment)
                        .commit();
            }

        } else {
            mFragmentManager.beginTransaction()
                    .add(R.id.container, fragment, tag.name())
                    .commit();

            if (mCurrFragment != null) {
                mFragmentManager.beginTransaction()
                        .hide(mCurrFragment)
                        .commit();
            }
        }
        mCurrFragment = fragment;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                switchFragment(FragmentTag.HOME);
                break;
            case R.id.navigation_explore:
                switchFragment(FragmentTag.EXPLORE);
                break;
            case R.id.navigation_apply:
                switchFragment(FragmentTag.QUOTATION);
                break;
            case R.id.navigation_more:
                switchFragment(FragmentTag.MORE);
                break;
            default:
                break;
        }
        return true;
    }
}
