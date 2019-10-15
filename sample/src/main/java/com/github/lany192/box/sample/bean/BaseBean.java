package com.github.lany192.box.sample.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseBean<T> {
    private int code;
    private String msg;
    private T data;
}
