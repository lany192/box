package com.github.lany192.arch.entity;

import com.github.lany192.arch.R;
import com.github.lany192.utils.ContextUtils;
import com.google.gson.annotations.SerializedName;

public class ApiResult<T> {
    @SerializedName(value = "code", alternate = {"errorCode"})
    private int code;

    @SerializedName(value = "msg", alternate = {"errorMsg", "message"})
    private String msg;

    @SerializedName(value = "data", alternate = {"result"})
    private T data;

    public static <T> ApiResult<T> network() {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(-1);
        result.setMsg(ContextUtils.getContext().getString(R.string.default_network));
        return result;
    }

    public static <T> ApiResult<T> network(String error) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(-1);
        result.setMsg(error);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}