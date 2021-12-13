package com.github.lany192.box.sample.ui.main;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.github.lany192.box.activity.BindingActivity;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.UserInfo;
import com.github.lany192.box.sample.databinding.ActivityMainBinding;
import com.github.lany192.box.sample.viewmodel.UserViewModel;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BindingActivity<ActivityMainBinding> {
    // 第一次按退出的时间
    private long mLastClickTime = 0;

    private MainViewModel mainViewModel;
    private UserViewModel userViewModel;

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .init();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = getViewModel(MainViewModel.class);
        userViewModel = getAndroidViewModel(UserViewModel.class);

        userViewModel.getUserInfo().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                ToastUtils.show("首页：" + userInfo.getName());
            }
        });

        binding.viewpager.setUserInputEnabled(false);
        binding.viewpager.setOffscreenPageLimit(4);
        binding.viewpager.setAdapter(new MainAdapter(this));
        binding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.navigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        binding.navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_main_index:
                    binding.viewpager.setCurrentItem(0, false);
                    return true;
                case R.id.menu_main_pic:
                    binding.viewpager.setCurrentItem(1, false);
                    return true;
                case R.id.menu_main_city:
                    binding.viewpager.setCurrentItem(2, false);
                    return true;
                case R.id.menu_main_my:
                    binding.viewpager.setCurrentItem(3, false);
                    return true;
                default:
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
