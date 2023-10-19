package com.github.lany192.arch.startup

import android.content.Context
import androidx.startup.Initializer
import com.github.lany192.arch.CrashHelper
import com.github.lany192.arch.utils.DeviceId
import com.github.lany192.log.LogUtils

class CrashInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        DeviceId.getInstance().grantedSDPermission()
        CrashHelper.getInstance()
        LogUtils.i("CrashHelper初始化")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            LogInitializer::class.java,
            ContextInitializer::class.java,
            KVInitializer::class.java
        )
    }
}

