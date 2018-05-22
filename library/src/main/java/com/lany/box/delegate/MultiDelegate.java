package com.lany.box.delegate;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.lany.box.adapter.ItemViewHolder;
import com.lany.box.helper.ViewTypeHelper;

/**
 * 多布局代理基类
 */
public abstract class MultiDelegate implements MultiItemEntity {
    protected final String TAG = this.getClass().getSimpleName();
    private int spanSize = 2;
    private Context mContext;
    protected ItemViewHolder helper;
    protected Logger.Builder log = XLog.tag(TAG);

    @Override
    public int getItemType() {
        return ViewTypeHelper.getInstance().getViewType(this);
    }

    public int getSpanSize() {
        return spanSize;
    }

    /**
     * 返回布局文件id
     *
     * @return 布局文件id
     */
    @LayoutRes
    public abstract int getLayoutId();

    /**
     * 初始化方法
     */
    public abstract void init();

    public void convert(ItemViewHolder helper, Context context) {
        this.mContext = context;
        this.helper = helper;
        init();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        return (T) helper.getView(viewId);
    }

    public MultiDelegate setSpanSize(int spanSize) {
        log.i("spanSize:" + spanSize);
        this.spanSize = spanSize;
        return this;
    }

    public Context getContext() {
        return mContext;
    }
}
