package com.github.lany192.arch.items

enum class ViewState(val title: String) {
    CONTENT("内容"),
    ERROR("错误"),
    EMPTY("空数据"),
    LOADING("加载"),
    NETWORK("无网络");
}