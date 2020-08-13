package com.github.lany192.box.mvp

/**
 * 分页列表页视图
 */
interface ListPageView<T> : BaseView {
    fun addItems(items: List<T>?)
    fun fail()
    fun stop()
    fun end()
    fun gotoTop()
    var items: List<T>?
}