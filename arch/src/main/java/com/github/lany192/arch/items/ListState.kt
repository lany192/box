package com.github.lany192.arch.items

enum class ListState(val title: String) {
    ERROR("请求异常"),
    REFRESHING("正在刷新"),
    REFRESH_FINISH("刷新完成"),
    MORE_LOADING("更多加载中"),
    MORE_LOAD_END("更多加载结束"),
    MORE_LOAD_ERROR("加载更多异常"),
    MORE_LOAD_FINISH("加载更多完成");
}