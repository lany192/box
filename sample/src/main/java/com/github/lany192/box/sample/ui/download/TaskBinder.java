package com.github.lany192.box.sample.ui.download;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.databinding.ItemTaskBinding;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.Util;

public class TaskBinder extends ItemBinder<DownloadTask, ItemTaskBinding> {
    private final OnActionListener listener;

    public TaskBinder(OnActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void bind(@NonNull ItemTaskBinding binding, DownloadTask task, int position) {
        binding.name.setText(TaskUtils.INSTANCE.getTaskName(task));
        binding.status.setText(TaskUtils.INSTANCE.getStatus(task));
        binding.speed.setText(TaskUtils.INSTANCE.getSpeed(task));
        String readableOffset = Util.humanReadableBytes(TaskUtils.INSTANCE.getOffset(task), true);
        String readableTotalLength = Util.humanReadableBytes(TaskUtils.INSTANCE.getTotal(task), true);
        binding.total.setText(readableOffset + "/" + readableTotalLength);
        binding.progressBar.setProgress((int) (TaskUtils.INSTANCE.getOffset(task) * 1.0f / TaskUtils.INSTANCE.getTotal(task) * 100));
        binding.action.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClicked(task, position);
            }
        });
    }

    public interface OnActionListener {
        void onClicked(DownloadTask downloadTask, int position);
    }
}
