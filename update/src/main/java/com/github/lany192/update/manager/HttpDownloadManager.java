package com.github.lany192.update.manager;

import androidx.annotation.NonNull;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.update.listener.OnDownloadListener;
import com.github.lany192.update.utils.Constant;
import com.github.lany192.update.utils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpDownloadManager extends BaseHttpDownloadManager {
    private final Logger.Builder log = XLog.tag(getClass().getSimpleName());
    private final String downloadPath;
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(Constant.THREAD_NAME);
            return thread;
        }
    });
    private String apkUrl;
    private String apkName;
    private boolean shutdown = false;
    private OnDownloadListener listener;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //删除之前的安装包
            if (FileUtil.fileExists(downloadPath, apkName)) {
                FileUtil.delete(downloadPath, apkName);
            }
            fullDownload();
        }
    };

    public HttpDownloadManager(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    @Override
    public void download(String apkUrl, String apkName, OnDownloadListener listener) {
        this.apkUrl = apkUrl;
        this.apkName = apkName;
        this.listener = listener;
        executor.execute(runnable);
    }

    @Override
    public void cancel() {
        shutdown = true;
    }

    @Override
    public void release() {
        listener = null;
        executor.shutdown();
    }

    /**
     * 全部下载
     */
    private void fullDownload() {
        if (listener != null) listener.start();
        try {
            URL url = new URL(apkUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(Constant.HTTP_TIME_OUT);
            con.setConnectTimeout(Constant.HTTP_TIME_OUT);
            con.setRequestProperty("Accept-Encoding", "identity");
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = con.getInputStream();
                int length = con.getContentLength();
                int len;
                //当前已下载完成的进度
                int progress = 0;
                byte[] buffer = new byte[1024 * 2];
                File file = FileUtil.createFile(downloadPath, apkName);
                FileOutputStream stream = new FileOutputStream(file);
                while ((len = is.read(buffer)) != -1 && !shutdown) {
                    //将获取到的流写入文件中
                    stream.write(buffer, 0, len);
                    progress += len;
                    if (listener != null) listener.downloading(length, progress);
                }
                if (shutdown) {
                    //取消了下载 同时再恢复状态
                    shutdown = false;
                    log.d("fullDownload: 取消了下载");
                    if (listener != null) listener.cancel();
                } else {
                    if (listener != null) listener.done(file);
                }
                //完成io操作,释放资源
                stream.flush();
                stream.close();
                is.close();
                //重定向
            } else if (con.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM ||
                    con.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
                apkUrl = con.getHeaderField("Location");
                con.disconnect();
                log.d("fullDownload: 当前地址是重定向Url，定向后的地址：" + apkUrl);
                fullDownload();
            } else {
                if (listener != null)
                    listener.error(new SocketTimeoutException("下载失败：Http ResponseCode = " + con.getResponseCode()));
            }
            con.disconnect();
        } catch (Exception e) {
            if (listener != null) listener.error(e);
            e.printStackTrace();
        }
    }

}
