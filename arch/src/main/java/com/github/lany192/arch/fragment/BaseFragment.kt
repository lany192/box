package com.github.lany192.arch.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.arch.R
import com.github.lany192.dialog.LoadingDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseFragment : Fragment() {
    protected var log: Logger.Builder = XLog.tag(javaClass.name)
    private var loadingDialog: LoadingDialog? = null

    /**
     * 是否执行过懒加载
     */
    private var lazyLoaded = false

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
        if (immersionBarEnabled()) {
            initImmersionBar()
        }
        if (!lazyLoaded && !isHidden) {
            onLazyLoad()
            lazyLoaded = true
        }
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        lazyLoaded = false
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    /**
     * 如果需要懒加载，逻辑写在这里,只被调用一次
     */
    protected open fun onLazyLoad() {
        log.i("懒加载...")
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