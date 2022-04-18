package com.github.lany192.arch.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.items.ViewState
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.github.lany192.binding.getBinding

/**
 * ViewBinding实现基类
 */
abstract class BoxFragment<VM : LifecycleViewModel, VB : ViewBinding> : BindingFragment<VB>() {
    lateinit var viewModel: VM

    @CallSuper
    override fun init() {
        viewModel = getDefaultViewModel()
        //Loading对话框状态观察
        viewModel.loadingState.observe(this) { show: Boolean ->
            if (show) {
                showLoadingDialog()
            } else {
                cancelLoadingDialog()
            }
        }
        //页面基础状态观察
        viewModel.viewState.observe(this) { state: ViewState ->
            when (state) {
                ViewState.CONTENT -> showContentView()
                ViewState.ERROR -> showErrorView()
                ViewState.EMPTY -> showEmptyView()
                ViewState.LOADING -> showLoadingView()
                ViewState.NETWORK -> showNetworkView()
            }
        }
    }

    open fun getDefaultViewModel(): VM {
        return getViewModel(getClass(0))
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return getClass<VB>(1).getBinding(inflater, container)
    }
}