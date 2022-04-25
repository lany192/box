package com.lany192.box.sample.ui.download;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BoxActivity;
import com.github.lany192.arch.items.ItemsAdapter;
import com.lany192.box.sample.databinding.ActivityDownloadBinding;
import com.liulishuo.okdownload.DownloadMonitor;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.dispatcher.DownloadDispatcher;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/download")
public class DownloadActivity extends BoxActivity<DownloadViewModel, ActivityDownloadBinding> {
    private final ItemsAdapter itemsAdapter = new ItemsAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        itemsAdapter.addItemBinder(DownloadTask.class, new TaskBinder((downloadTask, position) -> viewModel.start(downloadTask)));
        binding.recyclerView.setAdapter(itemsAdapter);
        //解决item刷新时，界面闪烁
        ((SimpleItemAnimator) Objects.requireNonNull(binding.recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        viewModel.getItems().observe(this, data -> {
            if (data.isChange()) {
                itemsAdapter.setData(data.getIndex(), data.getDownloadTask());
            } else {
                itemsAdapter.setList(data.getTasks());
            }
        });
    }
}