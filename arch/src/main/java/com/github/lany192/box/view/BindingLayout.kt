package com.github.lany192.box.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.viewbinding.ViewBinding
import com.github.lany192.box.binding.getBinding

/**
 * ViewBinding实现基类
 */
abstract class BindingLayout<VB : ViewBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var binding: VB = getBinding()

    init {
        addView(binding.root)
        init(attrs)
    }

    abstract fun init(attrs: AttributeSet?)
}