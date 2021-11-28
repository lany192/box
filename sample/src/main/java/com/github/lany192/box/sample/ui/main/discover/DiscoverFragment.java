package com.github.lany192.box.sample.ui.main.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.dialog.LoadingDialog;
import com.github.lany192.box.sample.databinding.FragmentDiscoverBinding;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DiscoverFragment extends BindingFragment<FragmentDiscoverBinding> {
    private DiscoverViewModel viewModel;
    private DiscoverAdapter adapter;
    private LoadingDialog loadingDialog;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        getLifecycle().addObserver(viewModel);

        binding.toolbar.setTitle("发现");

        adapter = new DiscoverAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(adapter);
        viewModel.getItems().observe(this, items -> adapter.setNewInstance(items));
        viewModel.getLoading().observe(this, loading -> {
            if (loading) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog();
                }
                if (!loadingDialog.isAdded()) {
                    loadingDialog.show(getParentFragmentManager(), "TAG" + System.currentTimeMillis());
                }
            } else {
                if (loadingDialog != null && loadingDialog.isAdded()) {
                    loadingDialog.cancel();
                    loadingDialog = null;
                }
            }
        });
        return root;
    }
}