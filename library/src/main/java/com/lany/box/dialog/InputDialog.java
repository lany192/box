package com.lany.box.dialog;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lany.box.R;
import com.lany.box.utils.PhoneUtils;

import java.util.Objects;

public class InputDialog extends com.lany.box.fragment.DialogFragment {
    private OnInputListener mOnInputListener;
    private CharSequence mHint;
    private CharSequence mTitle;
    private CharSequence mButtonText;
    private CharSequence mContent;

    private EditText editText;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_input;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.width = PhoneUtils.getDeviceWidth();
            window.setAttributes(lp);
        }
        new Handler().postDelayed(() -> {
            InputMethodManager manager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }, 300);
    }

    @Override
    protected void init() {
        TextView titleText = findViewById(R.id.dialog_input_title);
        editText = findViewById(R.id.dialog_input_input_edit);
        Button button = findViewById(R.id.dialog_input_ok_btn);

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        if (!TextUtils.isEmpty(mContent)) {
            editText.setText(mContent);
            editText.setSelection(mContent.length());
        }
        if (!TextUtils.isEmpty(mHint)) {
            editText.setHint(mHint);
        }
        if (!TextUtils.isEmpty(mTitle)) {
            titleText.setText(mTitle);
            titleText.setVisibility(View.VISIBLE);
        } else {
            titleText.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mButtonText)) {
            button.setText(mButtonText);
        }
        button.setOnClickListener(v -> {
            getDialog().cancel();
            if (null != mOnInputListener) {
                mOnInputListener.onResult(Objects.requireNonNull(editText.getText()).toString());
            }
        });
        findViewById(R.id.dialog_input_close_btn).setOnClickListener(v -> cancel());
    }

    public interface OnInputListener {
        void onResult(CharSequence result);
    }

    public void setOnInputListener(OnInputListener listener) {
        this.mOnInputListener = listener;
    }

    public void setButtonText(CharSequence text) {
        this.mButtonText = text;
    }

    public void setHint(CharSequence hint) {
        this.mHint = hint;
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    public void setContent(CharSequence content) {
        this.mContent = content;
    }
}