package com.github.lany192.box.sample.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.lany192.box.adapter.MultiAdapter;
import com.github.lany192.box.config.FragmentConfig;
import com.github.lany192.box.delegate.ItemDelegate;
import com.github.lany192.box.fragment.DaggerFragment;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.delegate.AreaDelegate;
import com.github.lany192.box.sample.http.ApiService;
import com.github.lany192.box.sample.http.HttpCallback;
import com.github.lany192.box.utils.DensityUtils;
import com.github.lany192.decoration.LinearDecoration;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class CityFragment extends DaggerFragment {
    @BindView(R.id.showView)
    RecyclerView mShowView;
    @Inject
    ApiService apiService;

    private MultiAdapter mMultiAdapter;

    @NonNull
    @Override
    protected FragmentConfig getConfig(FragmentConfig config) {
        return config.layoutId(R.layout.fragment_city);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mShowView.setLayoutManager(manager);
        mShowView.addItemDecoration(new LinearDecoration(manager.getOrientation())
                .setColor(Color.GRAY)
                .setWidth(1));
        mMultiAdapter = new MultiAdapter(new ArrayList<>());
        mShowView.setAdapter(mMultiAdapter);
        request();
    }

    @Override
    public void onRetry() {
        request();
    }

    private void request(){
        showLoading();
        apiService.getCityInfo().enqueue(new HttpCallback<List<Area>>() {
            @Override
            public void onSuccess(String msg, List<Area> areas) {
                List<ItemDelegate> items = new ArrayList<>();
                for (Area area : areas) {
                    items.add(new AreaDelegate(area));
                }
                mMultiAdapter.setNewData(items);
                showContent();
            }

            @Override
            public void onFailure(int code, Throwable e) {
                showError(e.getMessage());
                e.printStackTrace();
                ToastUtils.show("请求异常" + e.getMessage());
            }
        });
    }
}
