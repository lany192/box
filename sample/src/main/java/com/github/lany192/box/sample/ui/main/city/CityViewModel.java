package com.github.lany192.box.sample.ui.main.city;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elvishew.xlog.XLog;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.http.ApiCallback;
import com.github.lany192.box.sample.http.ApiService;
import com.github.lany192.box.sample.mvvm.BaseViewModel;
import com.hjq.toast.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CityViewModel extends BaseViewModel implements DefaultLifecycleObserver {
    private final MutableLiveData<List<Area>> items = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(true);
    private final ApiService apiService;

    @Inject
    public CityViewModel(ApiService apiService) {
        this.apiService = apiService;
        requestCityInfo();
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<List<Area>> getItems() {
        return items;
    }

    public void requestCityInfo() {
        Log.i("TAG:", "请求城市数据接口");
        loading.postValue(true);
        apiService.cityInfo().subscribe(new ApiCallback<List<Area>>() {

            @Override
            public void onSuccess(String msg, List<Area> areas) {
                loading.postValue(false);
                items.postValue(areas);
            }

            @Override
            public void onFailure(String msg, int code) {
                loading.postValue(false);
                ToastUtils.show(msg);
            }
        });
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
