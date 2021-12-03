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
    public void request(boolean refresh) {
        apiService.getHomeArticles(getPage()).subscribe(new ApiCallback<ArticleList>() {

            @Override
            public void onSuccess(String msg, ArticleList result) {
                if (refresh) {
                    resetItems(result.getDatas().stream().map(ArticleDelegate::new).collect(Collectors.toList()));
                    finishRefresh();
                } else {
                    addItems(result.getDatas().stream().map(ArticleDelegate::new).collect(Collectors.toList()));
                    finishLoadMore();
                }
            }

            @Override
            public void onFailure(String msg, int code) {
                ToastUtils.show(msg);
                stopRequest();
            }
        });
    }
}
