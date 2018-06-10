package com.example.android_painter.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android_painter.R;
import com.example.android_painter.ui.base.BaseActivity;
import com.example.android_painter.ui.fragment.ExploreFragment;
import com.example.android_painter.ui.fragment.HomeFragment;
import com.example.android_painter.ui.fragment.MoreFragment;
import com.example.android_painter.ui.fragment.QuotationsFragment;
import com.example.android_painter.util.BottomNavigationViewHelper;
import com.example.android_painter.util.FragmentTag;
import com.example.android_painter.util.StatusBarCompat;


public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;
    private Fragment mHomeFragment, mExploreFragment,
            mQuotationsFragment, mMoreFragment, mCurrFragment;

    private BottomNavigationView mBottomNavigationView;
    private NavigationView mNavigationView;

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

        //init toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mNavigationView = findViewById(R.id.navigation_view);

        mBottomNavigationView = findViewById(R.id.navigation_bottom_home);
        //禁用动画
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        //切换底部导航栏
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        //显示首页
        mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
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
            mFragmentManager.beginTransaction()
                    .show(fragment)
                    .hide(mCurrFragment)
                    .commit();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_search://搜索

                break;
        }
        return super.onOptionsItemSelected(item);
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
