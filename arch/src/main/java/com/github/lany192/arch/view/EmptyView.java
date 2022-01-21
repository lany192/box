package com.github.lany192.arch.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.arch.databinding.ViewEmptyBinding;
import com.github.lany192.interfaces.OnSimpleListener;

public class EmptyView extends BindingLayout<ViewEmptyBinding> {
    private OnSimpleListener listener;

    public EmptyView(@NonNull Context context) {
        super(context);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public ViewEmptyBinding getViewBinding() {
        return ViewEmptyBinding.inflate(LayoutInflater.from(getContext()), this, true);
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

    public void setOnRetryListener(OnSimpleListener listener) {
        this.listener = listener;
    }
}
