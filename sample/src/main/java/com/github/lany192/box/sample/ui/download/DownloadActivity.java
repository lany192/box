package com.github.lany192.box.sample.ui.download;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BindingActivity;
import com.github.lany192.box.sample.databinding.ActivityDownloadBinding;
import com.gyf.immersionbar.ImmersionBar;
import com.liulishuo.okdownload.DownloadMonitor;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.dispatcher.DownloadDispatcher;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/download")
public class DownloadActivity extends BindingActivity<ActivityDownloadBinding> {
    private DownloadAdapter downloadAdapter;
    private DownloadViewModel viewModel;

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
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        viewModel = new ViewModelProvider(this).get(DownloadViewModel.class);
        //全局配置
        DownloadDispatcher.setMaxParallelRunningCount(2);
        OkDownload.with().setMonitor(new DownloadMonitor() {
            @Override
            public void taskStart(DownloadTask task) {
            }

            @Override
            public void taskDownloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {

            }

            @Override
            public void taskDownloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @Nullable ResumeFailedCause cause) {

            }

            @Override
            public void taskEnd(DownloadTask task, EndCause cause, @Nullable Exception realCause) {

            }
        });



        downloadAdapter = new DownloadAdapter(new ArrayList<>());
        downloadAdapter.setOnItemActionListener(new DownloadAdapter.OnItemActionListener() {
            @Override
            public void onItemAction(DownloadTask task) {
            }
        });
        binding.recyclerView.setAdapter(downloadAdapter);
        //解决item刷新时，界面闪烁
        ((SimpleItemAnimator) Objects.requireNonNull(binding.recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        viewModel.getItems().observe(this, data -> {
            if (data.isChange()) {
                downloadAdapter.setData(data.getIndex(), data.getDownloadTask());
            } else {
                downloadAdapter.setNewInstance(data.getTasks());
            }
        });
        binding.button.setOnClickListener(view -> viewModel.start());
    }
}