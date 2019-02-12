package com.lany.box.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elvishew.xlog.XLog;
import com.lany.box.event.NetWorkEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 监听网络变化
 */
public class NetChangedReceiver extends BroadcastReceiver {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.tag(TAG).i("网络变化了--------------------");
        EventBus.getDefault().post(new NetWorkEvent());
    }
}