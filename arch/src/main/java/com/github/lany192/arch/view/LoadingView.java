package com.github.lany192.arch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.arch.databinding.ViewLoadingBinding;

public class LoadingView extends BindingLayout<ViewLoadingBinding> {

    public LoadingView(@NonNull Context context) {
        super(context);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public ViewLoadingBinding getViewBinding() {
        return ViewLoadingBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    @Override
    public void init(@Nullable AttributeSet attrs) {

    }
}
