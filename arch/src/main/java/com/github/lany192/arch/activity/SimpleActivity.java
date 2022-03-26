package com.github.lany192.arch.activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import com.github.lany192.arch.databinding.ToolbarDefaultBinding;

public abstract class SimpleActivity<VM extends ViewModel, VB extends ViewBinding>
        extends ModelActivity<VM, VB, ToolbarDefaultBinding> {

    @NonNull
    @Override
    public ToolbarDefaultBinding getToolbarBinding() {
        return ToolbarDefaultBinding.inflate(getLayoutInflater());
    }
}
