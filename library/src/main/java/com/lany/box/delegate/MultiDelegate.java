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
 * 多布局代理基类，适用于MultiAdapter适配器
 */
public abstract class MultiDelegate<D> implements MultiItemEntity {
    protected final String TAG = this.getClass().getSimpleName();
    private int mSpanSize = 2;
    private Context mContext;
    protected ItemViewHolder mHelper;
    protected Logger.Builder log = XLog.tag(TAG);
    protected D mData;

    public MultiDelegate(D data) {
        this.mData = data;
    }

    /**
     * 根据类class的名称生成对应的唯一id
     *
     * @return item类型
     */
    @Override
    public int getItemType() {
        return ViewTypeHelper.getInstance().getViewType(this);
    }

    /**
     * 站位的大小
     *
     * @return 大小
     */
    public int getSpanSize() {
        return mSpanSize;
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
        this.mHelper = helper;
        init();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        return (T) mHelper.getView(viewId);
    }

    public MultiDelegate setSpanSize(int spanSize) {
        log.i("spanSize:" + spanSize);
        this.mSpanSize = spanSize;
        return this;
    }

    public Context getContext() {
        return mContext;
    }
}
