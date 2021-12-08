package com.github.lany192.box.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.box.R;

public class LoadingView extends BaseLayout{

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
    public int getLayoutId() {
        return R.layout.view_loading;
    }

    @Override
    public void init(@Nullable AttributeSet attrs) {

    }
}
