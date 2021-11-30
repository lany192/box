package com.github.lany192.box.interfaces;

import android.view.MotionEvent;
import android.view.View;

import com.elvishew.xlog.XLog;


/**
 * 处理双击事件
 */
public class OnDoubleClickListener implements View.OnTouchListener {
    private final String TAG = this.getClass().getSimpleName();
    private int count = 0;
    private long firstClickTime = 0;

    private DoubleClickCallback mCallback;

    public OnDoubleClickListener(DoubleClickCallback callback) {
        super();
        this.mCallback = callback;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            count++;
            if (1 == count) {
                firstClickTime = System.currentTimeMillis();
            } else if (2 == count) {
                long secondClickTime = System.currentTimeMillis();
                final int interval = 1500;//两次点击时间间隔，单位毫秒
                if (secondClickTime - firstClickTime < interval) {
                    if (mCallback != null) {
                        mCallback.onDoubleClick(v);
                    } else {
                        XLog.tag(TAG).e(TAG + "   请在构造方法中传入一个双击回调");
                    }
                    count = 0;
                    firstClickTime = 0;
                } else {
                    firstClickTime = secondClickTime;
                    count = 1;
                }
            }
        }
        return true;
    }

    public interface DoubleClickCallback {
        void onDoubleClick(View view);
    }
}