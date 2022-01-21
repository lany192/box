package com.github.lany192.arch.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.arch.binding.findClass
import com.github.lany192.arch.binding.getBinding

/**
 * 自定义视图基类
 */
abstract class BindingView<VB : ViewBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), LifecycleOwner {
    private val registry = LifecycleRegistry(this)
    protected var log: Logger.Builder = XLog.tag(javaClass.simpleName)

    lateinit var binding: VB

    abstract fun init(attrs: AttributeSet?)

    init {
        binding = findClass().getBinding(LayoutInflater.from(context), this)
        addView(binding.root)
        init(attrs)
    }

    fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProvider((context as ViewModelStoreOwner))[modelClass]
    }

    fun <T : ViewModel> getAndroidViewModel(modelClass: Class<T>): T {
        return ViewModelProvider((context.applicationContext as ViewModelStoreOwner))[modelClass]
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        registry.currentState = Lifecycle.State.CREATED
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        registry.currentState = Lifecycle.State.DESTROYED
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (visibility == VISIBLE) {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_START)
            registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        } else if (visibility == GONE || visibility == INVISIBLE) {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
    }

    override fun getLifecycle(): Lifecycle {
        return registry
    }
}