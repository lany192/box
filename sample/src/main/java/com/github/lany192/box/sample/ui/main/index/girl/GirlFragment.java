package com.github.lany192.box.sample.ui.main.index.girl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.lany192.box.fragment.BindingFragment;
import com.github.lany192.box.items.PageAdapter;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.databinding.FragmentGirlBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GirlFragment extends BindingFragment<FragmentGirlBinding> {
    private StaggeredGridLayoutManager layoutManager;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        GirlViewModel viewModel = getFragmentViewModel(GirlViewModel.class);
        if (layoutManager != null) {
            binding.recyclerView.restoreSaveState();
        } else {
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        }
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                layoutManager.invalidateSpanAssignments();
            }
        });

        PageAdapter adapter = new PageAdapter();
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