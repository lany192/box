package com.github.lany192.arch.startup

import android.content.Context
import androidx.startup.Initializer
import com.github.lany192.log.LogUtils

class LogInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        LogUtils.init(context, true)
        LogUtils.i("Log完成初始化")
    }

    override fun dependencies(): MutableList<Class<Initializer<*>>> {
        return mutableListOf()
    }
}

