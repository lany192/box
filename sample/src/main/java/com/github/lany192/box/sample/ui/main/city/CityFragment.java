package com.github.lany192.box.sample.ui.main.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentCityBinding;
import com.github.lany192.dialog.LoadingDialog;

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

        adapter = new CityAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(adapter);
        viewModel.getItems().observe(this, areas -> adapter.setNewInstance(areas));
        viewModel.getLoading().observe(this, loading -> {
//            if (loading) {
//                if (loadingDialog == null) {
//                    loadingDialog = new LoadingDialog();
//                }
//                loadingDialog.show(this);
//            } else {
//                loadingDialog.cancel();
//            }
        });
        return root;
    }
}