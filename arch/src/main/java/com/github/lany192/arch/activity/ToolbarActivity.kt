package com.github.lany192.arch.activity;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.github.lany192.arch.databinding.ToolbarDefaultBinding;

public abstract class ToolbarActivity<VB extends ViewBinding>
        extends BindingActivity<VB, ToolbarDefaultBinding> {

    @NonNull
    @Override
    public ToolbarDefaultBinding getToolbarBinding() {
        return ToolbarDefaultBinding.inflate(getLayoutInflater());
    }
}
