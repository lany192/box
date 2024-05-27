package com.github.lany192.arch.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.R
import com.github.lany192.arch.items.ViewState
import com.github.lany192.binding.getBinding
import com.github.lany192.view.DefaultView
import com.github.lany192.view.databinding.ViewLoadingBinding
import java.lang.reflect.ParameterizedType

/**
 * ViewBinding的Activity基类,包含<头部和内容>
 */
abstract class ViewBindingActivity<VB : ViewBinding> : BaseActivity() {
    lateinit var binding: VB

    private lateinit var content: FrameLayout

    private var viewState = ViewState.CONTENT

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        content = FrameLayout(this)
        content.addView(binding.root)
        setContentView(content)
        addToolbarBackClickListener()
    }

    /**
     * 如果有返回id，添加监听
     */
    open fun addToolbarBackClickListener() {
        findViewById<View>(R.id.back)?.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    /**
     * 获取内容的ViewBinding实例
     * 如果不想用反射，复写该方法，去反射。
     */
    open fun getViewBinding(): VB {
        return getClass<VB>(0).getBinding(layoutInflater)
    }

    override fun setTitle(title: CharSequence?) {
        findViewById<TextView>(R.id.title)?.text = title
    }

    override fun setTitle(titleId: Int) {
        setTitle(getString(titleId))
    }

    open fun getErrorView(): View {
        val view = DefaultView(this)
        view.setMessage(R.string.default_network)
        return view
    }

    open fun getEmptyView(): View {
        val view = DefaultView(this)
        view.setMessage(R.string.default_empty)
        return view
    }

    open fun getLoadingView(): View {
        return ViewLoadingBinding.inflate(layoutInflater).root
    }

    open fun getNetworkView(): View {
        val view = DefaultView(this)
        view.setMessage(R.string.default_network)
        return view
    }

    fun showContentView() {
        resetViewType(ViewState.CONTENT)
    }

    fun showErrorView() {
        resetViewType(ViewState.ERROR)
    }

    fun showLoadingView() {
        resetViewType(ViewState.LOADING)
    }

    fun showNetworkView() {
        resetViewType(ViewState.NETWORK)
    }

    fun showEmptyView() {
        resetViewType(ViewState.EMPTY)
    }

    private fun resetViewType(state: ViewState) {
        if (viewState == state) {
            return
        }
        viewState = state
        content.removeAllViews()
        when (state) {
            ViewState.CONTENT -> {
                content.addView(binding.root)
            }

            ViewState.ERROR -> {
                content.addView(getErrorView())
            }

            ViewState.LOADING -> {
                content.addView(getLoadingView())
            }

            ViewState.NETWORK -> {
                content.addView(getNetworkView())
            }

            ViewState.EMPTY -> {
                content.addView(getEmptyView())
            }
        }
    }
}