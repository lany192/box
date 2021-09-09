package com.github.lany192.box.dialog;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.elvishew.xlog.XLog;
import com.github.lany192.box.binding.BindingDialogFragment;
import com.github.lany192.box.databinding.DialogLoadingLayoutBinding;

import java.util.Objects;

/**
 * 加载对话框
 */
public class LoadingDialog extends BindingDialogFragment<DialogLoadingLayoutBinding> {
    private final String TAG = this.getClass().getName();
    private CharSequence mMessage;

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

    @Override
    public void show(@NonNull FragmentManager manager, String tag) {
        if (!TextUtils.isEmpty(mMessage)) {
            binding.message.setText(mMessage);
        }
        if (isAdded()) {
            XLog.tag(TAG).w("已经显示，忽略......");
        } else {
            super.show(manager, tag);
        }
    }

    public void cancel() {
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().cancel();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(this);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}