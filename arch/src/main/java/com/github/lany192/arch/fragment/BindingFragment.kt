package com.github.lany192.arch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.items.ViewState
import com.github.lany192.binding.getBinding
import com.github.lany192.view.DefaultView
import com.github.lany192.view.LoadingView
import java.lang.reflect.ParameterizedType

/**
 * ViewBinding实现基类
 */
abstract class BindingFragment<VB : ViewBinding> : BaseFragment() {
    private var _binding: VB? = null

    private lateinit var content: FrameLayout
    private var viewState = ViewState.CONTENT

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding(inflater, container)
        content = FrameLayout(requireContext())
        content.addView(binding.root)
        init()
        return content
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * 获取第几个泛型的class
     */
    open fun <T> getClass(index: Int): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>
    }

    open fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return getClass<VB>(0).getBinding(inflater, container)
    }

    abstract fun init()

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
        view.setMessage("网络异常，请检查网络设置。")
        return view
    }
}