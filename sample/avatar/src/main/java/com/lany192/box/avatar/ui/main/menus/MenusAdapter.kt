package com.lany192.box.avatar.ui.main.menus

import android.util.Log
import com.github.lany192.arch.adapter.BindingAdapter
import com.github.lany192.arch.utils.DeviceId
import com.github.lany192.dialog.InputDialog
import com.github.lany192.dialog.SimpleDialog
import com.github.lany192.extension.toast
import com.github.lany192.toolkit.BoxToolKit
import com.github.lany192.utils.ChannelUtils
import com.lany192.box.avatar.databinding.ItemMenuBinding
import com.lany192.box.avatar.ui.settings.SettingsRouter
import com.lany192.box.browser.ui.BrowserRouter
import com.lany192.box.hello.ui.HelloRouter
import com.lany192.box.login.ui.LoginRouter
import com.lany192.box.math.ui.MathRouter
import com.lany192.box.user.ui.UserInfoRouter
import com.scottyab.rootbeer.RootBeer

class MenusAdapter(data: List<MenuItem>) : BindingAdapter<MenuItem, ItemMenuBinding>(data) {
    override fun convert(binding: ItemMenuBinding, item: MenuItem, position: Int) {
        binding.name.text = item.name
        binding.name.setIcon(item.resId)
    }

    override fun onItemClicked(binding: ItemMenuBinding, item: MenuItem, position: Int) {
        when (position) {
            0 -> {
                test1()
            }
            1 -> {
                test2()
            }
            2 -> {
                test3()
            }
            3 -> {
                toast("是否是模拟器：" + BoxToolKit.isEmulator())
            }
            4 -> {
                toast("是否是模拟器：" + BoxToolKit.isEmulator())
            }
            5 -> {
                val channelApkPath = ChannelUtils.getChannelApkPath(context, "hello")
                Log.i("测试", "apkPath: $channelApkPath")
                toast(channelApkPath)
            }
            6 -> {
                val path =
                    ChannelUtils.getChannelApkPath(context, "hello_" + System.currentTimeMillis())
                val channel = ChannelUtils.getChannelByPath(path)
                toast("渠道信息：$channel")
            }
            7 -> {
                toast("是否是模拟器：" + BoxToolKit.isEmulator())
            }
            8 -> {
                toast("是否是模拟器：" + BoxToolKit.isEmulator())
            }
            9 -> {
                toast("是否是模拟器：" + BoxToolKit.isEmulator())
            }
            10 -> {
                toast(DeviceId.get().deviceId)
            }
            11 -> {
                toast("是否是模拟器：" + BoxToolKit.isEmulator())
            }
            12 -> {
                HelloRouter.start()
            }
            13 -> {
                BrowserRouter.start("测试", "https//www.baidu.com")
            }
            14 -> {
                LoginRouter.start()
            }
            15 -> {
                MathRouter.start()
            }
            16 -> {
                UserInfoRouter.start()
            }
            17 -> {
                toast("是否是模拟器：" + BoxToolKit.isEmulator())
            }
            18 -> {
                SettingsRouter.start()
            }
            19 -> {
                toast("是否是模拟器：" + BoxToolKit.isEmulator())
            }
            20 -> {
                InputDialog().apply {
                    title = "输入框"
                    message = "请输入内容"
                }.show()
            }
            21 -> {
                toast("是否是模拟器：" + BoxToolKit.isEmulator())
            }
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

    private fun hello(x: Int, y: Int): Int {
        Log.d("测试", x.toString() + "+" + y + "==" + (x + y))
        return x + y
    }
}
