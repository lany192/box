package com.github.lany192.box.sample.ui.download;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BindingActivity;
import com.github.lany192.arch.items.BinderAdapter;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.arch.view.EmptyView;
import com.github.lany192.box.sample.databinding.ActivityDownloadBinding;
import com.gyf.immersionbar.ImmersionBar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/download")
public class DownloadActivity extends BindingActivity<ActivityDownloadBinding> {
    private DownloadViewModel viewModel;
    private final BinderAdapter binderAdapter = new BinderAdapter();

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .titleBar(binding.toolbar)
                .init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = getViewModel(DownloadViewModel.class);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        binderAdapter.addItemBinder(Task.class, new TaskBinder());
        binding.recyclerView.setAdapter(binderAdapter);
        viewModel.getItems().observe(this, tasks -> {
            if (ListUtils.isEmpty(tasks)) {
                EmptyView emptyView = new EmptyView(this);
                emptyView.setMessage("没有发现数据");
                emptyView.setHint("重新点击试试");
                binderAdapter.setEmptyView(emptyView);
            } else {
                binderAdapter.setNewInstance(tasks);
            }
        });
    }
}