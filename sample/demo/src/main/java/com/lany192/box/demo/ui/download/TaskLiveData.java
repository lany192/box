package com.lany192.box.demo.ui.download;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.log.LogUtils;
import com.liulishuo.okdownload.DownloadTask;

import java.util.ArrayList;
import java.util.List;

public class TaskLiveData extends MutableLiveData<TaskLiveData> {
    private List<Object> tasks = new ArrayList<>();
    private int index = -1;
    private DownloadTask downloadTask;
    private boolean change;

    public List<Object> getTasks() {
        return tasks;
    }

    public void setTasks(List<?> items) {
        this.change = false;
        this.tasks = (List<Object>) items;
        postValue(this);
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
        LogUtils.i("任务id:" + task.getId());
        this.change = true;
        index = tasks.indexOf(task);
        this.downloadTask = task;
        this.tasks.set(index, task);
        postValue(this);
    }
}
