package com.github.lany192.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.github.lany192.binding.getBinding
import com.github.lany192.log.LogUtils
import com.github.lany192.log.XLog
import java.lang.reflect.ParameterizedType

/**
 * 自定义视图基类
 */
abstract class BindingView<VB : ViewBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), LifecycleOwner {
    private val registry = LifecycleRegistry(this)

    @JvmField
    protected var log: XLog = LogUtils.tag(javaClass.simpleName)

    lateinit var binding: VB

    abstract fun init(attrs: AttributeSet?)

    init {
        binding = getClass<VB>(0).getBinding(LayoutInflater.from(context), this)
        addView(binding.root)
        init(attrs)
    }

    /**
     * 获取第几个泛型的class
     */
    open fun <T> getClass(index: Int): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>
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