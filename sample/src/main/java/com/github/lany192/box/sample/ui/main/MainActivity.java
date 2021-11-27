package com.github.lany192.box.sample.ui.main;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.github.lany192.box.activity.ActivityConfig;
import com.github.lany192.box.activity.BaseActivity;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.fragment.HelloFragment;
import com.github.lany192.box.sample.ui.main.city.CityFragment;
import com.github.lany192.box.sample.ui.main.index.IndexFragment;
import com.github.lany192.box.sample.fragment.MyFragment;
import com.github.lany192.box.sample.ui.splash.SplashViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity implements MainContract.View {
    ViewPager2 mViewPager2;
    BottomNavigationView mBottomNavigationView;
    @Inject
    MainPresenter mPresenter;

    // 第一次按退出的时间
    private long mLastClickTime = 0;

    MainViewModel viewModel;

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
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewPager2 = findViewById(R.id.main_viewpager);
        mBottomNavigationView = findViewById(R.id.main_navigation_bar);
        mViewPager2.setUserInputEnabled(false);
        mViewPager2.setAdapter(new FragmentStateAdapter(this) {

            @Override
            public int getItemCount() {
                return 4;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new CityFragment();
                    case 1:
                        return new CityFragment();
                    case 2:
                        return new CityFragment();
                    case 3:
                        return new CityFragment();
                    default:
                        return new CityFragment();
                }
            }
        });
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_main_index:
                    ImmersionBar.with(this).statusBarDarkFont(true).init();
                    mViewPager2.setCurrentItem(0, false);
                    return true;
                case R.id.menu_main_pic:
                    ImmersionBar.with(this).statusBarDarkFont(true).init();
                    mViewPager2.setCurrentItem(1, false);
                    return true;
                case R.id.menu_main_city:
                    ImmersionBar.with(this).statusBarDarkFont(true).init();
                    mViewPager2.setCurrentItem(2, false);
                    return true;
                case R.id.menu_main_my:
                    ImmersionBar.with(this).statusBarDarkFont(false).init();
                    mViewPager2.setCurrentItem(3, false);
                    return true;
                default:
            }
            return false;
        });
        mPresenter.hello();
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
