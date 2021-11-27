package com.github.lany192.box.sample.ui.main.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.dialog.LoadingDialog;
import com.github.lany192.box.sample.databinding.FragmentCityBinding;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends BindingFragment<FragmentCityBinding> {
    private CityViewModel viewModel;
    private CityAdapter adapter;
    private LoadingDialog loadingDialog;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CityViewModel.class);
        getLifecycle().addObserver(viewModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.collectionView.setLayoutManager(layoutManager);
        adapter = new CityAdapter(new ArrayList<>());
        binding.collectionView.setAdapter(adapter);
        viewModel.getLiveData().observe(this, areas -> adapter.setNewInstance(areas));
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