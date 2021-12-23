package com.github.lany192.arch.items;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseBinderAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;

/**
 * 多布局适配器
 */
public class ListAdapter extends BaseBinderAdapter implements LoadMoreModule {

    @NonNull
    @Override
    public BaseLoadMoreModule addLoadMoreModule(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        return new BaseLoadMoreModule(baseQuickAdapter);
    }
}
