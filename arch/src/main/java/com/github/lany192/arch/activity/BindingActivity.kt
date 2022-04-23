package com.github.lany192.arch.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.R
import com.github.lany192.arch.items.ViewState
import com.github.lany192.binding.getBinding
import com.github.lany192.view.DefaultView
import com.github.lany192.view.LoadingView
import com.gyf.immersionbar.ImmersionBar
import java.lang.reflect.ParameterizedType

/**
 * ViewBinding的Activity基类,包含<头部和内容>
 */
abstract class BindingActivity<CVB : ViewBinding, TVB : ViewBinding> : BaseActivity() {
    lateinit var binding: CVB
    lateinit var toolbar: TVB

    private lateinit var content: FrameLayout
    private var viewState = ViewState.CONTENT

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getContentBinding()
        content = FrameLayout(this)
        content.addView(binding.root)
        setContentView(content)
        //如果有返回id，添加监听
        findViewById<View>(R.id.back)?.setOnClickListener { onBackPressed() }
        //如果有标题id，设置默认标题值
        findViewById<TextView>(R.id.title)?.text = title
    }

    /**
     * 获取第几个泛型的class
     */
    open fun <T> getClass(index: Int): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>
    }

    /**
     * 获取toolbar的ViewBinding实例
     * 如果不想用反射，复写该方法，去反射。
     */
    open fun getToolbarBinding(): TVB {
        return getClass<TVB>(1).getBinding(layoutInflater)
    }

    /**
     * 获取内容的ViewBinding实例
     * 如果不想用反射，复写该方法，去反射。
     */
    open fun getContentBinding(): CVB {
        return getClass<CVB>(0).getBinding(layoutInflater)
    }

    override fun setTitle(title: CharSequence?) {
        findViewById<TextView>(R.id.title)?.text = title
    }

    override fun setTitle(titleId: Int) {
        findViewById<TextView>(R.id.title)?.text = getString(titleId)
    }

    override fun setContentView(view: View?) {
        if (hasToolbar()) {
            toolbar = getToolbarBinding()
            val content = LinearLayout(this)
            content.orientation = LinearLayout.VERTICAL
            content.addView(
                toolbar.root,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    resources.getDimension(R.dimen.actionbar_height).toInt()
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

    override fun initImmersionBar(): ImmersionBar {
        if (hasToolbar()) {
            return super.initImmersionBar().titleBar(toolbar.root)
        }
        return super.initImmersionBar()
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
        return LoadingView(this)
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