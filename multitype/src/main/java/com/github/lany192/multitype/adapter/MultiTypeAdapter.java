package com.github.lany192.multitype.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.multitype.delegate.ViewDelegate;

import java.util.HashMap;
import java.util.List;

public class MultiTypeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<ViewDelegate> items;
    private Context context;
    private final HashMap<Integer, ViewDelegate> itemTypeMap = new HashMap<>();

    public MultiTypeAdapter(List<ViewDelegate> items) {
        this.items = items;
    }

    public void setItems(List<ViewDelegate> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<ViewDelegate> items) {
        this.items.addAll(items);


        notifyItemRangeChanged(items.size(), items.size());
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.context = recyclerView.getContext();
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItem(position).getSpanSize();
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.itemTypeMap.clear();
    }

    @Override
    public int getItemViewType(int position) {
        ViewDelegate delegate = getItem(position);
        if (!itemTypeMap.containsKey(delegate.getItemType())) {
            itemTypeMap.put(delegate.getItemType(), delegate);
        }
        return delegate.getItemType();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDelegate delegate = itemTypeMap.get(viewType);
        View view = delegate.getView(context, parent);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ViewDelegate delegate = getItem(position);
        delegate.onBind(holder, position);
    }

    public ViewDelegate getItem(int position) {
        if (position >= 0 && position < items.size()) {
            return items.get(position);
        }
        throw new RuntimeException("数组越界，position=" + position + ",size=" + items.size());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
