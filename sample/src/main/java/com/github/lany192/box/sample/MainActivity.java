package com.github.lany192.box.sample;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.github.lany192.box.activity.ActivityConfig;
import com.github.lany192.box.activity.DaggerActivity;
import com.github.lany192.box.sample.fragment.HelloFragment;
import com.github.lany192.box.sample.fragment.IndexFragment;
import com.github.lany192.box.sample.fragment.MyFragment;
import com.github.lany192.dagger.annotation.Dagger;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;

@Dagger
public class MainActivity extends DaggerActivity implements MainContract.View {
    @BindView(R.id.main_viewpager)
    ViewPager2 mViewPager2;
    @BindView(R.id.main_navigation_bar)
    BottomNavigationView mBottomNavigationView;
    @Inject
    MainPresenter mPresenter;

    private long mLastClickTime = 0; // 第一次按退出的时间

    @NonNull
    @Override
    public ActivityConfig getConfig() {
        return ActivityConfig.builder()
                .layoutId(R.layout.activity_main)
                .statusBarDarkFont(true)
                .transparentStatusBar(true)
                .build();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mViewPager2.setUserInputEnabled(false);
        mViewPager2.setAdapter(new FragmentStateAdapter(this) {

            @Override
            public int getItemCount() {
                return 3;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new IndexFragment();
                    case 1:
                        return new HelloFragment();
                    case 2:
                        return new MyFragment();
                    default:
                        return null;
                }
            }
        });
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_main_index:
                    ImmersionBar.with(this).statusBarDarkFont(true).init();
                    mViewPager2.setCurrentItem(0, false);
                    return true;
                case R.id.menu_main_pic:
                    ImmersionBar.with(this).statusBarDarkFont(true).init();
                    mViewPager2.setCurrentItem(1, false);
                    return true;
                case R.id.menu_main_my:
                    ImmersionBar.with(this).statusBarDarkFont(false).init();
                    mViewPager2.setCurrentItem(2, false);
                    return true;
            }
            return false;
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mLastClickTime) > 3000) {
                ToastUtils.show("再按一次退出" + getString(R.string.app_name));
                mLastClickTime = System.currentTimeMillis();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
