package com.lany.box.event;

/**
 * 网络事件
 */
public class NetWorkEvent {
    private boolean available;

    public NetWorkEvent() {
    }

    public NetWorkEvent(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}