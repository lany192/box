package com.lany192.box.demo.ui.main.my

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.ViewModelFragment
import com.github.lany192.arch.utils.BarUtils
import com.github.lany192.dialog.BirthdayDialog
import com.github.lany192.dialog.MenuDialog
import com.github.lany192.dialog.SimpleDialog
import com.github.lany192.extension.load
import com.github.lany192.extension.toast
import com.github.lany192.interfaces.OnSimpleListener
import com.github.lany192.toolkit.BoxToolKit
import com.lany192.box.demo.R
import com.lany192.box.network.data.bean.UserInfo
import com.lany192.box.router.provider.HelloProvider
import com.lany192.box.router.provider.LoginProvider
import com.lany192.box.demo.databinding.FragmentMyBinding
import com.lany192.box.demo.ui.download.DownloadRouter
import com.lany192.box.demo.ui.goods.GoodsRouter
import com.lany192.box.demo.ui.settings.SettingsRouter
import com.lany192.box.demo.ui.user.UserViewModel
import com.lany192.box.demo.ui.video.VideoRouter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@Route(path = "/page/my")
class MyFragment : ViewModelFragment<MyViewModel, FragmentMyBinding>() {

    @Autowired
    lateinit var loginProvider: LoginProvider

    @Autowired
    lateinit var helloProvider: HelloProvider

    private lateinit var userViewModel: UserViewModel

    override fun initImmersionBar() {
        BarUtils.init(this).init()
    }

    override fun init() {
        super.init()
        userViewModel = getAndroidViewModel(UserViewModel::class.java)
        userViewModel.userInfo.observe(this) { userInfo: UserInfo -> binding.testView.hint(userInfo.name) }
        binding.downloadView.setOnClickListener { DownloadRouter.start() }
        binding.dialogView.setOnClickListener { showDialog() }
        binding.loginView.setOnClickListener {
            loginProvider.startLogin()
        }
        binding.settingsView.setOnClickListener { SettingsRouter.start() }
        binding.helloView.setOnClickListener {
            helloProvider.startHello()
        }
        binding.goods.setOnClickListener { GoodsRouter.start() }
        binding.dialog2View.setOnClickListener { showDialog2() }
        binding.birthday.setOnClickListener {
            val dialog = BirthdayDialog(2001, 1, 1)
            dialog.setOnBirthdayListener(object :
                BirthdayDialog.OnBirthdayListener {
                override fun onResult(year: Int, month: Int, day: Int) {
                    toast("你选择的是：$year-$month-$day")
                }
            })
            dialog.show()
        }
        binding.testView.setOnClickListener {
            userViewModel.setName("我是张三")
        }
        binding.image.load(R.mipmap.a)
        binding.gif1.load("https://img.zcool.cn/community/01ef345bcd8977a8012099c82483d3.gif")
        binding.gif2.load("https://img.zcool.cn/community/01b8355bcd8978a801213deaae9e9c.gif")
        binding.checkView.isChecked = true
        binding.imagePicker.setOnClickListener {

        }
        binding.photoPicker.setOnClickListener {

        }
        binding.video.setOnClickListener { VideoRouter.start() }
        binding.test1.setOnClickListener {
            helloProvider.startHello()
        }
        binding.test2.setOnClickListener {
            loginProvider.startLogin()
        }
        binding.roundLayout.setRadius(30f)
        binding.test3.text = "当前进程：" + BoxToolKit.getCurrentProcess()
    }

    private fun showDialog2() {
        val menus = mutableListOf<String>()
        for (i in 0..4) {
            menus.add("test$i")
        }
        val dialog = MenuDialog()
        dialog.setTitle("我是标题")
        dialog.setItems(menus)
        dialog.show()
    }

    private fun showDialog() {
        SimpleDialog().apply {
            title = "我是标题"
            message = "我是内容"
            rightButton = "确定"
            leftButton = "取消"
            rightClickListener = OnSimpleListener { toast("点击了确定") }
        }.show()
    }
}