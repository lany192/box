package com.github.lany192.arch.activity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.github.lany192.arch.R
import com.github.lany192.arch.event.HideSoftInputEvent
import com.github.lany192.arch.network.NetworkHelper
import com.github.lany192.arch.utils.BarUtils
import com.github.lany192.dialog.LoadingDialog
import com.github.lany192.log.XLog
import com.github.lany192.utils.KeyboardUtils
import com.gyf.immersionbar.ImmersionBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.reflect.ParameterizedType

/**
 * Activity基类
 */
abstract class BaseActivity : AppCompatActivity() {
    @JvmField
    protected var log: XLog = XLog.tag(javaClass.simpleName)

    private var loadingDialog: LoadingDialog? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(NetworkHelper.getInstance())
        //控制屏幕方向
        requestedOrientation = getCustomRequestedOrientation()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    open fun getCustomRequestedOrientation(): Int {
        return if (isPortraitScreen()) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
    }

    @CallSuper
    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        KeyboardUtils.hide(this)
        super.onDestroy()
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

    /**
     * 获取第几个泛型的class
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getClass(index: Int): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>
    }

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
        return BarUtils.init(this).keyboardEnable(true)
    }

    @ColorInt
    fun getColorResId(@ColorRes id: Int): Int {
        return ContextCompat.getColor(this, id)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            //点击空白区域收起输入法
            KeyboardUtils.hide(this)
        }
        return super.dispatchTouchEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEvent(event: HideSoftInputEvent) {
        Handler(Looper.getMainLooper()).postDelayed({ KeyboardUtils.hide(this) }, 300)
    }
}