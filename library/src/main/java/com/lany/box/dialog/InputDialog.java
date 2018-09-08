package com.lany.box.dialog;

import android.support.v4.app.BaseDialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lany192.edittext.ClearEditText;
import com.lany.box.R;

public class InputDialog extends BaseDialogFragment {
    private OnInputListener mOnInputListener;
    private CharSequence mHint;
    private CharSequence mTitle;
    private CharSequence mButtonText;
    private CharSequence mContent;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_input;
    }

    @Override
    protected void init() {
        TextView titleText = (TextView) findViewById(R.id.dialog_input_title);
        final ClearEditText editText = (ClearEditText) findViewById(R.id.dialog_input_input_edit);
        Button button = (Button) findViewById(R.id.dialog_input_ok_btn);
        if (!TextUtils.isEmpty(mContent)) {
            editText.setText(mContent);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
                if (null != mOnInputListener) {
                    mOnInputListener.onResult(editText.getText().toString());
                }
            }
        });
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