package com.lany.box.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import java.util.List;

import butterknife.ButterKnife;

/**
 * ListView适配器基类,不推荐使用ListView
 */
@Deprecated
public abstract class BasicAdapter<T> extends BaseAdapter {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    private Context mContext;
    protected List<T> mItems;

    public BasicAdapter(List<T> items) {
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mItems != null ? mItems.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear() {
        this.mItems.clear();
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return mItems;
    }

    public void addItems(List<T> items) {
        if (items != null && items.size() > 0) {
            this.mItems.addAll(items);
            notifyDataSetChanged();
        }
    }

    protected final View getItemView(@LayoutRes int resource, ViewGroup root) {
        this.mContext = root.getContext();
        return LayoutInflater.from(mContext).inflate(resource, root, false);
    }

    protected final CharSequence checkNoNull(CharSequence str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    protected int getColor(@ColorRes int id) {
        return ContextCompat.getColor(mContext, id);
    }

    protected Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(mContext, id);
    }

    public Context getContext() {
        return mContext;
    }

    public abstract class BaseViewHolder {
        public final View itemView;

        public BaseViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}