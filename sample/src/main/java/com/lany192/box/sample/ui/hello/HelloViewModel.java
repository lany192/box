package com.lany192.box.sample.ui.hello;

import com.github.lany192.arch.items.ItemsViewModel;
import com.github.lany192.arch.utils.ListUtils;
import com.hjq.toast.ToastUtils;
import com.lany192.box.sample.data.api.ApiCallback;
import com.lany192.box.sample.data.api.ApiService;
import com.lany192.box.sample.data.bean.ArticleList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HelloViewModel extends ItemsViewModel {
    @Inject
    ApiService apiService;

    @Inject
    public HelloViewModel() {

    }

    @Override
    public void request(boolean refresh) {
        apiService.getHomeArticles(getPage())
                .subscribe(new ApiCallback<ArticleList>() {
                    @Override
                    public void onSuccess(String msg, ArticleList result) {
                        if (ListUtils.isEmpty(result.getDatas())) {
                            showEmptyView();
                        } else {
                            if (refresh) {
                                resetItems(result.getDatas());
                                refreshFinish();
                            } else {
                                addItems(result.getDatas());
                                moreLoadFinish();
                            }
                            showContentView();
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
