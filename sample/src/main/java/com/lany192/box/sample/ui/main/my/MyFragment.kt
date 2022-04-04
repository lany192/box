package com.lany192.box.sample.ui.main.my

import com.alibaba.android.arouter.AppRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.ModelFragment
import com.github.lany192.dialog.SimpleDialog
import com.gyf.immersionbar.ImmersionBar
import com.lany192.box.sample.R
import com.lany192.box.sample.data.bean.UserInfo
import com.lany192.box.sample.databinding.FragmentMyBinding
import com.lany192.box.sample.ui.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/page/my")
class MyFragment : ModelFragment<MyViewModel, FragmentMyBinding>() {
    private lateinit var userViewModel: UserViewModel

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false)
            .navigationBarColor(android.R.color.holo_green_light)
            .init()
    }

    override fun init() {
        super.init()
        userViewModel = getAndroidViewModel(UserViewModel::class.java)
        userViewModel.userInfo.observe(this) { userInfo: UserInfo -> binding.testView.hint(userInfo.name) }
        binding.downloadView.setOnClickListener { AppRouter.startDownload() }
        binding.dialogView.setOnClickListener { showDialog() }
        binding.loginView.setOnClickListener { AppRouter.startLogin() }
        binding.settingsView.setOnClickListener { AppRouter.startSettings() }
        binding.helloView.setOnClickListener { AppRouter.startHello() }
        binding.testView.setOnClickListener {
            userViewModel.setName(
                "我是张三"
            )
        }
        binding.image.load(R.mipmap.a)
    }

    private fun showDialog() {
        val dialog = SimpleDialog()
        dialog.setTitle("提示")
        dialog.setMessage("猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁")
        dialog.setRightButton("确定") { }
        dialog.setLeftButton("取消") { }
        dialog.show(this)
    }
}