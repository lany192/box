package com.github.lany192.arch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewbinding.ViewBinding;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

public abstract class BindingLayout<VB extends ViewBinding> extends FrameLayout implements LifecycleOwner {
    private final LifecycleRegistry registry = new LifecycleRegistry(this);
    protected Logger.Builder log = XLog.tag(getClass().getSimpleName());
    protected VB binding;

    public BindingLayout(@NonNull Context context) {
        this(context, null, 0);
    }

    public BindingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BindingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = getViewBinding();
        addView(binding.getRoot());
        init(attrs);
    }

    public abstract VB getViewBinding();

    public <T extends ViewModel> T getViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider((ViewModelStoreOwner) getContext()).get(modelClass);
    }

    public <T extends ViewModel> T getAndroidViewModel(@NonNull Class<T> modelClass) {
        return new ViewModelProvider((ViewModelStoreOwner) getContext().getApplicationContext()).get(modelClass);
    }

    public abstract void init(@Nullable AttributeSet attrs);

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registry.setCurrentState(Lifecycle.State.CREATED);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        registry.setCurrentState(Lifecycle.State.DESTROYED);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_START);
            registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        } else if (visibility == GONE || visibility == INVISIBLE) {
            registry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
            registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        }
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return registry;
    }
}
