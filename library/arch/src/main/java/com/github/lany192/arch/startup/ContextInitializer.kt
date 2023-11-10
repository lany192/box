package com.github.lany192.arch.startup

import android.content.Context
import androidx.startup.Initializer
import com.github.lany192.utils.ContextUtils


class ContextInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        ContextUtils.setApplicationContext(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(LogInitializer::class.java)
    }
}

