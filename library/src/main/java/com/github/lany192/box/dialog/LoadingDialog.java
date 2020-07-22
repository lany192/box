package com.github.lany192.box.dialog;


import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.lany192.box.R;

import java.util.Objects;

public class LoadingDialog extends DialogFragment {
    private CharSequence mMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_loading_layout;
    }

    @Override
    protected void init() {
        TextView msgText = findViewById(R.id.loading_dialog_msg_text);
        if (!TextUtils.isEmpty(mMessage)) {
            msgText.setText(mMessage);
        }
    }

    @Override
    protected int getDialogWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f;//设置背景全透明
            window.setAttributes(layoutParams);
        }
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
    }
}