package com.github.lany192.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import com.github.lany192.extension.addStatusBarPadding
import com.github.lany192.view.databinding.ViewToolbarBinding

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BindingView<ViewToolbarBinding>(context, attrs, defStyleAttr) {

    override fun getViewBinding(): ViewToolbarBinding {
        return ViewToolbarBinding.inflate(LayoutInflater.from(context))
    }

    override fun init(attrs: AttributeSet?) {
        addStatusBarPadding()
        binding.back.setOnClickListener {
            if (context is ComponentActivity) {
                (context as ComponentActivity).onBackPressedDispatcher.onBackPressed()
            }
        }
        if (context is ComponentActivity) {
            setTitle((context as ComponentActivity).title)
        }
    }

    fun setTitle(title: CharSequence?) {
        binding.title.text = title
    }

    fun setTitle(@StringRes titleId: Int) {
        setTitle(context.getString(titleId))
    }
}
