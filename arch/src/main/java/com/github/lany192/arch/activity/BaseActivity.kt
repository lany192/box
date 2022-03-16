package com.github.lany192.arch.activity

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.elvishew.xlog.XLog
import com.github.lany192.dialog.LoadingDialog
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.elvishew.xlog.Logger
import com.github.lany192.arch.network.NetworkHelper
import kotlin.jvm.JvmOverloads
import com.github.lany192.arch.R
import com.gyf.immersionbar.ImmersionBar

abstract class BaseActivity : AppCompatActivity() {
    @JvmField
    val log: Logger.Builder = XLog.tag(javaClass.simpleName)

    private var loadingDialog: LoadingDialog? = null
    
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(NetworkHelper.getInstance())
        findViewById<View>(R.id.back)?.setOnClickListener { finish() }
    }

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        val viewModel = ViewModelProvider(this)[modelClass]
        if (viewModel is LifecycleObserver) {
            lifecycle.addObserver((viewModel as LifecycleObserver))
        }
        return viewModel
    }

    fun <T : ViewModel> getAndroidViewModel(modelClass: Class<T>): T {
        return ViewModelProvider((applicationContext as ViewModelStoreOwner))[modelClass]
    }

    @JvmOverloads
    fun showLoadingDialog(message: CharSequence? = getString(R.string.loading)) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog()
        }
        loadingDialog!!.setMessage(message)
        loadingDialog!!.show(this)
    }

    fun cancelLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog!!.cancel()
            loadingDialog = null
        }
    }

    @CallSuper
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (immersionBarEnabled()) {
            initImmersionBar()
        }
    }

    @CallSuper
    public override fun onResume() {
        super.onResume()
        if (immersionBarEnabled()) {
            initImmersionBar()
        }
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    fun immersionBarEnabled(): Boolean {
        return true
    }

    open fun initImmersionBar() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(true)
            .navigationBarColor(android.R.color.white)
            .navigationBarDarkIcon(true)
            .init()
    }
}