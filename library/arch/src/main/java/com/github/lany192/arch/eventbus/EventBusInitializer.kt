package com.github.lany192.arch.eventbus

import android.app.Application
import com.github.lany192.arch.eventbus.util.ILogger

object EventBusInitializer {

    lateinit var application: Application

    var logger: ILogger? = null

    fun init(application: Application, logger: ILogger? = null) {
        EventBusInitializer.application = application
        this.logger = logger
    }
}