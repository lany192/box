package com.github.lany192.box.sample.ui.download;

import androidx.lifecycle.MutableLiveData;

import com.liulishuo.okdownload.DownloadTask;

import java.util.ArrayList;
import java.util.List;

public class TaskLiveData extends MutableLiveData<TaskLiveData> {
    private List<DownloadTask> tasks = new ArrayList<>();
    private int index = -1;
    private DownloadTask downloadTask;
    private boolean change;

    public List<DownloadTask> getTasks() {
        return tasks;
    }

    public int getIndex() {
        return index;
    }

    public boolean isChange() {
        return change;
    }

    public DownloadTask getDownloadTask() {
        return downloadTask;
    }

    public void change(DownloadTask task) {
        this.change = true;
        index = tasks.indexOf(task);
        this.downloadTask = task;
        this.tasks.set(index, task);
        postValue(this);
    }

    public void setTasks(List<DownloadTask> items) {
        this.change = false;
        this.tasks = items;
        postValue(this);
    }
}
