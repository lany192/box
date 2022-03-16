package com.github.lany192.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.StringRes;

import com.github.lany192.dialog.databinding.DialogLoadingBinding;

import java.util.Objects;

/**
 * 加载对话框
 */
public class LoadingDialog extends BaseDialog<DialogLoadingBinding> {
    private CharSequence mMessage;

    @Override
    protected void init() {
        if (!TextUtils.isEmpty(mMessage)) {
            binding.message.setText(mMessage);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f;//设置背景全透明
            window.setAttributes(layoutParams);
        }
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
    }

    public void setMessage(@StringRes int resId) {
        this.mMessage = getString(resId);
    }
}