package com.lany.box.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lany.box.adapter.MultiAdapter;
import com.lany.box.delegate.ItemDelegate;
import com.lany.box.fragment.BaseFragment;
import com.lany.box.sample.delegate.HelloDelegate;
import com.lany.box.utils.DensityUtils;
import com.lany.itemdecoration.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HelloFragment extends BaseFragment {
    @BindView(R.id.showView)
    RecyclerView mShowView;

    @Override
    protected int getLayoutId() {
        return R.layout.hello;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mShowView.setLayoutManager(manager);
        mShowView.addItemDecoration(new LinearItemDecoration(manager.getOrientation())
                .setPadding(DensityUtils.dp2px(8))
                .setColor(Color.GRAY)
                .setWidth(1));
        List<ItemDelegate> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add(new HelloDelegate("test " + i));
        }
        mShowView.setAdapter(new MultiAdapter(items));
    }
}
