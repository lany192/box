package com.github.lany192.box.dialog;


import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.lany192.box.fragment.DialogFragment;
import com.github.lany192.box.R;

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
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;//设置背景全透明
            window.setAttributes(windowParams);
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    public void setMessage(CharSequence message) {
        this.mMessage = message;
    }
}