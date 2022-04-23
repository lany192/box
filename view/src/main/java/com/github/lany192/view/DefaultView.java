package com.github.lany192.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.github.lany192.interfaces.OnSimpleListener;
import com.github.lany192.utils.ImageUtils;
import com.github.lany192.view.databinding.ViewDefaultBinding;

public class DefaultView extends BindingView<ViewDefaultBinding> {
    private OnSimpleListener listener;

    public DefaultView(@NonNull Fragment fragment) {
        super(fragment.requireContext());
    }

    public DefaultView(@NonNull Context context) {
        super(context);
    }

    public DefaultView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    public void setImage(Object object) {
        ImageUtils.show(binding.image, object);
    }

    public void setMessage(@StringRes int message) {
        setMessage(getResources().getString(message));
    }

    public void setMessage(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            binding.message.setText(message);
        }
    }

    public void setHint(@StringRes int hint) {
        setHint(getResources().getString(hint));
    }

    public void setHint(CharSequence hint) {
        if (!TextUtils.isEmpty(hint)) {
            binding.hint.setText(hint);
            binding.hint.setVisibility(View.VISIBLE);
        } else {
            binding.hint.setVisibility(View.GONE);
        }
    }

    public void setOnRetryListener(OnSimpleListener listener) {
        this.listener = listener;
    }
}
