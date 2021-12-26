package com.github.lany192.box.sample.ui.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.AppRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewModelActivity
import com.github.lany192.arch.tab.TabAdapter
import com.github.lany192.arch.tab.TabItem
import com.github.lany192.box.sample.R
import com.github.lany192.box.sample.data.bean.UserInfo
import com.github.lany192.box.sample.databinding.ActivityMainBinding
import com.github.lany192.box.sample.viewmodel.UserViewModel
import com.gyf.immersionbar.ImmersionBar
import com.hjq.toast.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@Route(path = "/ui/main")
@AndroidEntryPoint
class MainActivity : ViewModelActivity<MainViewModel, ActivityMainBinding>() {
    // 第一次按退出的时间
    private var mLastClickTime: Long = 0
    private lateinit var userViewModel: UserViewModel

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = getAndroidViewModel(UserViewModel::class.java)
        userViewModel.userInfo.observe(
            this,
            { userInfo: UserInfo -> ToastUtils.show("首页：" + userInfo.name) })
        val items: MutableList<TabItem> = ArrayList()
        items.add(TabItem("首页", AppRouter.get().index))
        items.add(TabItem("发现", AppRouter.get().discover))
        items.add(TabItem("消息", AppRouter.get().message))
        items.add(TabItem("我的", AppRouter.get().my))
        binding.viewpager.isUserInputEnabled = false
        binding.viewpager.offscreenPageLimit = items.size
        binding.viewpager.adapter = TabAdapter(this, items)
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.navigationView.menu.getItem(position).isChecked = true
            }
        })
        binding.navigationView.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_main_index -> {
                    binding.viewpager.setCurrentItem(0, false)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_pic -> {
                    binding.viewpager.setCurrentItem(1, false)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_city -> {
                    binding.viewpager.setCurrentItem(2, false)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_my -> {
                    binding.viewpager.setCurrentItem(3, false)
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
                ToastUtils.show("再按一次退出" + getString(R.string.app_name))
                mLastClickTime = System.currentTimeMillis()
                return false
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}