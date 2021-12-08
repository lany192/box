package com.github.lany192.box.sample.ui.main.index;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.http.ApiCallback;
import com.github.lany192.box.sample.http.ApiService;
import com.github.lany192.box.viewmodel.LifecycleViewModel;
import com.hjq.toast.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class IndexViewModel extends LifecycleViewModel {
    private final MutableLiveData<List<Area>> items = new MutableLiveData<>();
    private final ApiService apiService;

    @Inject
    public IndexViewModel(ApiService apiService) {
        this.apiService = apiService;
        requestCityInfo();
    }

    public MutableLiveData<List<Area>> getItems() {
        return items;
    }

    public void requestCityInfo() {
        Log.i("TAG:", "请求城市数据接口");
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
