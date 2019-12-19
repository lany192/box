package com.github.lany192.box.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 监听网络变化
 */
public class NetChangedReceiver extends BroadcastReceiver {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.tag(TAG).i("网络变化了--------------------");
        EventBus.getDefault().post(new NetWorkEvent(NetUtils.isNetWorkAvailable()));
    }
}