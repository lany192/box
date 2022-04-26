package com.github.lany192.arch.items;

import androidx.collection.SparseArrayCompat;
import com.elvishew.xlog.XLog;

/**
 * 根据类名获得一个唯一的随机数，用于给多布局适配器代理获取对应的类型
 */
public class ItemTypeHelper {
    private final String TAG = getClass().getSimpleName();
    private volatile static ItemTypeHelper instance = null;
    private SparseArrayCompat<Class<?>> mTypeMap;

    private ItemTypeHelper() {
        mTypeMap = new SparseArrayCompat<>();
    }

    public static ItemTypeHelper getInstance() {
        if (instance == null) {
            synchronized (ItemTypeHelper.class) {
                if (instance == null) {
                    instance = new ItemTypeHelper();
                }
            }
        }
        return instance;
    }

    public int getViewType(ItemDelegate item) {
        Class<?> cls = item.getClass();
        int viewType = mTypeMap.indexOfValue(cls);
        if (viewType == -1) {//如果不存在
            viewType = mTypeMap.size();
            mTypeMap.put(viewType, cls);
            XLog.tag(TAG).i("添加类型：" + cls.getSimpleName() + " 对应的viewType:" + viewType);
        }
        return viewType;
    }

    public Class<?> getViewClass(int itemType) {
        Class<?> cls = mTypeMap.get(itemType);
        XLog.tag(TAG).i("获取itemType:" + itemType + "对应的类型是:" + cls.getSimpleName());
        return cls;
    }
}
