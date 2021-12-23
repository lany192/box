package com.github.lany192.arch.items;

public enum ViewState {
    CONTENT("内容"),
    ERROR("错误"),
    EMPTY("空数据"),
    LOADING("加载"),
    NETWORK("无网络");

    private final String title;

    ViewState(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
