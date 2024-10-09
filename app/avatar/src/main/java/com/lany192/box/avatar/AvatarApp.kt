package com.lany192.box.avatar

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import androidx.appcompat.app.AppCompatDelegate
import com.alibaba.android.arouter.launcher.ARouter
import com.github.lany192.arch.Box
import com.github.lany192.utils.ContextUtils
import com.jakewharton.processphoenix.ProcessPhoenix
import com.lany192.box.router.lifecycle.ActivityLifecycle
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AvatarApp : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        Box.getInstance().attachBaseContext(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (ProcessPhoenix.isPhoenixProcess(this)) {
            return
        }
        Box.getInstance().onCreate(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        initARouter()
        //注册Activity生命周期监听
        registerActivityLifecycleCallbacks(ActivityLifecycle())
        detectNonSdkApiUsageOnAndroidP()
    }

    override fun onTerminate() {
        Box.getInstance().onTerminate()
    }

    private fun detectNonSdkApiUsageOnAndroidP() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return
        }
        val builder = VmPolicy.Builder()
        builder.detectNonSdkApiUsage()
        StrictMode.setVmPolicy(builder.build())
    }

    private fun initARouter() {
        if (ContextUtils.isDebug()) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}
