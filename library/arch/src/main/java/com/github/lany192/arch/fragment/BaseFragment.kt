package com.github.lany192.arch.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.github.lany192.arch.R
import com.github.lany192.dialog.LoadingDialog
import com.github.lany192.log.XLog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class BaseFragment : Fragment() {
    protected var log: XLog = XLog.tag(javaClass.name)
    private var loadingDialog: LoadingDialog? = null

    @CallSuper
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        initImmersionBar()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEvent(event: Void) {
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
        initImmersionBar()
    }

    open fun initImmersionBar() {}

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        val viewModel = ViewModelProvider(this)[modelClass]
        if (viewModel is LifecycleObserver) {
            lifecycle.addObserver((viewModel as LifecycleObserver))
        }
        return viewModel
    }

    fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T {
        val viewModel = ViewModelProvider(requireActivity())[modelClass]
        if (viewModel is LifecycleObserver) {
            lifecycle.addObserver((viewModel as LifecycleObserver))
        }
        return viewModel
    }

    fun <T : ViewModel> getAndroidViewModel(modelClass: Class<T>): T {
        return ViewModelProvider((requireActivity().applicationContext as ViewModelStoreOwner))[modelClass]
    }
}