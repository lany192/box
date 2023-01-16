package com.github.lany192.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class LoadingView extends FrameLayout {
    private int top;

    public LoadingView(@NonNull Fragment fragment, int top) {
        this(fragment.requireContext());
        this.top = top;
    }

    public LoadingView(@NonNull Context context, int top) {
        this(context, null);
        this.top = top;
    }

    public LoadingView(@NonNull Fragment fragment) {
        this(fragment.requireContext());
    }

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
