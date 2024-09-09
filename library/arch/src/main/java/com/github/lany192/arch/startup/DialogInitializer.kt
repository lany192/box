package com.github.lany192.arch.startup

import android.content.Context
import androidx.startup.Initializer
import com.github.lany192.log.LogUtils

class DialogInitializer : Initializer<Unit> {

    override fun create(context: Context) {
//        DialogHelper.get().init(context)
        LogUtils.i("DialogHelper完成初始化")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            LogInitializer::class.java,
            ContextInitializer::class.java
        )
    }
}

