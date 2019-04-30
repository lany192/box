package com.lany.box.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.lany.box.activity.DaggerActivity;
import com.lany.box.adapter.TabAdapter;
import com.lany.box.adapter.TabItem;
import com.lany.box.config.UIConfig;
import com.lany.box.sample.fragment.HelloFragment;
import com.lany.box.sample.fragment.IndexFragment;
import com.lany.box.sample.fragment.MyFragment;
import com.lany.box.widget.NavigationBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends DaggerActivity implements MainContract.View {
    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.main_navigation_bar)
    NavigationBar mNavigationBar;
    @Inject
    MainPresenter mPresenter;

    private long exitTime = 0; // 第一次按退出的时间

    @NonNull
    @Override
    protected UIConfig getConfig(UIConfig config) {
        return config
                .layoutId(R.layout.activity_main)
                .hasToolbar(false)
                .keyboardEnable(false);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        List<TabItem> items = new ArrayList<>();
        items.add(new TabItem(new IndexFragment()));
        items.add(new TabItem(new HelloFragment()));
        items.add(new TabItem(new MyFragment()));
        mViewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), items));
        mNavigationBar.setupWithViewPager(mViewPager);
        mNavigationBar.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.i_music:
                    ImmersionBar.with(this).statusBarDarkFont(true).init();
                    break;
                case R.id.i_backup:
                    ImmersionBar.with(this).statusBarDarkFont(false).init();
                    break;
                case R.id.i_friends:
                    ImmersionBar.with(this).statusBarDarkFont(false).init();
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                ToastUtils.show("再按一次退出应用");
                exitTime = System.currentTimeMillis();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
