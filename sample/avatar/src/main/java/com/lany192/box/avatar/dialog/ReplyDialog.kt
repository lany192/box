package com.lany192.box.avatar.dialog

import com.github.lany192.dialog.BaseDialog
import com.github.lany192.extension.toast
import com.github.lany192.utils.KeyboardWatcher
import com.lany192.box.avatar.databinding.DialogReplyBinding

class ReplyDialog : BaseDialog<DialogReplyBinding>() {
    override fun bottomStyle(): Boolean {
        return true
    }

    override fun init() {
        KeyboardWatcher(requireActivity(), object : KeyboardWatcher.OnKeyboardListener {
            override fun onChanged(showKeyboard: Boolean, keyboardHeight: Int) {
                toast("键盘高度：$keyboardHeight, 是否显示：$showKeyboard")
            }
        })
    }
}
