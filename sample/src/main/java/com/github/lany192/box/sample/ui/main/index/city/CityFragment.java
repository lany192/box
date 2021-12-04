package com.github.lany192.box.sample.ui.main.index.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.lany192.box.items.ItemsFragment;
import com.github.lany192.box.sample.bean.Area;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends ItemsFragment<CityViewModel> {
    {
        register(Area.class, new AreaDelegate());
    }
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
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