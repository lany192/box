package com.github.lany192.box.sample.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.github.lany192.box.adapter.MultiAdapter;
import com.github.lany192.box.delegate.Delegate;
import com.github.lany192.box.delegate.DividerDelegate;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.interfaces.OnRefreshMoreListener;
import com.github.lany192.box.sample.MockUtils;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.delegate.ImageDelegate;
import com.github.lany192.box.view.CollectionView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

public class HelloFragment extends BaseFragment {
    @BindView(R.id.collection_view)
    CollectionView mCollectionView;

    private int page = 1;
    private MultiAdapter adapter;

    @NonNull
    @Override
    public FragmentConfig getConfig() {
        return FragmentConfig.builder()
                .layoutId(R.layout.hello)
                .toolBarLayoutId(R.layout.toolbar_hello)
                .coverStyle(true)
                .build();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mCollectionView.setToolbarHeight(getToolbarHeight());
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        mCollectionView.setLayoutManager(manager);
        adapter = new MultiAdapter(new ArrayList<>(), manager);
        mCollectionView.setAdapter(adapter);
        mCollectionView.setOnRefreshMoreListener(new OnRefreshMoreListener() {
            @Override
            public void onRefresh() {
                List<String> images = MockUtils.getItems(1);
                List<Delegate> items = images.stream().map(ImageDelegate::new).collect(Collectors.toList());
                adapter.setList(items);
            }

            @Override
            public void onLoadMore() {
                page++;
                List<String> images = MockUtils.getItems(page);
                List<Delegate> items = images.stream().map(ImageDelegate::new).collect(Collectors.toList());
                if (items.size() < 20) {
                    items.add(new DividerDelegate());
                    mCollectionView.setEnableLoadMore(false);
                }
                adapter.addData(items);
            }
        });
        List<String> images = MockUtils.getItems(1);
        List<Delegate> items = images.stream().map(ImageDelegate::new).collect(Collectors.toList());
        adapter.setList(items);
    }
}
