package com.github.lany192.box.sample.fragment.city;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lany192.box.adapter.MultiAdapter;
import com.github.lany192.box.delegate.Delegate;
import com.github.lany192.box.fragment.DaggerFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.sample.R;
import com.github.lany192.decoration.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class CityFragment extends DaggerFragment implements CityContract.View {
    @BindView(R.id.collection_view)
    RecyclerView mCollectionView;
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
        mCollectionView.setLayoutManager(manager);
        mCollectionView.addItemDecoration(new LinearItemDecoration(manager.getOrientation())
                .setColor(Color.GRAY)
                .setWidth(1));
        mMultiAdapter = new MultiAdapter(new ArrayList<>());
        mCollectionView.setAdapter(mMultiAdapter);
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