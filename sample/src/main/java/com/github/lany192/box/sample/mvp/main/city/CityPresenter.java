package com.github.lany192.box.sample.mvp.main.city;

import android.util.Log;

import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.http.ApiCallback;
import com.github.lany192.box.sample.http.ApiService;
import com.github.lany192.box.utils.JsonUtils;
import com.hjq.toast.ToastUtils;

import java.util.List;

import javax.inject.Inject;

public class CityPresenter implements CityContract.Presenter {
    @Inject
    ApiService apiService;

    @Inject
    public CityPresenter() {
    }

    @Override
    public void requestCityInfo() {
        apiService.cityInfo().subscribe(new ApiCallback<List<Area>>() {

            @Override
            public void onSuccess(String msg, List<Area> areas) {
                Log.i("数据:", JsonUtils.object2json(areas));
            }

            @Override
            public void onFailure(String msg, int code) {
                Log.i("错误:", code + "," + msg);
                ToastUtils.show(msg);
            }
        });
    }
}
