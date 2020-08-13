package com.github.lany192.box.mvp

import androidx.lifecycle.LifecycleOwner

interface BaseView : LifecycleOwner {
    fun showEmpty()
    fun showEmpty(msg: String?)
    fun showContent()
    fun showNoWifi()
    fun showError()
    fun showError(msg: String?)
    fun showLoading()
    fun showLoadingDialog()
    fun showLoadingDialog(message: CharSequence?)
    fun cancelLoadingDialog()
}