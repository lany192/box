package com.github.lany192.box.sample.fragment.city;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.lany192.box.adapter.MultiAdapter;
import com.github.lany192.box.delegate.Delegate;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.databinding.FragmentCityBinding;
import com.github.lany192.decoration.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CityFragment extends BaseFragment<FragmentCityBinding> implements CityContract.View {
    @Inject
    CityPresenter mCityPresenter;

    private MultiAdapter mMultiAdapter;

    @NonNull
    @Override
    public FragmentConfig getConfig() {
        return FragmentConfig.builder()
                .layoutId(R.layout.fragment_city)
                .build();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.collectionView.setLayoutManager(manager);
        binding.collectionView.addItemDecoration(new LinearItemDecoration(manager.getOrientation())
                .setColor(Color.GRAY)
                .setWidth(1));
        mMultiAdapter = new MultiAdapter(new ArrayList<>());
        binding.collectionView.setAdapter(mMultiAdapter);
        onRetry();
    }

    @Override
    public void onRetry() {
        mCityPresenter.init();
    }

    @Override
    public void showCities(List<Delegate> items) {
        mMultiAdapter.setList(items);
    }
}