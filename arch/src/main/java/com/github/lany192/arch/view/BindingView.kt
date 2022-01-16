package com.github.lany192.arch.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.binding.findClass
import com.github.lany192.arch.binding.getBinding

/**
 * ViewBinding实现基类
 */
abstract class BindingView<VB : ViewBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var binding: VB = createBinding()

    init {
        addView(binding.root)
        init(attrs)
    }

    abstract fun init(attrs: AttributeSet?)

    private fun <V : ViewBinding> createBinding(): V {
        return findClass().getBinding(LayoutInflater.from(context))
    }
}