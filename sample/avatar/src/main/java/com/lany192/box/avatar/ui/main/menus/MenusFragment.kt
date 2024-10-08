package com.lany192.box.avatar.ui.main.menus

import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.ViewModelFragment
import com.github.lany192.arch.utils.BarUtils
import com.lany192.box.avatar.R
import com.lany192.box.avatar.databinding.FragmentMenusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/menus")
class MenusFragment : ViewModelFragment<MenusViewModel, FragmentMenusBinding>() {

    override fun initImmersionBar() {
        BarUtils.init(this)
            .titleBar(binding.toolbar)
            .init()
    }

    override fun init() {
        super.init()
        val items = mutableListOf<MenuItem>()
        items.add(MenuItem("root检查", R.drawable.android))
        items.add(MenuItem("当前进程名称", R.drawable.ic_empty_feed))
        items.add(MenuItem("加法hook", R.drawable.android))
        items.add(MenuItem("是否是模拟器", R.drawable.ic_empty_feed))
        items.add(MenuItem("html测试", R.drawable.ic_empty_common))
        items.add(MenuItem("获取指定渠道信息的分享包", R.drawable.ic_empty_feed))
        items.add(MenuItem("读取分享包的渠道信息", R.drawable.ic_empty_history))
        items.add(MenuItem("扫描测试", R.drawable.ic_empty_feed))
        items.add(MenuItem("转场动画", R.drawable.ic_empty_common))
        items.add(MenuItem("数据库", R.drawable.ic_empty_feed))
        items.add(MenuItem("设备id", R.drawable.ic_empty_history))
        items.add(MenuItem("加密解密", R.drawable.ic_empty_common))
        items.add(MenuItem("hello模块", R.drawable.ic_empty_favourites))
        items.add(MenuItem("browser模块", R.drawable.ic_empty_feed))
        items.add(MenuItem("login模块", R.drawable.ic_empty_local))
        items.add(MenuItem("math模块", R.drawable.ic_empty_history))
        items.add(MenuItem("user模块", R.drawable.android))
        items.add(MenuItem("图片测试", R.drawable.ic_empty_feed))
        items.add(MenuItem("设置", R.drawable.android))
        items.add(MenuItem("高斯模糊", R.drawable.android))
        items.add(MenuItem("输入弹窗", R.drawable.ic_empty_feed))
        items.add(MenuItem("引导页示例", R.drawable.ic_empty_feed))
        binding.recyclerView.adapter = MenusAdapter(items)
    }
}