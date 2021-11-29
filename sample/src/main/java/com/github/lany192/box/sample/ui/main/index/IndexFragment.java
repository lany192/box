package com.github.lany192.box.sample.ui.main.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentIndexBinding;
import com.gyf.immersionbar.ImmersionBar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IndexFragment extends BindingFragment<FragmentIndexBinding> {
    private IndexViewModel viewModel;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(IndexViewModel.class);
        getLifecycle().addObserver(viewModel);
        ImmersionBar.with(this).titleBar(binding.tabLayout).statusBarDarkFont(true).init();
        binding.viewpager.setAdapter(new IndexAdapter(requireActivity()));
        binding.tabLayout.setViewPager2(binding.viewpager, new CharSequence[]{"精选", "测试", "测试", "测试"});
        return root;
    }
}
