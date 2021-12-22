package com.github.lany192.arch.items;

public enum ListState {
    STOP_REQUEST("停止请求"),
    REFRESHING("正在刷新"),
    REFRESH_FINISH("刷新完成"),
    MORE_LOADING("更多加载中"),
    MORE_LOAD_END("更多加载结束"),
    MORE_LOAD_ERROR("加载更多异常"),
    MORE_LOAD_FINISH("加载更多完成");

    private final String title;

    ListState(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
