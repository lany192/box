package com.github.lany192.box.sample.ui.main.city;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.http.ApiCallback;
import com.github.lany192.box.sample.http.ApiService;
import com.github.lany192.box.utils.JsonUtils;
import com.hjq.toast.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CityViewModel extends ViewModel {
    private final MutableLiveData<List<Area>> liveData = new MutableLiveData<>();
    private final ApiService apiService;

    @Inject
    public CityViewModel(ApiService apiService) {
        this.apiService = apiService;
        requestCityInfo();
    }

    public MutableLiveData<List<Area>> getLiveData() {
        return liveData;
    }

    public void requestCityInfo() {
        Log.i("TAG:", "请求城市数据接口");
        apiService.cityInfo().subscribe(new ApiCallback<List<Area>>() {

            @Override
            public void onSuccess(String msg, List<Area> areas) {
                Log.i("数据:", JsonUtils.object2json(areas));
                liveData.postValue(areas);
            }

            @Override
            public void onFailure(String msg, int code) {
                Log.i("错误:", code + "," + msg);
                ToastUtils.show(msg);
            }
        });
    }
}
