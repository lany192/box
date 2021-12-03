package com.github.lany192.box.sample.ui.main.index.article;

import com.github.lany192.box.sample.bean.ArticleList;
import com.github.lany192.box.sample.http.ApiCallback;
import com.github.lany192.box.sample.http.ApiService;
import com.hjq.toast.ToastUtils;

import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ArticleViewModel extends ItemsViewModel {
    private final ApiService apiService;

    @Inject
    public ArticleViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    protected void onLazyLoad() {
        requestCityInfo();
    }

    public void requestCityInfo() {
        showLoading(true);
        apiService.getHomeArticles(1).subscribe(new ApiCallback<ArticleList>() {

            @Override
            public void onSuccess(String msg, ArticleList result) {
                showLoading(false);

                setItems(result.getDatas().stream().map(ArticleDelegate::new).collect(Collectors.toList()));
            }

            @Override
            public void onFailure(String msg, int code) {
                showLoading(false);
                ToastUtils.show(msg);
            }
        });
    }

}
