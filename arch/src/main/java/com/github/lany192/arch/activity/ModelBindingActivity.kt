package com.github.lany192.arch.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.items.ViewState
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.github.lany192.binding.getBinding

/**
 * ViewBinding实现基类
 */
abstract class ModelBindingActivity<VM : LifecycleViewModel, CVB : ViewBinding, TVB : ViewBinding> :
    BindingActivity<CVB, TVB>() {
    lateinit var viewModel: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun getToolbarBinding(): TVB {
        return getClass<TVB>(2).getBinding(layoutInflater)
    }

    override fun getContentBinding(): CVB {
        return getClass<CVB>(1).getBinding(layoutInflater)
    }
}