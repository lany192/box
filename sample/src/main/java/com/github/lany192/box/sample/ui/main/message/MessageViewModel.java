package com.github.lany192.box.sample.ui.main.message;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.arch.viewmodel.LifecycleViewModel;
import com.github.lany192.box.sample.data.api.ApiCallback;
import com.github.lany192.box.sample.data.api.ApiService;
import com.github.lany192.box.sample.data.bean.Area;
import com.hjq.toast.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MessageViewModel extends LifecycleViewModel {
    private final MutableLiveData<List<Area>> items = new MutableLiveData<>();
    private final ApiService apiService;

    @Inject
    public MessageViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public MutableLiveData<List<Area>> getItems() {
        return items;
    }

    @Override
    protected void onLazyLoad() {
        requestCityInfo();
    }

    public void requestCityInfo() {
        log.i("请求城市数据接口");
        showLoading(true);
        apiService.cityInfo().subscribe(new ApiCallback<List<Area>>() {

            @Override
            public void onSuccess(String msg, List<Area> areas) {
                showLoading(false);
                items.postValue(areas);
            }

            @Override
            public void onFailure(String msg, int code) {
                showLoading(false);
                ToastUtils.show(msg);
            }
        });
    }

}
