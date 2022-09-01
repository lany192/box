package com.github.lany192.arch.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.elvishew.xlog.XLog;
import com.github.lany192.arch.event.NetWorkEvent;
import com.github.lany192.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

public class NetworkHelper implements DefaultLifecycleObserver {
    private volatile static NetworkHelper instance = null;
    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {

        @Override
        public void onLost(Network network) {
            super.onLost(network);
            EventBus.getDefault().post(new NetWorkEvent(false));
        }

        @Override
        public void onAvailable(Network network) {
            super.onAvailable(network);
            EventBus.getDefault().post(new NetWorkEvent(true));
        }
    };
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
                XLog.tag("TAG").i("网络变化了--------------------");
                EventBus.getDefault().post(new NetWorkEvent(NetUtils.isAvailable()));
            }
        }
    };

    private NetworkHelper() {
    }

    public static NetworkHelper getInstance() {
        if (instance == null) {
            synchronized (NetworkHelper.class) {
                if (instance == null) {
                    instance = new NetworkHelper();
                }
            }
        }
        return instance;
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
//                && PermissionUtils.hasPermission(ContextUtils.getContext(), Manifest.permission.CHANGE_NETWORK_STATE)
//                && PermissionUtils.hasPermission(ContextUtils.getContext(), Manifest.permission.WRITE_SETTINGS)) {
//            ConnectivityManager manager = (ConnectivityManager) ContextUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//            manager.requestNetwork(new NetworkRequest.Builder().build(), networkCallback);
//        } else {
//            ContextUtils.getContext().registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
//                && PermissionUtils.hasPermission(ContextUtils.getContext(), Manifest.permission.CHANGE_NETWORK_STATE)
//                && PermissionUtils.hasPermission(ContextUtils.getContext(), Manifest.permission.WRITE_SETTINGS)) {
//            ConnectivityManager manager = (ConnectivityManager) ContextUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//            manager.unregisterNetworkCallback(networkCallback);
//        } else {
//            ContextUtils.getContext().unregisterReceiver(receiver);
//        }
    }
}