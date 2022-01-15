package com.github.lany192.box.sample.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.alibaba.android.arouter.AppRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.allen.android.lib.PermissionUtils
import com.github.lany192.arch.activity.ViewModelActivity
import com.github.lany192.box.sample.R
import com.github.lany192.box.sample.databinding.ActivitySettingsBinding
import com.github.lany192.box.sample.viewmodel.UserViewModel
import com.github.lany192.dialog.SimpleDialog
import com.github.lany192.update.config.UpdateConfig
import com.github.lany192.update.manager.UpdateManager
import com.github.lany192.utils.CacheUtils
import com.gyf.immersionbar.ImmersionBar
import com.hjq.toast.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import moe.feng.alipay.zerosdk.AlipayZeroSdk

@AndroidEntryPoint
@Route(path = "/ui/settings")
class SettingsActivity : ViewModelActivity<SettingsViewModel, ActivitySettingsBinding>() {
    private var userViewModel: UserViewModel? = null

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(true)
            .navigationBarColor(android.R.color.white)
            .navigationBarDarkIcon(true)
            .titleBar(binding.toolbar)
            .init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = getAndroidViewModel(UserViewModel::class.java)
        binding.cacheView.hint(CacheUtils.getCacheSize(this))
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.versionView.setOnClickListener { checkVersion() }
        binding.cacheView.setOnClickListener { showCacheDialog() }
        binding.permissionView.setOnClickListener { permissionSetting() }
        binding.rewardView.setOnClickListener {
            if (AlipayZeroSdk.hasInstalledAlipayClient(this)) {
                AlipayZeroSdk.startAlipayClient(this, "fkx125282bvqbijw7s6uh8f")
            } else {
                ToastUtils.show("感谢您的支持，不过你的手机还没有安装支付宝")
            }
        }
        binding.noticeView.setOnClickListener {
            AppRouter.get().browser("百度也不知道", "https://www.baidu.com")
        }
        binding.aboutView.setOnClickListener { AppRouter.get().about() }
    }

    private fun showCacheDialog() {
        val dialog = SimpleDialog()
        dialog.setMessage("确定要清除缓存吗？")
        dialog.setRightButton("确定") {
            CacheUtils.clean(this)
            binding.cacheView.hint("0KB")
            ToastUtils.show("缓存清除成功！")
        }
        dialog.setLeftButton("取消", null)
        dialog.show()
    }

    private fun permissionSetting() {
        try {
            PermissionUtils.toPermissionSetting(this)
        } catch (e: Exception) {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package", packageName, null)
            startActivity(intent)
        }
    }

    private fun checkVersion() {
        val configuration = UpdateConfig() //设置自定义的下载
            //.setHttpManager()
            //下载完成自动跳动安装页面
            .setJumpInstallPage(true) //设置对话框强制更新时进度条和文字的颜色
            //.setDialogProgressBarColor(Color.parseColor("#E743DA"))
            //设置是否显示通知栏进度
            .setShowNotification(true) //设置是否提示后台下载toast
            .setShowBgdToast(false) //设置强制更新
            .setForcedUpgrade(false) //设置下载过程的监听
            .setOnDownloadListener { max, progress ->
                val curr = (progress / max.toDouble() * 100.0).toInt()
                //            progressBar.setMax(100);
//            progressBar.setProgress(curr);
            }
        val url = "https://dldir1.qq.com/weixin/android/weixin8016android2040_arm64.apk"
        UpdateManager.getInstance(this)
            .setApkName("ESFileExplorer.apk")
            .setApkUrl(url)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setShowNewerToast(true)
            .setConfiguration(configuration)
            .setApkVersionCode(2)
            .setApkVersionName("2.1.8")
            .setApkSize("20.4")
            .setApkDescription("1.支持Android M N O P Q\n2.支持自定义下载过程\n3.支持 设备>=Android M 动态权限的申请\n4.支持通知栏进度条展示\n5.支持文字国际化") //                .setApkMD5("DC501F04BBAA458C9DC33008EFED5E7F")
            .download()
    }
}