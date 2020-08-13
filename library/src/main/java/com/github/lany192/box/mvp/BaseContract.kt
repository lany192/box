package com.github.lany192.box.mvp

interface BaseContract {
    interface View : BaseView
    interface Presenter : OnLifecycle
    interface Model : OnLifecycle
}