package com.github.lany192.box.items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lany192.adapter.ItemViewBinder;
import com.github.lany192.adapter.MultiTypeAdapter;
import com.github.lany192.box.databinding.FragmentItemsBinding;
import com.github.lany192.box.fragment.BindingFragment;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class ItemsFragment<VM extends ItemsViewModel>
        extends BindingFragment<FragmentItemsBinding> {
    protected VM viewModel;
    private HashMap<Class, ItemViewBinder> hashMap=new HashMap<>();

    public RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        return layoutManager;
    }
    
    public void register(Class clazz, ItemViewBinder binder){
        hashMap.put(clazz,binder);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        viewModel = getFragmentViewModel((Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        MultiTypeAdapter adapter = new MultiTypeAdapter();

        Iterator entries = hashMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Class, ItemViewBinder> entry = (Map.Entry) entries.next();
            adapter.register(entry.getKey(),entry.getValue());
        }
        
        
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
        binding.refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                viewModel.onLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                viewModel.onRefresh();
            }
        });
        viewModel.getRefreshState().observe(this, refreshing -> {
            if (!refreshing) {
                binding.refreshLayout.finishRefresh();
            }
        });
        viewModel.getLoadMoreState().observe(this, moreLoading -> {
            if (!moreLoading) {
                binding.refreshLayout.finishLoadMore();
            }
        });
        viewModel.getItems().observe(this, data -> {
            if (data.isRefresh()) {
                adapter.setItems(data.getItems());
            } else {
                adapter.setItems(data.getItems());
            }
        });

//        viewModel.getLoading().observe(this, loading -> {
//            if (loading) {
//                showLoading();
//            } else {
//                showContent();
//            }
//        });
        return root;
    }


}