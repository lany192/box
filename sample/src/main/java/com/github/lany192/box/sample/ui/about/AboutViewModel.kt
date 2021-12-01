package com.github.lany192.box.sample.ui.about

import com.github.lany192.box.binding.BindingActivity.onCreate
import dagger.hilt.android.AndroidEntryPoint
import com.github.lany192.box.binding.BindingActivity
import com.github.lany192.box.sample.ui.about.AboutViewModel
import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.github.lany192.box.mvvm.BaseViewModel

@HiltViewModel
class AboutViewModel @Inject constructor() : BaseViewModel()