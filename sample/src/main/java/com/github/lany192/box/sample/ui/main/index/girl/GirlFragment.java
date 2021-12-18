package com.github.lany192.box.sample.ui.main.index.girl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.lany192.arch.fragment.BindingFragment;
import com.github.lany192.arch.items.BinderAdapter;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.databinding.FragmentGirlBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GirlFragment extends BindingFragment<FragmentGirlBinding> {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        GirlViewModel viewModel =  getFragmentViewModel(GirlViewModel.class);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] first = new int[layoutManager.getSpanCount()];
                layoutManager.findFirstCompletelyVisibleItemPositions(first);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (first[0] == 1 || first[1] == 1)) {
                    //防止第一行到顶部有空白区域
                    layoutManager.invalidateSpanAssignments();
                }
            }
        });

        BinderAdapter adapter = new BinderAdapter();
        adapter.addItemBinder(String.class, new GirlBinder());
        binding.recyclerView.setAdapter(adapter);
        viewModel.getItems().observe(this, adapter::setList);
        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                adapter.setEmptyView(R.layout.view_loading);
            } else {
//                showContent();
            }
        });
        return root;
    }
}