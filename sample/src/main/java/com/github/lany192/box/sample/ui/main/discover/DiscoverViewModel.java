package com.github.lany192.box.sample.ui.main.discover;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elvishew.xlog.XLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DiscoverViewModel extends ViewModel implements DefaultLifecycleObserver {
    private final MutableLiveData<List<String>> items = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(true);


    @Inject
    public DiscoverViewModel() {
        requestCityInfo();
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<List<String>> getItems() {
        return items;
    }

    public void requestCityInfo() {
        loading.postValue(true);
        List<String> images = new ArrayList<>();
        images.add("https://hbimg.huabanimg.com/9c7b8969e5367d70a4c854f9aa079a76df4471a1190837-dd65JF_fw658/format/webp");
        images.add("https://hbimg.huabanimg.com/e31425fa46a743a3dbf6d8c17537fa3cb49fa90a2007db-GntkV2_fw658/format/webp");
        images.add("https://hbimg.huabanimg.com/b33e1e43d63c67d94dd29da844b34cd61d1b48761a98ec-VbMRjB_fw658/format/webp");
        images.add("https://hbimg.huabanimg.com/f8a89497f69af76dfecfa2626b7f8e7fd1de434919959d-FVr4jB_fw658/format/webp");
        images.add("https://hbimg.huabanimg.com/fb95cb5b653671063c46059eef75ec6851dfd65b5ef43-9dFusw_fw658/format/webp");
        images.add("https://hbimg.huabanimg.com/f686b52dc260d153003ca05c71cecd43686c14787ddae8-ySCn0w_fw658/format/webp");
        images.add("https://hbimg.huabanimg.com/6b494213e4132613c9eb89215347c82b66453e5ca37bb-nBfPC7_fw658/format/webp");
        images.add("https://hbimg.huabanimg.com/5b8ea8580475b23443f4f4449b1db93c655148de10b5c1-U58xWF_fw658/format/webp");
        images.add("https://hbimg.huabanimg.com/991880d1919e2b77d6493eed235d4ce838c696652158b3-0BekcG_fw658/format/webp");
        images.add("https://hbimg.huabanimg.com/e8cde189321a974090eef2d27861c46bada6eee0110aba-axLQ4L_fw658/format/webp");
        images.add("https://hbimg.huabanimg.com/759ad84955b7be26f9a1c0121d76feb0a973a5e7f9375-Ki1IRC_fw658/format/webp");
        loading.postValue(false);
        items.postValue(images);
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        XLog.i("onResume");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        XLog.i("onPause");
    }
}
