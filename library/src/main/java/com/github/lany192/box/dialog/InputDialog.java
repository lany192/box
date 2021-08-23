package com.github.lany192.box.dialog;

import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

import com.github.lany192.box.databinding.DialogInputBinding;
import com.github.lany192.box.utils.SoftKeyboardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InputDialog extends DialogFragment<DialogInputBinding> {
    private OnInputListener mOnInputListener;
    private CharSequence hint;
    private CharSequence title;
    private CharSequence btnText;
    private CharSequence content;
    private int inputType = InputType.TYPE_NULL;
    private final List<InputFilter> filters = new ArrayList<>();

    @Override
    protected boolean bottomStyle() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> SoftKeyboardUtils.toggle(requireActivity()), 300);
    }

    @Override
    public void cancel() {
        SoftKeyboardUtils.hideSoftInput(requireActivity(), binding.inputEdit);
        super.cancel();
    }

    @Override
    protected void init() {
        binding.inputEdit.setFocusable(true);
        binding.inputEdit.setFocusableInTouchMode(true);
        binding.inputEdit.requestFocus();
        if (inputType != InputType.TYPE_NULL) {
            binding.inputEdit.setInputType(inputType);
        }
        if (filters.size() > 0) {
            binding.inputEdit.setFilters(filters.toArray(new InputFilter[0]));
        }
        if (!TextUtils.isEmpty(content)) {
            binding.inputEdit.setText(content);
            binding.inputEdit.setSelection(content.length());
        }
        if (!TextUtils.isEmpty(hint)) {
            binding.inputEdit.setHint(hint);
        }
        if (!TextUtils.isEmpty(title)) {
            binding.title.setText(title);
        } else {
            binding.title.setText("");
        }
        if (!TextUtils.isEmpty(btnText)) {
            binding.okBtn.setText(btnText);
        }
        binding.okBtn.setOnClickListener(v -> {
            getDialog().cancel();
            if (null != mOnInputListener) {
                mOnInputListener.onResult(Objects.requireNonNull(binding.inputEdit.getText()).toString());
            }
        });
        binding.closeBtn.setOnClickListener(v -> cancel());
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
