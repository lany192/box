package com.github.lany192.box.adapter;

import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.lany192.box.delegate.ItemDelegate;

import java.util.List;
import java.util.Objects;

/**
 * 多布局适配器
 */
public class MultiAdapter extends BaseQuickAdapter<ItemDelegate, ItemViewHolder> {
    private SparseIntArray mTypeMap = new SparseIntArray();
    private final int TYPE_NOT_FOUND = -404;

    public MultiAdapter(List<ItemDelegate> items) {
        super(items);
        setSpanSizeLookup((gridLayoutManager, position) -> Objects.requireNonNull(getItem(position)).getSpanSize());
    }

    @Override
    protected int getDefItemViewType(int position) {
        ItemDelegate item = getItem(position);
        int viewType = -1;
        if (item != null) {
            viewType = item.getItemType();
            if (getLayoutId(viewType) == TYPE_NOT_FOUND) {
                mTypeMap.put(viewType, item.getLayoutId());
            }
        }
        return viewType;
    }

    @Override
    protected ItemViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return mTypeMap.get(viewType, TYPE_NOT_FOUND);
    }

    @Override
    protected void convert(ItemViewHolder holder, ItemDelegate item) {
        item.convert(holder, mContext);
    }
}