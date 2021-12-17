package com.github.lany192.box.sample.ui.download;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.databinding.ItemTaskBinding;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.Util;

public class TaskBinder extends ItemBinder<DownloadTask, ItemTaskBinding> {

    @Override
    public void bind(ItemTaskBinding binding, DownloadTask task, int position) {
        binding.name.setText(TaskUtils.INSTANCE.getTaskName(task) + TaskUtils.INSTANCE.getSpeed(task));
        binding.status.setText(TaskUtils.INSTANCE.getStatus(task));
        String readableOffset = Util.humanReadableBytes(TaskUtils.INSTANCE.getOffset(task), true);
        String readableTotalLength = Util.humanReadableBytes(TaskUtils.INSTANCE.getTotal(task), true);
        binding.total.setText(readableOffset + "/" + readableTotalLength);
        binding.progressBar.setProgress((int) (TaskUtils.INSTANCE.getOffset(task) * 1.0f / TaskUtils.INSTANCE.getTotal(task) * 100));
    }

    @NonNull
    @Override
    public ItemTaskBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        return ItemTaskBinding.inflate(layoutInflater, viewGroup, false);
    }
}
