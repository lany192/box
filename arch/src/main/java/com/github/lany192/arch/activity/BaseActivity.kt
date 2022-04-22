package com.github.lany192.arch.activity

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.arch.R
import com.github.lany192.arch.network.NetworkHelper
import com.github.lany192.dialog.LoadingDialog
import com.gyf.immersionbar.ImmersionBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Activity基类
 */
abstract class BaseActivity : AppCompatActivity() {
    @JvmField
    protected var log: Logger.Builder = XLog.tag(javaClass.simpleName)

    private var loadingDialog: LoadingDialog? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(NetworkHelper.getInstance())
        //控制屏幕方向
        requestedOrientation = if (isPortraitScreen()) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: Void?) {

    }

    /**
     * 自定义屏幕方向，默认竖屏
     * 注意：需要配置 android:screenOrientation="nosensor"
     */
    open fun isPortraitScreen(): Boolean {
        return true
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
        loadingDialog!!.show()
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
            initImmersionBar().init()
        }
    }

    @CallSuper
    public override fun onResume() {
        super.onResume()
        if (immersionBarEnabled()) {
            initImmersionBar().init()
        }
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    open fun immersionBarEnabled(): Boolean {
        return true
    }

    open fun initImmersionBar(): ImmersionBar {
        return ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .navigationBarColor(R.color.white_bg)
            .navigationBarDarkIcon(true)
    }

    @ColorInt
    fun getColorResId(@ColorRes id: Int): Int {
        return ContextCompat.getColor(this, id)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action === MotionEvent.ACTION_DOWN) {
            //点击空白区域收起输入法
            hideSoftInput()
        }
        return super.onTouchEvent(event)
    }

    override fun finish() {
        hideSoftInput()
        super.finish()
    }

    /**
     * 关闭输入法
     */
    protected open fun hideSoftInput() {
        if (currentFocus != null && currentFocus!!.windowToken != null) {
            val manager: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(
                currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}