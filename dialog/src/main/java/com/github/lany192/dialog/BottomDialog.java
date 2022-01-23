package com.github.lany192.dialog;

import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.viewbinding.ViewBinding;

import java.util.Objects;

public abstract class BottomDialog<VB extends ViewBinding> extends BaseDialog<VB> {

    @Override
    protected int getDialogWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int getTheme() {
        return R.style.BottomDialogTheme;
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window != null) {
            window.setLayout(getDialogWidth(), getDialogHeight());
            window.setGravity(Gravity.BOTTOM);
        }
    }
}