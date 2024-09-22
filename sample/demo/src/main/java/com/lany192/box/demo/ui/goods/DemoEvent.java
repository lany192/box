package com.lany192.box.demo.ui.goods;

public class DemoEvent {
    private int firstVisibleItemPosition;

    public DemoEvent(int firstVisibleItemPosition) {
        this.firstVisibleItemPosition = firstVisibleItemPosition;
    }

    public int getFirstVisibleItemPosition() {
        return firstVisibleItemPosition;
    }

    public void setFirstVisibleItemPosition(int firstVisibleItemPosition) {
        this.firstVisibleItemPosition = firstVisibleItemPosition;
    }
}
