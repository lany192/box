package com.github.lany192.box.sample.ui.main.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.lany192.box.fragment.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentDiscoverBinding;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DiscoverFragment extends BindingFragment<FragmentDiscoverBinding> {
    private DiscoverViewModel viewModel;

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .navigationBarColor(android.R.color.holo_red_light)
                .titleBar(binding.toolbar)
                .init();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel(DiscoverViewModel.class);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView){
        ImmersionBar.with(requireActivity()).statusBarDarkFont(true).init();
        DiscoverAdapter adapter = new DiscoverAdapter(new ArrayList<>());

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域
            }
        });
        viewModel.getItems().observe(this, adapter::setList);
        viewModel.getLoading().observe(this, loading -> {
//            if (loading) {
//                showLoading();
//            } else {
//                showContent();
//            }
        });
    }

}