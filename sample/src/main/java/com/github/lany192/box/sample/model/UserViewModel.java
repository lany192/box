package com.github.lany192.box.sample.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.github.lany192.box.sample.bean.UserInfo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserViewModel extends AndroidViewModel {
    private final MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();

    @Inject
    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setName(String name) {
        UserInfo info = new UserInfo();
        info.setName(name);
        info.setId(userInfo.getValue().getId());
        userInfo.postValue(info);
    }
}
