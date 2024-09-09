package com.github.lany192.dialog

import android.text.TextUtils
import android.text.method.MovementMethod
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import com.github.lany192.dialog.databinding.DialogInputBinding
import com.github.lany192.interfaces.OnSimpleListener
import com.github.lany192.utils.KeyboardWatcher
import com.github.lany192.utils.KeyboardWatcher.OnKeyboardListener
import com.hjq.toast.Toaster


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

    override fun bottomStyle(): Boolean {
        return true
    }

//    override fun getDialogHeight(): Int {
//        return WindowManager.LayoutParams.MATCH_PARENT
//    }

    override fun getTheme(): Int {
        return R.style.InputDialogTheme
    }

    override fun init() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        dialog?.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        if (TextUtils.isEmpty(title)) {
            binding.title.visibility = View.GONE
        } else {
            binding.title.text = title
            binding.title.textSize = titleSize
            binding.title.visibility = View.VISIBLE
            binding.title.setTextColorId(titleColor)
        }
        KeyboardWatcher(requireActivity()) { showKeyboard, keyboardHeight ->
            Toaster.show("键盘板状态：$showKeyboard, 高度：$keyboardHeight")
            binding.panel.layoutParams.height = keyboardHeight
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }
}