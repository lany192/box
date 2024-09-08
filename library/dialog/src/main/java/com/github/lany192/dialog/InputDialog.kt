package com.github.lany192.dialog

import android.text.TextUtils
import android.text.method.MovementMethod
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorRes
import com.github.lany192.dialog.databinding.DialogInputBinding
import com.github.lany192.interfaces.OnSimpleListener

class InputDialog : BaseDialog<DialogInputBinding>() {
    @ColorRes
    var titleColor = R.color.text_1level
    var titleSize = 16f
    var title: CharSequence? = null

    @ColorRes
    var messageColor = R.color.text_3level
    var message: CharSequence? = null
    var messageSize = 14f
    var movementMethod: MovementMethod? = null

    @ColorRes
    var rightTextColor = 0
    var rightButton: CharSequence? = null
    var rightClickListener: OnSimpleListener? = null

    @ColorRes
    var leftTextColor = 0
    var leftButton: CharSequence? = null
    var leftClickListener: OnSimpleListener? = null

    override fun bottomStyle(): Boolean {
        return true
    }

    override fun getTheme(): Int {
        return R.style.InputDialogTheme
    }

    override fun init() {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        if (TextUtils.isEmpty(title)) {
            binding.title.visibility = View.GONE
        } else {
            binding.title.text = title
            binding.title.textSize = titleSize
            binding.title.visibility = View.VISIBLE
            binding.title.setTextColorId(titleColor)
        }
    }

}