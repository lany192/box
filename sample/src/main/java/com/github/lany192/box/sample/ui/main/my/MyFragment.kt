package com.github.lany192.box.sample.ui.main.my

import com.alibaba.android.arouter.AppRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.BindingFragment
import com.github.lany192.box.sample.R
import com.github.lany192.box.sample.data.bean.UserInfo
import com.github.lany192.box.sample.databinding.FragmentMyBinding
import com.github.lany192.box.sample.viewmodel.UserViewModel
import com.github.lany192.dialog.SimpleDialog
import com.github.lany192.extensions.load
import com.github.lany192.extensions.setGone
import com.gyf.immersionbar.ImmersionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/fragment/my")
class MyFragment : BindingFragment<FragmentMyBinding>() {
    private lateinit var viewModel: MyViewModel
    private lateinit var userViewModel: UserViewModel

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(false)
            .navigationBarColor(android.R.color.holo_green_light)
            .init()
    }

    override fun initView() {
        viewModel = getFragmentViewModel(MyViewModel::class.java)
        userViewModel = getAndroidViewModel(UserViewModel::class.java)
        userViewModel.userInfo.observe(
            this,
            { userInfo: UserInfo -> binding.testView.hint(userInfo.name) })
        binding.downloadView.setOnClickListener { AppRouter.get().download() }
        binding.dialogView.setOnClickListener { showDialog() }
        binding.loginView.setOnClickListener { AppRouter.get().login() }
        binding.settingsView.setOnClickListener { AppRouter.get().settings() }
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