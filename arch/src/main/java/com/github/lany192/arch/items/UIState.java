package com.github.lany192.arch.items;

public enum UIState {
    CONTENT("内容"),
    ERROR("错误"),
    EMPTY("空数据"),
    LOADING("加载"),
    NETWORK("无网络"),
    STOP_REQUEST("停止请求"),
    REFRESHING("正在刷新"),
    REFRESH_FINISH("刷新完成"),
    MORE_LOADING("更多加载中"),
    MORE_END("更多加载结束"),
    MORE_ERROR("加载更多异常"),
    MORE_FINISH("加载更多完成");

    private final String title;

    UIState(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
