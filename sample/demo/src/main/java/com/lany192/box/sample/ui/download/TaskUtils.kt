package com.lany192.box.sample.ui.download

import com.liulishuo.okdownload.DownloadTask

object TaskUtils {
    private const val KEY_STATUS = 0
    private const val KEY_OFFSET = 1
    private const val KEY_TOTAL = 2
    private const val KEY_NAME = 3
    private const val KEY_SPEED = 4

    fun DownloadTask.saveSpeed(speed: String) {
        addTag(KEY_SPEED, speed)
    }

    fun DownloadTask.getSpeed(): String {
        val speed = getTag(KEY_SPEED)
        return if (speed != null) speed as String else "0KB/s"
    }

    fun DownloadTask.saveStatus(status: String) {
        addTag(KEY_STATUS, status)
    }

    fun DownloadTask.getStatus(): String? {
        val status = getTag(KEY_STATUS)
        return if (status != null) status as String else null
    }

    fun DownloadTask.saveOffset(offset: Long) {
        addTag(KEY_OFFSET, offset)
    }

    fun DownloadTask.getOffset(): Long {
        val offset = getTag(KEY_OFFSET)
        return if (offset != null) offset as Long else 0
    }

    fun DownloadTask.saveTotal(total: Long) {
        addTag(KEY_TOTAL, total)
    }

    fun DownloadTask.getTotal(): Long {
        val total = getTag(KEY_TOTAL)
        return if (total != null) total as Long else 0
    }

    fun DownloadTask.saveTaskName(name: String) {
        addTag(KEY_NAME, name)
    }

    fun DownloadTask.getTaskName(): String? {
        val taskName = getTag(KEY_NAME)
        return if (taskName != null) taskName as String else null
    }

    fun DownloadTask.getTags(): String {
        val name = getTaskName()
        val speed = getSpeed()
        val status = getStatus()
        val offset = getOffset()
        val total = getTotal()
        return "$name,$status,$speed,$offset/$total"
    }

    fun DownloadTask.clearProceedTask() {
        removeTag(KEY_STATUS)
        removeTag(KEY_OFFSET)
        removeTag(KEY_TOTAL)
        removeTag(KEY_OFFSET)
        removeTag(KEY_SPEED)
    }
}