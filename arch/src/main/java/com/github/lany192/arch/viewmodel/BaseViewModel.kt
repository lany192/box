package com.github.lany192.arch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.lany192.arch.items.ViewState
import com.github.lany192.utils.LogUtils
import com.github.lany192.utils.XLog
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*

open class BaseViewModel : ViewModel() {
    /**
     * 日志打印
     */
    protected val log: XLog = LogUtils.tag(javaClass.simpleName)

    /**
     * 观察界面基础状态
     */
    private val _viewState = MutableStateFlow(ViewState.CONTENT)

    /**
     * 观察加载对话框状态
     */
    private val _loadingState = MutableStateFlow(false)

    /**
     * 关闭当前Activity界面
     */
    private val _finishState = MutableStateFlow(false)

    /**
     * 异常信息
     */
    private val _error = MutableStateFlow<CharSequence>("发现异常")

    val viewState: StateFlow<ViewState> = _viewState
    val loadingState: StateFlow<Boolean> = _loadingState
    val finishState: StateFlow<Boolean> = _finishState
    val error: StateFlow<CharSequence> = _error

    /**
     * 显示加载对话框
     */
    fun showLoadingDialog() {
        if (_loadingState.subscriptionCount.value > 0) {
            _loadingState.value = true
        } else {
            log.e("没有发现可用的观察者，请检查逻辑是否正确")
        }
    }

    /**
     * 取消加载对话框
     */
    fun cancelLoadingDialog() {
        if (_loadingState.subscriptionCount.value > 0) {
            _loadingState.value = false
        } else {
            log.e("没有发现可用的观察者，请检查逻辑是否正确")
        }
    }

    /**
     * 显示内容界面
     */
    fun showContentView() {
        _viewState.value = ViewState.CONTENT
    }

    /**
     * 显示异常界面
     */
    fun showErrorView() {
        _viewState.value = ViewState.ERROR
    }

    /**
     * 显示异常界面
     */
    fun showErrorView(error: CharSequence) {
        _error.value = error
        _viewState.value = ViewState.ERROR
    }

    /**
     * 显示加载界面
     */
    fun showLoadingView() {
        _viewState.value = ViewState.LOADING
    }

    /**
     * 显示网络异常界面
     */
    fun showNetworkView() {
        _viewState.value = ViewState.NETWORK
    }

    /**
     * 显示空界面
     */
    fun showEmptyView() {
        _viewState.value = ViewState.EMPTY
    }

    /**
     * 关闭当前Activity界面
     */
    fun finish() {
        if (_finishState.subscriptionCount.value > 0) {
            _finishState.value = true
        } else {
            log.e("没有发现可用的观察者，请检查逻辑是否正确")
        }
    }

    /**
     * 带加载对话框的请求
     */
    suspend fun <T> requestWithLoading(block: suspend () -> Flow<T>): Flow<T> {
        return block().onStart { showLoadingDialog() }.onCompletion { cancelLoadingDialog() }
    }

    fun onRetry() {

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}