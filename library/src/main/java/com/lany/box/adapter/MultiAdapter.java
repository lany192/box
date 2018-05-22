package com.lany.box.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.util.SparseIntArray;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.lany.box.delegate.MultiDelegate;

import java.util.List;

/**
 * 多布局适配器
 */
public class MultiAdapter extends BaseQuickAdapter<MultiDelegate, ItemViewHolder> {
    private SparseIntArray mTypeMap = new SparseIntArray();
    private final int TYPE_NOT_FOUND = -404;
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);

    public MultiAdapter(List<MultiDelegate> items) {
        super(items);
        setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                int spanSize = getItem(position).getSpanSize();
                log.i("spanSize:" + spanSize);
                return spanSize;
            }
        });
    }

    @Override
    protected int getDefItemViewType(int position) {
        MultiDelegate item = getItem(position);
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
    protected void convert(ItemViewHolder helper, MultiDelegate item) {
        item.convert(helper, mContext);
    }
}