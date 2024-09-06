package com.lany192.box.user.dialog

import android.graphics.Color
import com.github.lany192.dialog.BaseDialog
import com.lany192.box.user.R
import com.lany192.box.user.databinding.DialogSexBinding


/**
 * 性别选择对话框
 */
class SexDialog(private var isMan: Boolean) : BaseDialog<DialogSexBinding>() {
    private var mListener: OnSexListener? = null

    override fun bottomStyle(): Boolean {
        return true
    }

    override fun init() {
        if (isMan) {
            manClicked()
        } else {
            womanClicked()
        }
        binding.cancel.setOnClickListener { cancel() }
        binding.confirm.setOnClickListener {
            cancel()
            mListener?.onResult(isMan)
        }
        binding.boy.setOnClickListener { manClicked() }
        binding.girl.setOnClickListener { womanClicked() }
    }

    fun manClicked() {
        isMan = true
        binding.boy.setIcon(R.mipmap.sex_man_selected)
        binding.boy.setTextColor(Color.parseColor("#4a97ff"))
        binding.girl.setIcon(R.mipmap.sex_woman_normal)
        binding.girl.setTextColor(Color.parseColor("#babcc8"))
    }

    fun womanClicked() {
        isMan = false
        binding.boy.setIcon(R.mipmap.sex_man_normal)
        binding.boy.setTextColor(Color.parseColor("#babcc8"))
        binding.girl.setIcon(R.mipmap.sex_woman_selected)
        binding.girl.setTextColor(Color.parseColor("#ff889b"))
    }

    fun setOnSexListener(listener: OnSexListener?) {
        this.mListener = listener
    }

    interface OnSexListener {
        fun onResult(isMan: Boolean)
    }
}