package com.github.lany192.box.adapter;

import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.delegate.ItemDelegate;

import java.util.List;
import java.util.Objects;

/**
 * 多布局适配器
 */
public class MultiAdapter extends BaseQuickAdapter<ItemDelegate, ItemViewHolder> {
    private SparseIntArray typeMap = new SparseIntArray();
    private final int TYPE_NOT_FOUND = -404;
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);

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
                typeMap.put(viewType, item.getLayoutId());
            }
        }
        return viewType;
    }

    @Override
    protected ItemViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return typeMap.get(viewType, TYPE_NOT_FOUND);
    }

    @Override
    protected void convert(ItemViewHolder holder, ItemDelegate item) {
        item.convert(holder, mContext);
    }
}