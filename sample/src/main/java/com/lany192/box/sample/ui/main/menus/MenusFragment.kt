package com.lany192.box.sample.ui.main.menus

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.VMVBFragment
import com.github.lany192.arch.utils.BarUtils
import com.lany192.box.sample.R
import com.lany192.box.sample.databinding.FragmentMenusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/menus")
class MenusFragment : VMVBFragment<MenusViewModel, FragmentMenusBinding>() {

    override fun initImmersionBar() {
        BarUtils.init(this)
            .titleBar(binding.toolbar)
            .init()
    }

    override fun init() {
        super.init()
        val items = mutableListOf<MenuItem>()
        items.add(MenuItem("root检查", R.drawable.android))
        items.add(MenuItem("当前进程名称", R.drawable.android))
        items.add(MenuItem("加法hook", R.drawable.android))
        items.add(MenuItem("是否是模拟器", R.drawable.android))
        items.add(MenuItem("html测试", R.drawable.android))
        items.add(MenuItem("获取指定渠道信息的分享包", R.drawable.android))
        items.add(MenuItem("读取分享包的渠道信息", R.drawable.android))
        items.add(MenuItem("扫描测试", R.drawable.android))
        items.add(MenuItem("转场动画", R.drawable.android))
        items.add(MenuItem("数学练习", R.drawable.android))
        items.add(MenuItem("设备id", R.drawable.android))
        items.add(MenuItem("加密解密", R.drawable.android))
        items.add(MenuItem("hello模块", R.drawable.android))
        items.add(MenuItem("browser模块", R.drawable.android))
        items.add(MenuItem("login模块", R.drawable.android))
        items.add(MenuItem("math模块", R.drawable.android))
        items.add(MenuItem("user模块", R.drawable.android))
        items.add(MenuItem("图片测试", R.drawable.android))
        binding.recyclerView.adapter = MenusAdapter(items)
    }
}