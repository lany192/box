package com.github.lany192.box.sample.ui.main.index.city;

import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.http.ApiCallback;
import com.github.lany192.box.sample.http.ApiService;
import com.github.lany192.box.sample.ui.main.index.article.ItemsViewModel;
import com.hjq.toast.ToastUtils;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CityViewModel extends ItemsViewModel {
    private final ApiService apiService;

    @Inject
    public CityViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void request(boolean refresh) {
        showLoading(true);
        apiService.cityInfo().subscribe(new ApiCallback<List<Area>>() {

            @Override
            public void onSuccess(String msg, List<Area> items) {
                if (refresh) {
                    resetItems(items.stream().map(AreaDelegate::new).collect(Collectors.toList()));
                    finishRefresh();
                } else {
                    addItems(items.stream().map(AreaDelegate::new).collect(Collectors.toList()));
                    finishLoadMore();
                }
                showLoading(false);
            }

            @Override
            public void onFailure(String msg, int code) {
                ToastUtils.show(msg);
                stopRequest();
                showLoading(false);
            }
        });
    }
}
