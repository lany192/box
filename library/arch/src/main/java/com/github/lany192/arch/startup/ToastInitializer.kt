package com.github.lany192.arch.startup

import android.content.Context
import androidx.startup.Initializer
import com.github.lany192.arch.R
import com.github.lany192.log.LogUtils
import com.hjq.toast.Toaster

class ToastInitializer : Initializer<Unit> {

    override fun create(context: Context) {
//        Toaster.init(context)
//        Toaster.setView(R.layout.toast_view)
        LogUtils.i("Toast完成初始化")
    }

    override fun dependencies(): MutableList<Class<Initializer<*>>> {
        return mutableListOf()
    }
}

