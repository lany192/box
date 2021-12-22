package com.github.lany192.box.sample.ui.main.index.city;

import com.github.lany192.arch.items.PageListViewModel;
import com.github.lany192.box.sample.data.api.ApiCallback;
import com.github.lany192.box.sample.data.api.ApiService;
import com.github.lany192.box.sample.data.bean.Area;
import com.hjq.toast.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CityViewModel extends PageListViewModel {
    private final ApiService apiService;

    @Inject
    public CityViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void request(boolean refresh) {
        apiService.cityInfo().subscribe(new ApiCallback<List<Area>>() {

            @Override
            public void onSuccess(String msg, List<Area> items) {
                if (refresh) {
                    resetItems(items);
                    finishRefresh();
                } else {
                    addItems(items);
                    finishLoadMore();
                }
            }

            @Override
            public void onFailure(String msg, int code) {
                ToastUtils.show(msg);
                finishRequest();
            }
        });
    }
}
