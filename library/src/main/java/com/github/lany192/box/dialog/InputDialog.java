package com.github.lany192.box.dialog;

import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.lany192.box.R;
import com.github.lany192.box.utils.SoftKeyboardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InputDialog extends DialogFragment {
    private OnInputListener mOnInputListener;
    private CharSequence hint;
    private CharSequence title;
    private CharSequence btnText;
    private CharSequence content;
    private int inputType = InputType.TYPE_NULL;
    private EditText editText;
    private List<InputFilter> filters = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_input;
    }

    @Override
    protected boolean bottomStyle() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> SoftKeyboardUtils.toggle(self), 300);
    }

    @Override
    public void cancel() {
        SoftKeyboardUtils.hideSoftInput(self, editText);
        super.cancel();
    }

    @Override
    protected void init() {
        TextView titleText = findViewById(R.id.dialog_input_title);
        editText = findViewById(R.id.dialog_input_input_edit);
        Button button = findViewById(R.id.dialog_input_ok_btn);

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        if (inputType != InputType.TYPE_NULL) {
            editText.setInputType(inputType);
        }
        if (filters.size() > 0) {
            editText.setFilters(filters.toArray(new InputFilter[0]));
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
        findViewById(R.id.dialog_input_close_btn).setOnClickListener(v -> cancel());
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public void addInputFilter(InputFilter filter) {
        filters.add(filter);
    }

    public void setMaxLength(int maxLength) {
        filters.add(new InputFilter.LengthFilter(maxLength));
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

    public interface OnInputListener {
        void onResult(CharSequence result);
    }
}
