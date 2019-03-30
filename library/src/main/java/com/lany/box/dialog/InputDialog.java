package com.lany.box.dialog;

import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lany.box.R;
import com.lany.box.fragment.DialogFragment;
import com.lany.box.utils.PhoneUtils;
import com.lany.box.utils.SoftKeyboardUtils;

import java.util.Objects;

public class InputDialog extends DialogFragment {
    private OnInputListener mOnInputListener;
    private CharSequence hint;
    private CharSequence title;
    private CharSequence btnText;
    private CharSequence content;
    private int inputType = InputType.TYPE_NULL;
    private int maxLength = Integer.MAX_VALUE;
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
        new Handler().postDelayed(() -> SoftKeyboardUtils.toggle(editText.getContext()), 300);
    }

    @Override
    public void cancel() {
        SoftKeyboardUtils.hide(editText.getContext());
        super.cancel();
    }

    @Override
    protected void init() {
        TextView titleText = findViewById(com.lany.box.R.id.dialog_input_title);
        editText = findViewById(com.lany.box.R.id.dialog_input_input_edit);
        Button button = findViewById(com.lany.box.R.id.dialog_input_ok_btn);

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        if (inputType != InputType.TYPE_NULL) {
            editText.setInputType(inputType);
        }
        if (maxLength > 0 && maxLength != Integer.MAX_VALUE) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        if (!TextUtils.isEmpty(content)) {
            editText.setText(content);
            editText.setSelection(content.length());
        }
        if (!TextUtils.isEmpty(hint)) {
            editText.setHint(hint);
        }
        if (!TextUtils.isEmpty(title)) {
            titleText.setText(title);
        } else {
            titleText.setText("");
        }
        if (!TextUtils.isEmpty(btnText)) {
            button.setText(btnText);
        }
        button.setOnClickListener(v -> {
            getDialog().cancel();
            if (null != mOnInputListener) {
                mOnInputListener.onResult(Objects.requireNonNull(editText.getText()).toString());
            }
        });
        findViewById(com.lany.box.R.id.dialog_input_close_btn).setOnClickListener(v -> cancel());
    }

    public interface OnInputListener {
        void onResult(CharSequence result);
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setOnInputListener(OnInputListener listener) {
        this.mOnInputListener = listener;
    }

    public void setButtonText(CharSequence text) {
        this.btnText = text;
    }

    public void setHint(CharSequence hint) {
        this.hint = hint;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public void setContent(CharSequence content) {
        this.content = content;
    }
}