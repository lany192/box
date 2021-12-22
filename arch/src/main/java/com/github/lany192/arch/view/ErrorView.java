package com.github.lany192.arch.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.arch.R;
import com.github.lany192.interfaces.OnSimpleListener;

public class ErrorView extends BaseLayout {
    private OnSimpleListener listener;
    private TextView msgText;
    private TextView hintText;
    private ImageView imageView;

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
    public int getLayoutId() {
        return R.layout.view_error;
    }

    @Override
    public void init(@Nullable AttributeSet attrs) {
        setOnClickListener(v -> {
            if (listener != null) {
                listener.onCallback();
            }
        });
        msgText = findViewById(R.id.message);
        hintText = findViewById(R.id.hint);
        imageView = findViewById(R.id.image);
    }

    public void setMessage(CharSequence message) {
        if (msgText != null && !TextUtils.isEmpty(message)) {
            msgText.setText(message);
        }
    }

    public void setHint(CharSequence hint) {
        if (hintText != null && !TextUtils.isEmpty(hint)) {
            hintText.setText(hint);
        }
    }

    public void setImageResource(@DrawableRes int resId) {
        if (imageView != null) {
            imageView.setImageResource(resId);
        }
    }

    public void setOnRetryListener(OnSimpleListener listener) {
        this.listener = listener;
    }
}
