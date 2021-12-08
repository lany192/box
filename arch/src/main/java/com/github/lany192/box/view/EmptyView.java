package com.github.lany192.box.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.box.databinding.ViewEmptyBinding;
import com.github.lany192.interfaces.OnSimpleListener;

public class EmptyView extends BindingLayout<ViewEmptyBinding> {
    private OnSimpleListener listener;
    private CharSequence message;
    private CharSequence hint;

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
    public void init(@Nullable AttributeSet attrs) {
        setOnClickListener(v -> {
            if (listener != null) {
                listener.onCallback();
            }
        });
        if (!TextUtils.isEmpty(message)) {
            getBinding().message.setText(message);
        }
        if (!TextUtils.isEmpty(hint)) {
            getBinding().hint.setText(hint);
        }
    }

    public void setMessage(CharSequence message) {
        this.message = message;
    }

    public void setHint(CharSequence hint) {
        this.hint = hint;
    }

    public void setOnRetryListener(OnSimpleListener listener) {
        this.listener = listener;
    }
}
