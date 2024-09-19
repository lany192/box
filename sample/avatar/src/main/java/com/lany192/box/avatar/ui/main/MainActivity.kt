package com.lany192.box.avatar.ui.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewModelActivity
import com.github.lany192.arch.tab.TabAdapter
import com.github.lany192.arch.tab.TabItem
import com.github.lany192.log.LogUtils
import com.github.lany192.utils.ContextUtils
import com.hjq.toast.Toaster
import com.lany192.box.avatar.R
import com.lany192.box.avatar.databinding.ActivityMainBinding
import com.lany192.box.avatar.ui.main.discover.DiscoverBuilder
import com.lany192.box.avatar.ui.main.index.IndexBuilder
import com.lany192.box.avatar.ui.main.menus.MenusBuilder
import com.lany192.box.avatar.ui.main.message.MessageBuilder
import com.lany192.box.avatar.ui.main.my.MyBuilder
import dagger.hilt.android.AndroidEntryPoint

@Route(path = "/ui/main")
@AndroidEntryPoint
class MainActivity : ViewModelActivity<MainViewModel, ActivityMainBinding>() {
    // 第一次按退出的时间
    private var mLastClickTime: Long = 0

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toaster.show("测试：" + ContextUtils.isDebug())
        val items = mutableListOf<TabItem>()
        items.add(TabItem("测试", MenusBuilder.getFragment()))
        items.add(TabItem("首页", IndexBuilder.getFragment()))
        items.add(TabItem("发现", DiscoverBuilder.getFragment()))
        items.add(TabItem("消息", MessageBuilder.getFragment()))
        items.add(TabItem("我的", MyBuilder.getFragment()))
        binding.viewpager.isUserInputEnabled = false
        binding.viewpager.offscreenPageLimit = items.size
        binding.viewpager.adapter = TabAdapter(this, items)
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.navigationView.menu.getItem(position).isChecked = true
            }
        })
        //去掉选中图标的背景色
        binding.navigationView.isItemActiveIndicatorEnabled = false
        binding.navigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_main_index -> {
                    binding.viewpager.setCurrentItem(0, false)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_main_menus -> {
                    binding.viewpager.setCurrentItem(1, false)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_main_pic -> {
                    binding.viewpager.setCurrentItem(2, false)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_main_city -> {
                    binding.viewpager.setCurrentItem(3, false)
                    return@setOnItemSelectedListener true
                }

                R.id.menu_main_my -> {
                    binding.viewpager.setCurrentItem(4, false)
                    return@setOnItemSelectedListener true
                }

                else -> {}
            }
            false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mLastClickTime > 3000) {
                Toaster.show("再按一次退出" + getString(R.string.app_name))
                mLastClickTime = System.currentTimeMillis()
                return true;
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        LogUtils.close()
        super.onDestroy()
    }
}