package com.github.lany192.box.dialog;

import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


import com.github.lany192.box.R;

import java.util.Objects;

/**
 * 底部弹窗 基础对话框
 */
public abstract class BottomDialog extends DialogFragment {

    @Override
    public int getTheme() {
        return R.style.BottomDialogTheme;
    }

    @Override
    protected int getDialogWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void onResume() {
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
        }
        super.onResume();
    }
}
