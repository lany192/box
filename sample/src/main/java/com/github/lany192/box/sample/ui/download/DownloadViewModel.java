package com.github.lany192.box.sample.ui.download;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.arch.viewmodel.LifecycleViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DownloadViewModel extends LifecycleViewModel {
    private final MutableLiveData<List<Object>> items = new MutableLiveData<>();

    @Inject
    public DownloadViewModel() {
        List<Object> tasks = new ArrayList<>();
        tasks.add(new Task("1. WeChat", "http://dldir1.qq.com/weixin/android/weixin6516android1120.apk"));
        tasks.add(new Task("2. LiuLiShuo", "https://cdn.llscdn.com/yy/files/tkzpx40x-lls-LLS-5.7-785-20171108-111118.apk"));
        tasks.add(new Task("3. Alipay", "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk"));
        tasks.add(new Task("4. QQ for Mac", "https://dldir1.qq.com/qqfile/QQforMac/QQ_V6.2.0.dmg"));
        tasks.add(new Task("6. NetEaseMusic", "http://d1.music.126.net/dmusic/CloudMusic_official_4.3.2.468990.apk"));
        tasks.add(new Task("7. NetEaseMusic for Mac", "http://d1.music.126.net/dmusic/NeteaseMusic_1.5.9_622_officialsite.dmg"));
        tasks.add(new Task("8. WeChat for Windows", "http://dldir1.qq.com/weixin/Windows/WeChatSetup.exe"));
        tasks.add(new Task("9. WeChat Work", "https://dldir1.qq.com/foxmail/work_weixin/wxwork_android_2.4.5.5571_100001.apk"));
        tasks.add(new Task("10. WeChat Work for Mac", "https://dldir1.qq.com/foxmail/work_weixin/WXWork_2.4.5.213.dmg"));

        items.postValue(tasks);

//        DownloadContext.QueueSet queueSet = new DownloadContext.QueueSet();
//        File parentFile = new File(DemoUtil.getParentFile(context), "queue");
//        queueSet.setParentPathFile(parentFile);
//        queueSet.setMinIntervalMillisCallbackProcess(200);
//        DownloadContext.Builder builder = queueSet.commit();
//        builder.setListener(new DownloadContextListener() {
//
//            @Override
//            public void taskEnd(@NonNull DownloadContext context, @NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, int remainCount) {
//
//            }
//
//            @Override
//            public void queueEnd(@NonNull DownloadContext context) {
//
//            }
//        });
    }

    public MutableLiveData<List<Object>> getItems() {
        return items;
    }
}
