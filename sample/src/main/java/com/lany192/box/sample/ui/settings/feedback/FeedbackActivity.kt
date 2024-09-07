package com.lany192.box.sample.ui.settings.feedback

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewBindingActivity
import com.github.lany192.arch.extension.toast
import com.github.lany192.arch.utils.BarUtils
import com.github.lany192.utils.KeyboardWatcher
import com.lany192.box.sample.databinding.ActivityFeedbackBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/ui/feedback")
class FeedbackActivity : ViewBindingActivity<ActivityFeedbackBinding>() {
    override fun initImmersionBar() {
        BarUtils.init(this).keyboardEnable(true).titleBar(binding.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.button.setOnClickListener {
            startMediaPicker({
                toast("Selected URI: $it")
            })
        }
        KeyboardWatcher(this, object : KeyboardWatcher.OnKeyboardListener {

            override fun onChanged(showKeyboard: Boolean, keyboardHeight: Int) {
                toast("键盘弹出:$showKeyboard,高度：$keyboardHeight")
            }
        })
    }
}