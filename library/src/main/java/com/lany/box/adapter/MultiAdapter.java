package com.lany.box.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lany.box.item.MultiItem;

import java.util.List;

public class MultiAdapter extends BaseQuickAdapter<MultiItem, ItemViewHolder> {
    private SparseIntArray mTypeMap = new SparseIntArray();
    private final int TYPE_NOT_FOUND = -404;

    public MultiAdapter(List<MultiItem> items) {
        super(items);
        setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return getItem(position).getSpanSize();
            }
        });
    }

    @Override
    protected int getDefItemViewType(int position) {
        MultiItem item = getItem(position);
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
    protected void convert(ItemViewHolder helper, MultiItem item) {
        item.convert(helper, mContext);
    }
}