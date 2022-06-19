package com.github.lany192.arch.entity;

import java.util.List;

public interface Page<T> {

    List<T> getList();

    boolean hasNext();

    int getPageSize();

    int getPageNum();
}
