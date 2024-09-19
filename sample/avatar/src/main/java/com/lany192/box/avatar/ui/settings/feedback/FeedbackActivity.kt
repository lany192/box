package com.lany192.box.avatar.ui.settings.feedback

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewBindingActivity
import com.github.lany192.extension.toast
import com.github.lany192.utils.KeyboardWatcher
import com.lany192.box.avatar.databinding.ActivityFeedbackBinding
import com.lany192.box.avatar.dialog.ReplyDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/ui/feedback")
class FeedbackActivity : ViewBindingActivity<ActivityFeedbackBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.button.setOnClickListener {
            ReplyDialog().show()
        }
        KeyboardWatcher(this, object : KeyboardWatcher.OnKeyboardListener {

            override fun onChanged(showKeyboard: Boolean, keyboardHeight: Int) {
                toast("键盘弹出:$showKeyboard,高度：$keyboardHeight")
            }
        })
    }
}