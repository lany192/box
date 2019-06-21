package com.github.lany192.box.dialog;

import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

import butterknife.ButterKnife;


public abstract class BaseBottomDialog {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    protected BottomSheetDialog mDialog;
    protected View mContentView;

    protected abstract int getLayoutId();

    public BaseBottomDialog(Context context) {
        mDialog = new BottomSheetDialog(context);
        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), null);
        ButterKnife.bind(this, mContentView);
        mDialog.setContentView(mContentView);
        //使状态栏的颜色不变黑
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mDialog.getWindow();
            if (window != null) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
        }
    }

    protected <T extends View> T getView(@IdRes int viewId) {
        return mContentView.findViewById(viewId);
    }

    public void show() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void cancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.cancel();
        }
    }
}
