package com.lany192.box.user.ui

import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewBindingActivity
import com.github.lany192.arch.utils.BarUtils
import com.github.lany192.arch.utils.FileUtils
import com.github.lany192.dialog.BirthdayDialog
import com.github.lany192.extension.load
import com.github.lany192.extension.log
import com.github.lany192.extension.toast
import com.lany192.box.user.databinding.ActivityUserBinding
import com.lany192.box.user.dialog.SexDialog
import com.lany192.box.user.ui.nickname.NicknameRouter
import com.lany192.box.user.ui.signature.SignatureRouter
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/user/main")
class UserInfoActivity : ViewBindingActivity<ActivityUserBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle("")
        binding.nickname.setOnClickListener { NicknameRouter.start() }
        binding.signature.setOnClickListener { SignatureRouter.start() }
        binding.sexView.setOnClickListener { SexDialog(false).show() }
        binding.avatar.load("http://pic.imeitou.com/uploads/allimg/221021/8-221021094504.jpg")
        binding.avatar.setOnClickListener {
            startMediaPicker(
                { uri ->
                    if (uri != null) {
                        binding.avatar.load(uri)
                        val cropUri = FileUtils.getTempPicUri(this@UserInfoActivity)
                        log("切图保存地址：$cropUri")
                        val cropIntent = UCrop.of(uri, cropUri)
                            .withAspectRatio(1f, 1f)
                            .withMaxResultSize(300, 300)
                            .getIntent(this@UserInfoActivity)
                        startActivityForResult(cropIntent) { result ->
                            result?.let {
                                if (result.resultCode == RESULT_OK) {
                                    // 处理成功的结果
                                    val resultUri = UCrop.getOutput(result.data!!)
                                    toast("Selected URI: $resultUri")
                                } else {
                                    // 处理取消或失败的情况
                                    toast("处理取消或失败的情况")
                                }
                            }
                        }
                    } else {
                        toast("No media selected")
                    }
                },
                PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    .build()
            )
        }
        binding.birthday.setOnClickListener {
            val dialog = BirthdayDialog(2001, 1, 1)
            dialog.setOnBirthdayListener(object : BirthdayDialog.OnBirthdayListener {
                override fun onResult(year: Int, month: Int, day: Int) {
                    toast("你选择的是：$year-$month-$day")
                }
            })
            dialog.show()
        }
    }
}