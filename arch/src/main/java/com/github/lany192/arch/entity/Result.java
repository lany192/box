package com.github.lany192.arch.entity;

public interface Result<T> {

    /**
     * 接口响应码
     */
    int getCode();

    /**
     * 接口提示语
     */
    String getMessage();

    /**
     * 接口处理结果
     */
    T getResult();
}
