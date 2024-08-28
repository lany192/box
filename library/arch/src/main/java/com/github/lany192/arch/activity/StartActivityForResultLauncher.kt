package com.github.lany192.arch.activity

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts

open class BaseActivityResultLauncher<I, O>(
    caller: ActivityResultCaller,
    contract: ActivityResultContract<I, O>
) {
    private var launcher: ActivityResultLauncher<I>
    private lateinit var callback: ActivityResultCallback<O>

    init {
        launcher = caller.registerForActivityResult(contract) { result ->
            // ---- 1----
            callback.onActivityResult(result)
        }
    }

    // 在调用launch的时候把callback传入进去，然后结果回调会走 1 处代码
    open fun launch(
        input: I,
        callback: ActivityResultCallback<O>
    ) {
        this.callback = callback
        launcher.launch(input)
    }
}

class StartActivityForResultLauncher(caller: ActivityResultCaller) :
    BaseActivityResultLauncher<Intent, ActivityResult>(
        caller, ActivityResultContracts.StartActivityForResult()
    )

interface OnResultCallback {
    fun onResult(result: ActivityResult)
}
