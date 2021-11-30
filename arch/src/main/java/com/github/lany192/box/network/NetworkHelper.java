package com.github.lany192.box.network;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.elvishew.xlog.XLog;
import com.github.lany192.box.Box;
import com.github.lany192.box.event.NetWorkEvent;
import com.github.lany192.box.utils.NetUtils;
import com.github.lany192.box.utils.PermissionUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

public class NetworkHelper implements DefaultLifecycleObserver {
    private volatile static NetworkHelper instance = null;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && PermissionUtils.checkPermission(Box.get().getContext(), Manifest.permission.CHANGE_NETWORK_STATE)
                && PermissionUtils.checkPermission(Box.get().getContext(), Manifest.permission.WRITE_SETTINGS)) {
            ConnectivityManager manager = (ConnectivityManager) Box.get().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            manager.requestNetwork(new NetworkRequest.Builder().build(), networkCallback);
        } else {
            Box.get().getContext().registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && PermissionUtils.checkPermission(Box.get().getContext(), Manifest.permission.CHANGE_NETWORK_STATE)
                && PermissionUtils.checkPermission(Box.get().getContext(), Manifest.permission.WRITE_SETTINGS)) {
            ConnectivityManager manager = (ConnectivityManager) Box.get().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            manager.unregisterNetworkCallback(networkCallback);
        } else {
            Box.get().getContext().unregisterReceiver(receiver);
        }
    }

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
                EventBus.getDefault().post(new NetWorkEvent(NetUtils.isNetWorkAvailable()));
            }
        }
    };
}