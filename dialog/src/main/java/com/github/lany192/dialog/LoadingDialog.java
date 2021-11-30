package com.github.lany192.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.StringRes;

import java.util.Objects;

/**
 * 加载对话框
 */
public class LoadingDialog extends BaseDialogFragment {
    private CharSequence mMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, null);
        TextView textView = view.findViewById(R.id.message);
        if (!TextUtils.isEmpty(mMessage)) {
            textView.setText(mMessage);
        }
        return view;
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