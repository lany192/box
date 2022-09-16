package com.lany192.box.sample.ui.main.my

import android.content.pm.ActivityInfo
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.SampleRouter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.fragment.VMVBFragment
import com.github.lany192.arch.utils.BarUtils
import com.github.lany192.dialog.BirthdayDialog
import com.github.lany192.dialog.MenuDialog
import com.github.lany192.dialog.SimpleDialog
import com.github.lany192.extensions.load
import com.github.lany192.utils.DensityUtils
import com.hjq.toast.ToastUtils
import com.lany192.box.sample.BuildConfig
import com.lany192.box.sample.R
import com.lany192.box.sample.data.bean.UserInfo
import com.lany192.box.sample.databinding.FragmentMyBinding
import com.lany192.box.sample.ui.user.UserViewModel
import com.zhihu.matisse.GlideEngine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
@Route(path = "/page/my")
class MyFragment : VMVBFragment<MyViewModel, FragmentMyBinding>() {
    private lateinit var userViewModel: UserViewModel

    override fun initImmersionBar() {
        BarUtils.init(this).init()
    }

    override fun init() {
        super.init()
        userViewModel = getAndroidViewModel(UserViewModel::class.java)
        userViewModel.userInfo.observe(this) { userInfo: UserInfo -> binding.testView.hint(userInfo.name) }
        binding.downloadView.setOnClickListener { SampleRouter.startDownload() }
        binding.dialogView.setOnClickListener { showDialog() }
        binding.loginView.setOnClickListener { SampleRouter.startLogin() }
        binding.settingsView.setOnClickListener { SampleRouter.startSettings() }
        binding.helloView.setOnClickListener { SampleRouter.startHello() }
        binding.dialog2View.setOnClickListener { showDialog2() }
        binding.birthday.setOnClickListener {
            BirthdayDialog(LocalDate.of(2001, 1, 2)).show()
        }
        binding.testView.setOnClickListener {
            userViewModel.setName("我是张三")
        }
        binding.image.load(R.mipmap.a)

        binding.checkView.setOnCheckChangeListener { ToastUtils.show(it) }
        binding.checkView.isChecked = true
        binding.imagePicker.setOnClickListener {
            Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.WEBP), false)
                .countable(true).capture(true).captureStrategy(CaptureStrategy(true,
                    BuildConfig.APPLICATION_ID + ".fileprovider",
                    "images")).gridExpectedSize(DensityUtils.dp2px(120f))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).thumbnailScale(0.85f)
                .imageEngine(GlideEngine()).showSingleMediaType(true).originalEnable(true)
                .maxOriginalSize(10).autoHideToolbarOnSingleTap(true)
                .forResult { result: ActivityResult ->
                    if (result.resultCode == AppCompatActivity.RESULT_OK && result.data != null) {
                        val paths = Matisse.obtainPathResult(result.data)
                        ToastUtils.show(paths)
                    }
                }
        }
    }

    private fun showDialog2() {
        val menus = mutableListOf<String>()
        for (i in 0..4) {
            menus.add("test$i")
        }
        val dialog = MenuDialog()
        dialog.setItems(menus)
        dialog.show()
    }

    private fun showDialog() {
        val dialog = SimpleDialog()
        dialog.setTitle("提示")
        dialog.setMessage("猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁猜猜我是谁")
        dialog.setRightButton("确定") { }
        dialog.setLeftButton("取消") { }
        dialog.show()
    }
}