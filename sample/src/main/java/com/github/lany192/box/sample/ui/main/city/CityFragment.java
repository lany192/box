package com.github.lany192.box.sample.ui.main.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.databinding.FragmentCityBinding;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends BindingFragment<FragmentCityBinding> {

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        CityViewModel viewModel = getFragmentViewModel(CityViewModel.class);
        CityAdapter adapter = new CityAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(adapter);
        viewModel.getItems().observe(this, adapter::setNewInstance);
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