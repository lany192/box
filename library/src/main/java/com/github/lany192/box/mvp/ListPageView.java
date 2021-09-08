package com.github.lany192.box.mvp;

import java.util.List;

/**
 * 分页列表页视图
 */
public interface ListPageView<T> extends BaseView {

    void addItems(List<T> items);

    void fail();

    void stop();

    void end();

    void gotoTop();

    List<T> getItems();

    void setItems(List<T> items);
}
