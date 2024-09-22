package com.lany192.box.sample.dialog

import com.github.lany192.dialog.BaseDialog
import com.github.lany192.extension.toast
import com.github.lany192.utils.KeyboardWatcher
import com.lany192.box.demo.databinding.DialogReplyBinding

class ReplyDialog : BaseDialog<DialogReplyBinding>() {
    override fun bottomStyle(): Boolean {
        return true
    }

    override fun init() {
        KeyboardWatcher(requireActivity(), object : KeyboardWatcher.OnKeyboardListener {
            override fun onChanged(showKeyboard: Boolean, keyboardHeight: Int, floatMode: Boolean) {
                toast("键盘弹出:$showKeyboard,高度：$keyboardHeight,悬浮模式：$floatMode")
            }
        })
    }
}
