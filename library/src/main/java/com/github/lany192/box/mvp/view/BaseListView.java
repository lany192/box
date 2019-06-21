package com.github.lany192.box.mvp.view;

import java.util.List;

public interface BaseListView<T> extends BaseView {

    void addItems(List<T> items);

    void setItems(List<T> items);

    void fail();

    void stop();

    void end();

    void gotoTop();

    List<T> getItems();
}
