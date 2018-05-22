package com.lany.box.sample;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.lany.box.activity.BaseActivity;
import com.lany.box.interfaces.SimpleFileDownloadListener;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        findViewById(R.id.download_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + "gradle-4.4-all.zip";
                String fileUrl = "https://downloads.gradle.org/distributions/gradle-4.4-all.zip";
                FileDownloader.getImpl().create(fileUrl)
                        .setPath(path)
                        .setListener(new SimpleFileDownloadListener() {

                            @Override
                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                log.i("下载进度: " + (soFarBytes / (float) totalBytes * 100) + "%");
                            }

                            @Override
                            protected void completed(BaseDownloadTask task) {
                                log.i("completed: 下载完成" + task.getUrl() + " 目标文件路径：" + task.getTargetFilePath());
                            }

                            @Override
                            protected void error(BaseDownloadTask task, Throwable e) {
                                log.i("error: " + e.getMessage());
                            }
                        }).start();
            }
        });
    }
}
