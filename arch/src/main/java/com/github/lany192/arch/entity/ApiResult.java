package com.github.lany192.arch.entity;

import com.github.lany192.arch.R;
import com.github.lany192.utils.ContextUtils;
import com.google.gson.annotations.SerializedName;

public class ApiResult<T> {
    @SerializedName(value = "code", alternate = {"errorCode"})
    private int code;

    @SerializedName(value = "msg", alternate = {"errorMsg", "message"})
    private String msg;

    @SerializedName(value = "result", alternate = {"data"})
    private T result;

    public static <T> ApiResult<T> network() {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(-1);
        result.setMsg(ContextUtils.getContext().getString(R.string.default_network));
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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}