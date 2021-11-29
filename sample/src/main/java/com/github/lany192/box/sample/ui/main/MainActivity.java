package com.github.lany192.box.sample.ui.main;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.github.lany192.box.binding.BindingActivity;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.databinding.ActivityMainBinding;
import com.github.lany192.box.sample.ui.main.city.CityFragment;
import com.github.lany192.box.sample.ui.main.discover.DiscoverFragment;
import com.github.lany192.box.sample.ui.main.index.IndexFragment;
import com.github.lany192.box.sample.ui.main.my.MyFragment;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BindingActivity<ActivityMainBinding> {
    // 第一次按退出的时间
    private long mLastClickTime = 0;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).transparentStatusBar().init();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.viewpager.setUserInputEnabled(false);
//        binding.viewpager.setOffscreenPageLimit(4);
        binding.viewpager.setAdapter(new FragmentStateAdapter(this) {

            @Override
            public int getItemCount() {
                return 4;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new IndexFragment();
                    case 1:
                        return new DiscoverFragment();
                    case 2:
                        return new CityFragment();
                    case 3:
                        return new MyFragment();
                    default:
                        return new IndexFragment();
                }
            }
        });
        binding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.navigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        binding.navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_main_index:
//                    ImmersionBar.with(this).statusBarDarkFont(true).init();
                    binding.viewpager.setCurrentItem(0, false);
                    return true;
                case R.id.menu_main_pic:
//                    ImmersionBar.with(this).statusBarDarkFont(true).init();
                    binding.viewpager.setCurrentItem(1, false);
                    return true;
                case R.id.menu_main_city:
//                    ImmersionBar.with(this).statusBarDarkFont(true).init();
                    binding.viewpager.setCurrentItem(2, false);
                    return true;
                case R.id.menu_main_my:
//                    ImmersionBar.with(this).statusBarDarkFont(false).init();
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
