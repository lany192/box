package com.github.lany192.arch.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import com.github.lany192.dialog.LoadingDialog
import com.github.lany192.extension.log
import com.github.lany192.extension.postDelayedOnLifecycle
import com.github.lany192.utils.KeyboardUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.reflect.ParameterizedType

/**
 * Activity基类
 */
abstract class BaseActivity : AppCompatActivity() {
    private var loadingDialog: LoadingDialog? = null

    private lateinit var startForResultLauncher: StartActivityForResultLauncher
    private lateinit var mMediaPickerLauncher: MediaPickerLauncher

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(NetworkHelper.getInstance())
        //控制屏幕方向
        requestedOrientation = getCustomRequestedOrientation()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        startForResultLauncher = StartActivityForResultLauncher(this)
        mMediaPickerLauncher = MediaPickerLauncher(this)
    }

    fun startActivityForResult(intent: Intent, callback: OnResultCallback<ActivityResult>?) {
        startForResultLauncher.launch(intent) { callback?.onResult(it) }
    }

    fun startMediaPicker(
        callback: OnResultCallback<Uri>?,
        request: PickVisualMediaRequest = PickVisualMediaRequest
            .Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
            .build()
    ) {
        mMediaPickerLauncher.launch(request) { callback?.onResult(it) }
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
        initImmersionBar()
    }

    @CallSuper
    public override fun onResume() {
        super.onResume()
        initImmersionBar()
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

    @JvmOverloads
    fun showLoadingDialog(message: CharSequence? = getString(R.string.loading)) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog()
        }
        loadingDialog?.setMessage(message)
        loadingDialog?.show()
    }

    fun cancelLoadingDialog() {
        loadingDialog?.cancel()
        loadingDialog = null
    }

    open fun initImmersionBar() {
        log("执行initImmersionBar")
    }

    @ColorInt
    fun getColorResId(@ColorRes id: Int): Int {
        return ContextCompat.getColor(this, id)
    }

//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        if (event.action == MotionEvent.ACTION_DOWN) {
//            //点击空白区域收起输入法
//            KeyboardUtils.hide(this)
//        }
//        return super.dispatchTouchEvent(event)
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEvent(event: HideSoftInputEvent) {
        postDelayedOnLifecycle(300) {
            KeyboardUtils.hide(this)
        }
    }
}