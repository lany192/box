package com.github.lany192.box.interfaces

import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener

class SimpleFileDownloadListener : FileDownloadListener() {
    override fun pending(
        task: BaseDownloadTask,
        soFarBytes: Int,
        totalBytes: Int
    ) {
    }

    override fun progress(
        task: BaseDownloadTask,
        soFarBytes: Int,
        totalBytes: Int
    ) {
    }

    override fun completed(task: BaseDownloadTask) {}
    override fun paused(
        task: BaseDownloadTask,
        soFarBytes: Int,
        totalBytes: Int
    ) {
    }

    override fun error(task: BaseDownloadTask, e: Throwable) {}
    override fun warn(task: BaseDownloadTask) {}
}