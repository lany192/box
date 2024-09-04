package com.github.lany192.arch.activity

import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
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
            callback.onActivityResult(result)
        }
    }

    open fun launch(input: I, callback: ActivityResultCallback<O>) {
        this.callback = callback
        this.launcher.launch(input)
    }
}

class StartActivityForResultLauncher(caller: ActivityResultCaller) :
    BaseActivityResultLauncher<Intent, ActivityResult>(
        caller, ActivityResultContracts.StartActivityForResult()
    )

class MediaPickerLauncher(caller: ActivityResultCaller) :
    BaseActivityResultLauncher<PickVisualMediaRequest, Uri?>(
        caller, ActivityResultContracts.PickVisualMedia()
    )

fun interface OnResultCallback<O> {
    fun onResult(result: O?)
}
