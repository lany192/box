package com.github.lany192.box.sample.ui.main.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.box.fragment.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentIndexBinding;
import com.gyf.immersionbar.ImmersionBar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IndexFragment extends BindingFragment<FragmentIndexBinding> {
    private IndexViewModel viewModel;

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .navigationBarColor(android.R.color.holo_orange_light)
                .titleBar(binding.tabLayout)
                .init();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel(IndexViewModel.class);
        IndexAdapter indexAdapter=new IndexAdapter(requireActivity());
        binding.viewpager.setAdapter(indexAdapter);
        binding.tabLayout.setViewPager2(binding.viewpager, indexAdapter.getTitles());
        return root;
    }
}
