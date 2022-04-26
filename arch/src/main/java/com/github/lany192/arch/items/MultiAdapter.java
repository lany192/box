package com.github.lany192.arch.items;

import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import java.util.List;

/**
 * 多布局适配器
 */
public class MultiAdapter extends BaseQuickAdapter<ItemDelegate, BaseViewHolder> {
    private SparseIntArray mTypeMap = new SparseIntArray();
    private final int TYPE_NOT_FOUND = -404;
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);

    public MultiAdapter(List<ItemDelegate> items) {
        super(0, items);
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
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return mTypeMap.get(viewType, TYPE_NOT_FOUND);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemDelegate item) {
        item.convert(helper, getContext());
    }
}