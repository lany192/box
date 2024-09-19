package com.lany192.box.avatar.ui.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lany192.box.network.data.bean.UserInfo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserViewModel extends ViewModel {
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
