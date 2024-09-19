package com.lany192.box.avatar.ui.main.menus

import android.util.Log
import com.github.lany192.arch.adapter.BindingAdapter
import com.github.lany192.arch.utils.DeviceId
import com.github.lany192.dialog.InputDialog
import com.github.lany192.dialog.SimpleDialog
import com.github.lany192.extension.toast
import com.github.lany192.toolkit.BoxToolKit
import com.github.lany192.utils.ChannelUtils
import com.lany192.box.browser.ui.BrowserRouter
import com.lany192.box.hello.ui.HelloRouter
import com.lany192.box.login.ui.LoginRouter
import com.lany192.box.math.ui.MathRouter
import com.lany192.box.avatar.databinding.ItemMenuBinding
import com.lany192.box.avatar.ui.blur.BlurRouter
import com.lany192.box.avatar.ui.database.DatabaseRouter
import com.lany192.box.avatar.ui.encrypt.EncryptRouter
import com.lany192.box.avatar.ui.guide.GuideRouter
import com.lany192.box.avatar.ui.html.HtmlRouter
import com.lany192.box.avatar.ui.image.ImageRouter
import com.lany192.box.avatar.ui.settings.SettingsRouter
import com.lany192.box.avatar.ui.transformation.TransformationRouter
import com.lany192.box.avatar.ui.zxing.ZxingRouter
import com.lany192.box.user.ui.UserInfoRouter
import com.scottyab.rootbeer.RootBeer

class MenusAdapter(data: List<MenuItem>) : BindingAdapter<MenuItem, ItemMenuBinding>(data) {
    override fun convert(binding: ItemMenuBinding, item: MenuItem, position: Int) {
        binding.name.text = item.name
        binding.name.setIcon(item.resId)
    }

    override fun onItemClicked(binding: ItemMenuBinding, item: MenuItem, position: Int) {
        if (position == 0) {
            test1()
        } else if (position == 1) {
            test2()
        } else if (position == 2) {
            test3()
        } else if (position == 3) {
            toast("是否是模拟器：" + BoxToolKit.isEmulator())
        } else if (position == 4) {
            HtmlRouter.start()
        } else if (position == 5) {
            val channelApkPath = ChannelUtils.getChannelApkPath(context, "hello")
            Log.i("测试", "apkPath: $channelApkPath")
            toast(channelApkPath)
        } else if (position == 6) {
            val path =
                ChannelUtils.getChannelApkPath(context, "hello_" + System.currentTimeMillis())
            val channel = ChannelUtils.getChannelByPath(path)
            toast("渠道信息：$channel")
        } else if (position == 7) {
            ZxingRouter.start()
        } else if (position == 8) {
            TransformationRouter.start()
        } else if (position == 9) {
            DatabaseRouter.start()
        } else if (position == 10) {
            toast(DeviceId.get().deviceId)
        } else if (position == 11) {
            EncryptRouter.start()
        } else if (position == 12) {
            HelloRouter.start()
        } else if (position == 13) {
            BrowserRouter.start("测试", "https//www.baidu.com")
        } else if (position == 14) {
            LoginRouter.start()
        } else if (position == 15) {
            MathRouter.start()
        } else if (position == 16) {
            UserInfoRouter.start()
        } else if (position == 17) {
            ImageRouter.start()
        } else if (position == 18) {
            SettingsRouter.start()
        } else if (position == 19) {
            BlurRouter.start()
        } else if (position == 20) {
            InputDialog().apply {
                title = "输入框"
                message = "请输入内容"
            }.show()
        } else if (position == 21) {
            GuideRouter.start()
        }
    }

    private fun test1() {
        if (RootBeer(context).isRooted) {
            val dialog = SimpleDialog()
            dialog.title = "检测到root"
            dialog.message =
                "检测到当前手机已经被root，存在数据不安全情况。为保证良好的用户体验，请选择在非root手机上使用本软件。"
            dialog.rightButton = "取消"
            dialog.show()
        } else {
            toast("未发现root")
        }
    }

    private fun test2() {
        toast(BoxToolKit.getCurrentProcess())
    }

    private fun test3() {
        toast("计算结果：" + hello(1, 2))
    }

    private fun test4() {
        toast("点击了1")
    }

    private fun test5() {
        toast("点击了")
    }

    private fun hello(x: Int, y: Int): Int {
        Log.d("测试", x.toString() + "+" + y + "==" + (x + y))
        return x + y
    }
}
