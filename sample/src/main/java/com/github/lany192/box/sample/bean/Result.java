package com.github.lany192.box.sample.bean;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public boolean isSuccess() {
        return code == 200;
    }
}
