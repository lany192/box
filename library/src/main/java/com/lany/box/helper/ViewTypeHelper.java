package com.lany.box.helper;

import android.support.v4.util.SparseArrayCompat;

import com.elvishew.xlog.XLog;
import com.lany.box.item.MultiItem;


/**
 * 根据类名获得一个唯一的随机数
 */
public class ViewTypeHelper {
    private final String TAG = getClass().getSimpleName();
    private volatile static ViewTypeHelper instance = null;
    private SparseArrayCompat<Class<?>> mViewTypeMap = null;

    private ViewTypeHelper() {
        mViewTypeMap = new SparseArrayCompat<>();
    }

    public static ViewTypeHelper getInstance() {
        if (instance == null) {
            synchronized (ViewTypeHelper.class) {
                if (instance == null) {
                    instance = new ViewTypeHelper();
                }
            }
        }
        return instance;
    }

    public int getViewType(MultiItem item) {
        Class<?> cls = item.getClass();
        int viewType = mViewTypeMap.indexOfValue(cls);
        if (viewType == -1) {//如果不存在
            viewType = mViewTypeMap.size();
            mViewTypeMap.put(viewType, cls);
            XLog.tag(TAG).i("添加类型：" + cls.getSimpleName() + " 对应的viewType:" + viewType);
        }
        return viewType;
    }

    public Class<?> getViewClass(int viewType) {
        Class<?> cls = mViewTypeMap.get(viewType);
        XLog.tag(TAG).i("获取viewType==" + viewType + "对应的类型是:" + cls);
        return cls;
    }
}
