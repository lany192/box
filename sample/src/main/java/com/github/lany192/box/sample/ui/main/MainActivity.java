package com.github.lany192.box.sample.ui.main;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BindingActivity;
import com.github.lany192.arch.tab.TabAdapter;
import com.github.lany192.arch.tab.TabItem;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.data.bean.UserInfo;
import com.github.lany192.box.sample.databinding.ActivityMainBinding;
import com.github.lany192.box.sample.ui.main.discover.DiscoverFragment;
import com.github.lany192.box.sample.ui.main.index.IndexFragment;
import com.github.lany192.box.sample.ui.main.message.MessageFragment;
import com.github.lany192.box.sample.ui.main.my.MyFragment;
import com.github.lany192.box.sample.viewmodel.UserViewModel;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@Route(path = "/ui/main")
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

        List<TabItem> items = new ArrayList<>();
        items.add(new TabItem("首页", new IndexFragment()));
        items.add(new TabItem("发现", new DiscoverFragment()));
        items.add(new TabItem("消息", new MessageFragment()));
        items.add(new TabItem("我的", new MyFragment()));

        binding.viewpager.setUserInputEnabled(false);
        binding.viewpager.setOffscreenPageLimit(items.size());
        binding.viewpager.setAdapter(new TabAdapter(this, items));
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
