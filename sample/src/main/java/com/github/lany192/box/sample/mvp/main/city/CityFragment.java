package com.github.lany192.box.sample.mvp.main.city;

import android.util.Log;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.bean.Result;
import com.github.lany192.box.sample.databinding.FragmentCityBinding;
import com.github.lany192.box.sample.http.ApiService;
import com.github.lany192.box.utils.JsonUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class CityFragment extends BindingFragment<FragmentCityBinding> implements CityContract.View {
    @Inject
    CityPresenter mCityPresenter;
    @Inject
    ApiService apiService;

    @Override
    public void onResume() {
        super.onResume();
        apiService.cityInfo().subscribe(new Observer<Result<List<Area>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull Result<List<Area>> result) {
                Log.i("数据:", JsonUtils.object2json(result));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i("数据:", e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }
}