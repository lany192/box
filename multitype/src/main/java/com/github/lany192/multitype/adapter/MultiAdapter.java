package com.github.lany192.multitype.adapter;

import android.util.SparseIntArray;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.github.lany192.multitype.delegate.Delegate;

import java.util.List;

/**
 * 多布局适配器
 */
public class MultiAdapter extends BaseMultiItemQuickAdapter<Delegate, ItemViewHolder> {
    private final int TYPE_NOT_FOUND = -404;
    private final SparseIntArray mItemTypeMap = new SparseIntArray();

    public MultiAdapter(List<Delegate> items) {
        super(items);
    }

    public MultiAdapter(List<Delegate> items, GridLayoutManager layoutManager) {
        super(items);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return getItem(position).getSpanSize();
            }
        });
    }

    @Override
    protected int getDefItemViewType(int position) {
        Delegate item = getItem(position);
        int viewType = -1;
        if (item != null) {
            viewType = item.getItemType();
            if (getLayoutId(viewType) == TYPE_NOT_FOUND) {
                mItemTypeMap.put(viewType, item.getLayoutId());
            }
        }
        return viewType;
    }

    @Override
    protected ItemViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return mItemTypeMap.get(viewType, TYPE_NOT_FOUND);
    }

    @Override
    protected void convert(ItemViewHolder holder, Delegate delegate) {
        delegate.convert(holder, getContext());
    }
}