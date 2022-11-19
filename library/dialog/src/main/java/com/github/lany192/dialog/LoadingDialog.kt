package com.github.lany192.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.WindowManager
import androidx.annotation.StringRes
import com.github.lany192.dialog.databinding.DialogLoadingBinding
import com.github.lany192.utils.ContextUtils

/**
 * 加载对话框
 */
class LoadingDialog : BaseDialog<DialogLoadingBinding>() {

    private var mMessage: CharSequence? = null

    override fun init() {
        if (!TextUtils.isEmpty(mMessage)) {
            binding.message.text = mMessage
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            val layoutParams = it.attributes
            layoutParams.dimAmount = 0.0f //设置背景全透明
            it.attributes = layoutParams
        }
    }

    fun setMessage(message: CharSequence?) {
        mMessage = message
    }

    fun setMessage(@StringRes resId: Int) {
        mMessage = ContextUtils.getContext().getString(resId)
    }
}