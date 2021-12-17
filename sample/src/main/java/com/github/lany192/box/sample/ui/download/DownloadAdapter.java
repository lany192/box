package com.github.lany192.box.sample.ui.download;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.github.lany192.box.sample.R;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.Util;

import java.util.List;

public class DownloadAdapter extends BaseQuickAdapter<DownloadTask, BaseViewHolder> {
    private OnItemActionListener listener;

    public DownloadAdapter(@Nullable List<DownloadTask> tasks) {
        super(R.layout.item_task, tasks);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, DownloadTask downloadTask) {
        // task name
        holder.setText(R.id.name, TaskUtils.INSTANCE.getTaskName(downloadTask) + TaskUtils.INSTANCE.getSpeed(downloadTask));
        // task state
        holder.setText(R.id.download_status, TaskUtils.INSTANCE.getStatus(downloadTask));
        // task state
        holder.setText(R.id.download_status, TaskUtils.INSTANCE.getStatus(downloadTask));

        String readableOffset = Util.humanReadableBytes(TaskUtils.INSTANCE.getOffset(downloadTask), true);
        String readableTotalLength = Util.humanReadableBytes(TaskUtils.INSTANCE.getTotal(downloadTask), true);

        holder.setText(R.id.download_total, readableOffset + "/" + readableTotalLength);

        ProgressBar progressBar = holder.getView(R.id.progress_bar);
        progressBar.setProgress((int) (TaskUtils.INSTANCE.getOffset(downloadTask) * 1.0f / TaskUtils.INSTANCE.getTotal(downloadTask) * 100));


//        val tvTotal:TextView = holder.getView(R.id.download_total)
//        val tvSpeed:TextView = holder.getView(R.id.download_speed)
//        val tvPercent:TextView = holder.getView(R.id.download_percent)
//        val flDelete:FrameLayout = holder.getView(R.id.delete)
    }

    @Override
    protected void setOnItemClick(@NonNull View v, int position) {
        if (listener != null) {
            listener.onItemAction(getItem(position));
        }
    }

    public void setOnItemActionListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    public interface OnItemActionListener {
        void onItemAction(DownloadTask task);
    }
}
