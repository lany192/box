package com.github.lany192.box.delegate;

/**
 * 具有请求调用接口能力的代理
 *
 * @param <T> 数据类型
 * @param <M> app请求接口
 */
public abstract class ModelDelegate<T, M> extends ItemDelegate<T> {
    /**
     * 请求仓库
     */
    private M model;

    public ModelDelegate(T data, M model) {
        super(data);
        this.model = model;
    }

    /**
     * 获取接口提供器
     *
     * @return 接口提供器
     */
    public M getModel() {
        return model;
    }
}