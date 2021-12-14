package com.github.lany192.arch.viewmodel;

import androidx.lifecycle.ViewModel;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;

public class BaseViewModel extends ViewModel {
    protected Logger.Builder log = XLog.tag(getClass().getName());
}
