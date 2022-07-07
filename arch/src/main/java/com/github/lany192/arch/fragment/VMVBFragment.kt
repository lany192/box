package com.github.lany192.arch.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.items.ViewState
import com.github.lany192.arch.viewmodel.LifecycleViewModel
import com.github.lany192.binding.getBinding
import com.github.lany192.view.DefaultView
import kotlinx.coroutines.launch

/**
 * ViewBinding实现基类
 */
abstract class VMVBFragment<VM : LifecycleViewModel, VB : ViewBinding> : VBFragment<VB>() {
    lateinit var viewModel: VM

    @CallSuper
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        viewModel = getDefaultViewModel()
    }

    @CallSuper
    override fun init() {
        lifecycleScope.launch {
            //页面基础状态观察
            launch {
                viewModel.viewState.collect {
                    when (it) {
                        ViewState.CONTENT -> showContentView()
                        ViewState.ERROR -> showErrorView()
                        ViewState.EMPTY -> showEmptyView()
                        ViewState.LOADING -> showLoadingView()
                        ViewState.NETWORK -> showNetworkView()
                    }
                }
            }
            //Loading对话框状态观察
            launch {
                viewModel.loadingState.collect {
                    if (it) {
                        showLoadingDialog()
                    } else {
                        cancelLoadingDialog()
                    }
                }
            }
            //是否关闭当前界面
            launch {
                viewModel.finishState.collect {
                    if (it) {
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    open fun getDefaultViewModel(): VM {
        return getViewModel(getClass(0))
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return getClass<VB>(1).getBinding(inflater, container)
    }

    override fun getErrorView(): View {
        val view = DefaultView(this)
        view.setMessage(viewModel.error.value)
        return view
    }

}