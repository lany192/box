package com.github.lany192.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.utils.DensityUtils;

import java.util.Objects;

public abstract class BaseDialog extends BaseDialogFragment {
    private boolean canceledOnTouchOutside = true;
    private boolean isInitLoaded;

    protected abstract int getLayoutId();

    protected abstract void init();

    /**
     * 是否为底部弹窗
     */
    protected boolean bottomStyle() {
        return false;
    }

    protected int getDialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected int getDialogWidth() {
        if (bottomStyle()) {
            return WindowManager.LayoutParams.MATCH_PARENT;
        } else {
            return DensityUtils.dp2px(300);
        }
    }

    @Override
    public int getTheme() {
        if (bottomStyle()) {
            return R.style.BottomDialogTheme;
        } else {
            return super.getTheme();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(isCancelable());
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (!isCancelable()) {
            dialog.setOnKeyListener((dialog1, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
        }
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, true);
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window != null) {
            window.setLayout(getDialogWidth(), getDialogHeight());
            if (bottomStyle()) {
                window.setGravity(Gravity.BOTTOM);
            }
        }
        if (!isInitLoaded) {
            isInitLoaded = true;
            init();
        }
    }
}