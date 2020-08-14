package com.github.lany192.box.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.elvishew.xlog.Logger
import com.elvishew.xlog.XLog
import com.github.lany192.box.event.NetWorkEvent
import com.github.lany192.box.utils.NetUtils
import org.greenrobot.eventbus.EventBus

/**
 * 监听网络变化
 */
class NetChangedReceiver : BroadcastReceiver() {
    var log: Logger.Builder = XLog.tag(javaClass.simpleName)

    override fun onReceive(context: Context, intent: Intent) {
        log.i("网络变化了--------------------")
        EventBus.getDefault().post(NetWorkEvent(NetUtils.isNetWorkAvailable()))
    }
}