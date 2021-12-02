package com.github.lany192.box.sample.ui.main.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentDiscoverBinding;
import com.github.lany192.multitype.adapter.MultiAdapter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DiscoverFragment extends BindingFragment<FragmentDiscoverBinding> {
    private DiscoverViewModel viewModel;
    private MultiAdapter adapter;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel(DiscoverViewModel.class);

        binding.toolbar.setTitle("发现");
        ImmersionBar.with(this).titleBar(binding.toolbar).transparentStatusBar().statusBarDarkFont(true).init();
        adapter = new MultiAdapter(new ArrayList<>());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
//        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        viewModel.getItems().observe(this, items -> {
            adapter.setNewInstance(items);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return items.get(position).getSpanSize();
                }
            });
        });
        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                showLoading();
            } else {
                showContent();
            }
        });
        return root;
    }
}