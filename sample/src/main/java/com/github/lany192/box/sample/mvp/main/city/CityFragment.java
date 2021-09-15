package com.github.lany192.box.sample.mvp.main.city;

import android.util.Log;

import com.github.lany192.box.binding.BindingFragment;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.databinding.FragmentCityBinding;
import com.github.lany192.box.sample.http.ApiService;
import com.github.lany192.box.sample.http.ApiCallback;
import com.github.lany192.box.utils.JsonUtils;
import com.hjq.toast.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CityFragment extends BindingFragment<FragmentCityBinding> implements CityContract.View {
    @Inject
    CityPresenter mCityPresenter;
    @Inject
    ApiService apiService;

    @Override
    public void onResume() {
        super.onResume();
        apiService.cityInfo().subscribe(new ApiCallback<List<Area>>() {

            @Override
            public void onSuccess(String msg, List<Area> areas) {
                Log.i("数据:", JsonUtils.object2json(areas));
            }

            @Override
            public void onFailure(String msg, int code) {
                ToastUtils.show(msg);
            }
        });
    }
}