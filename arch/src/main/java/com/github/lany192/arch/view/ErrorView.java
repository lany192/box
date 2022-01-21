package com.github.lany192.arch.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.arch.databinding.ViewErrorBinding;
import com.github.lany192.interfaces.OnSimpleListener;

public class ErrorView extends BindingView<ViewErrorBinding> {
    private OnSimpleListener listener;

    public ErrorView(@NonNull Context context) {
        super(context);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(@Nullable AttributeSet attrs) {
        setOnClickListener(v -> {
            if (listener != null) {
                listener.onCallback();
            }
        });
    }

    public void setMessage(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            binding.message.setText(message);
        }
    }

    public void setHint(CharSequence hint) {
        if (!TextUtils.isEmpty(hint)) {
            binding.hint.setText(hint);
        }
    }

    public void setImageResource(@DrawableRes int resId) {
        binding.image.setImageResource(resId);
    }

    public void setOnRetryListener(OnSimpleListener listener) {
        this.listener = listener;
    }
}
