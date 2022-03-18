package com.github.lany192.arch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.binding.findClass
import com.github.lany192.arch.binding.getBinding
import com.github.lany192.arch.items.ViewState
import com.github.lany192.arch.view.DefaultView
import com.github.lany192.arch.view.LoadingView

/**
 * ViewBinding实现基类
 */
abstract class BindingFragment<VB : ViewBinding> : BaseFragment() {
    lateinit var binding: VB

    private lateinit var content: FrameLayout
    private var viewState = ViewState.CONTENT

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = findClass().getBinding(inflater, container)
        content = FrameLayout(requireContext())
        content.addView(binding.root)
        init()
        return content
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
        view.setMessage("網絡異常，請重試")
        return view
    }
}