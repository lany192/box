package com.github.lany192.arch.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.R
import com.github.lany192.arch.binding.findClass
import com.github.lany192.arch.binding.getBinding
import com.github.lany192.arch.items.ViewState
import com.github.lany192.arch.view.DefaultView
import com.github.lany192.arch.view.LoadingView
import com.github.lany192.utils.DensityUtils
import com.gyf.immersionbar.ImmersionBar

/**
 * ViewBinding的Activity<基类，包含头部和内容
 */
abstract class BindingActivity<CVB : ViewBinding, TVB : ViewBinding> : BaseActivity() {
    lateinit var binding: CVB
    lateinit var toolbar: TVB

    private lateinit var content: FrameLayout
    private var viewState = ViewState.CONTENT

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = getToolbarBinding()
        binding = findClass().getBinding(layoutInflater)
        content = FrameLayout(this)
        content.addView(binding.root)
        setContentView(content)

        if (hasToolbar()) {
            findViewById<View>(R.id.back)?.setOnClickListener { onBackPressed() }
            findViewById<TextView>(R.id.title)?.text = title
        }
    }

    abstract fun getToolbarBinding(): TVB

    override fun setTitle(title: CharSequence?) {
        if (hasToolbar()) {
            findViewById<TextView>(R.id.title)?.text = title
        }
    }

    override fun setTitle(titleId: Int) {
        if (hasToolbar()) {
            findViewById<TextView>(R.id.title)?.text = getString(titleId)
        }
    }

    override fun setContentView(view: View?) {
        if (hasToolbar()) {
            val content = LinearLayout(this)
            content.orientation = LinearLayout.VERTICAL
            content.addView(
                toolbar.root,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    DensityUtils.dp2px(48f)
                )
            )
            content.addView(
                view,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
            super.setContentView(content)
        } else {
            super.setContentView(view)
        }
    }

    /**
     * 是否需要toolbar
     */
    open fun hasToolbar(): Boolean {
        return true
    }

    override fun initImmersionBar() {
        if (hasToolbar()) {
            ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .navigationBarColor(android.R.color.white)
                .navigationBarDarkIcon(true)
                .transparentStatusBar()
                .titleBar(toolbar.root)
                .init()
        } else {
            ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .navigationBarColor(android.R.color.white)
                .navigationBarDarkIcon(true)
                .transparentStatusBar()
                .init()
        }
    }

    open fun getErrorView(): View {
        val view = DefaultView(this)
        view.setMessage("抱歉，发生异常")
        return view
    }

    open fun getEmptyView(): View {
        val view = DefaultView(this)
        view.setMessage("没有相关内容")
        return view
    }

    open fun getLoadingView(): View {
        return LoadingView(this)
    }

    open fun getNetworkView(): View {
        val view = DefaultView(this)
        view.setMessage("網絡異常，請重試")
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