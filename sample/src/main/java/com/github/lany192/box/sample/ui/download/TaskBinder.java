package com.github.lany192.box.sample.ui.download;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.arch.items.ItemBinder;
import com.github.lany192.box.sample.databinding.ItemTaskBinding;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.Util;

public class TaskBinder extends ItemBinder<DownloadTask, ItemTaskBinding> {
    private final OnActionListener listener;

    public TaskBinder(OnActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemTaskBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, int i) {
        return ItemTaskBinding.inflate(layoutInflater, viewGroup, false);
    }

    @Override
    public void convert(@NonNull ItemTaskBinding binding, @NonNull BaseViewHolder holder, DownloadTask task) {
        binding.name.setText(TaskUtils.INSTANCE.getTaskName(task));
        binding.status.setText(TaskUtils.INSTANCE.getStatus(task));
        binding.speed.setText(TaskUtils.INSTANCE.getSpeed(task));
        String readableOffset = Util.humanReadableBytes(TaskUtils.INSTANCE.getOffset(task), true);
        String readableTotalLength = Util.humanReadableBytes(TaskUtils.INSTANCE.getTotal(task), true);
        binding.total.setText(readableOffset + "/" + readableTotalLength);
        binding.progressBar.setProgress((int) (TaskUtils.INSTANCE.getOffset(task) * 1.0f / TaskUtils.INSTANCE.getTotal(task) * 100));
        binding.action.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClicked(task, holder.getBindingAdapterPosition());
            }
        });
    }

    public interface OnActionListener {
        void onClicked(DownloadTask downloadTask, int position);
    }
}
