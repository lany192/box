package com.github.lany192.box.sample.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.github.lany192.arch.viewmodel.BaseViewModel;
import com.github.lany192.box.sample.data.bean.UserInfo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserViewModel extends BaseViewModel {
    private final MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();

    @Inject
    public UserViewModel() {

    }

    public MutableLiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setName(String name) {
        UserInfo info = new UserInfo();
        info.setName(name);
        userInfo.postValue(info);
    }
}
