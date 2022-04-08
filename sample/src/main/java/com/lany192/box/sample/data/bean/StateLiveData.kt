package com.lany192.box.sample.data.bean

import androidx.lifecycle.MutableLiveData

class StateLiveData<T> : MutableLiveData<ApiResult<T>>()