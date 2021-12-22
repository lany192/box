package com.github.lany192.arch.items;

public enum UIState {
    CONTENT("内容"),
    ERROR("错误"),
    EMPTY("空数据"),
    LOADING("加载"),
    NETWORK("无网络"),
    REFRESH("刷新"),
    LOAD_MORE("加载更多");

    private final String title;

    UIState(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
