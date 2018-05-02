package com.lany.box.dialog;


import android.support.v4.app.BaseDialogFragment;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lany.box.R;

public class LoadingDialog extends BaseDialogFragment {
    private CharSequence mMessage;
    private TextView mMsgText;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_loading_layout;
    }

    @Override
    protected void init() {
        mMsgText = (TextView) findViewById(R.id.loading_dialog_msg_text);
        if (!TextUtils.isEmpty(mMessage)) {
            mMsgText.setText(mMessage);
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