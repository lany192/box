package com.github.lany192.box.sample.ui.main.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.databinding.FragmentCityBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends BindingFragment<FragmentCityBinding> {
    private CityViewModel viewModel;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CityViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.collectionView.setLayoutManager(layoutManager);
        viewModel.getLiveData().observe(this, new Observer<List<Area>>() {
            @Override
            public void onChanged(List<Area> areas) {
                binding.collectionView.setAdapter(new BaseQuickAdapter<Area, BaseViewHolder>(R.layout.item_area, areas) {

                    @Override
                    protected void convert(@NonNull BaseViewHolder holder, Area area) {
                        holder.setText(R.id.item_area_title, area.getName());
                    }
                });
            }
        });
        return root;
    }
}