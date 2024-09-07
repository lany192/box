package com.lany192.box.sample.dialog

import com.github.lany192.dialog.BaseDialog
import com.lany192.box.sample.databinding.DialogReplyBinding

class ReplyDialog : BaseDialog<DialogReplyBinding>() {
    override fun bottomStyle(): Boolean {
        return true
    }

    override fun init() {
    }
}
