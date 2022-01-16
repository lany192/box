package com.github.lany192.box.sample.ui.download;

import androidx.annotation.NonNull;

import com.github.lany192.arch.items.BindingItemBinder;
import com.github.lany192.box.sample.databinding.ItemTaskBinding;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.Util;

public class TaskBinder extends BindingItemBinder<DownloadTask, ItemTaskBinding> {
    private final OnActionListener listener;

    public TaskBinder(OnActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void convert(@NonNull BindingHolder<ItemTaskBinding> holder, DownloadTask task) {
        holder.getBinding().name.setText(TaskUtils.INSTANCE.getTaskName(task));
        holder.getBinding().status.setText(TaskUtils.INSTANCE.getStatus(task));
        holder.getBinding().speed.setText(TaskUtils.INSTANCE.getSpeed(task));
        String readableOffset = Util.humanReadableBytes(TaskUtils.INSTANCE.getOffset(task), true);
        String readableTotalLength = Util.humanReadableBytes(TaskUtils.INSTANCE.getTotal(task), true);
        holder.getBinding().total.setText(readableOffset + "/" + readableTotalLength);
        holder.getBinding().progressBar.setProgress((int) (TaskUtils.INSTANCE.getOffset(task) * 1.0f / TaskUtils.INSTANCE.getTotal(task) * 100));
        holder.getBinding().action.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClicked(task, holder.getBindingAdapterPosition());
            }
        });
    }

    public interface OnActionListener {
        void onClicked(DownloadTask downloadTask, int position);
    }
}
