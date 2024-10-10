package com.github.lany192.eventbus

import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T : Any> busEvent(
    valueBus: T,
    delayPost: Long = 0L
) = FlowBus.getInstance().busEvent(
    eventName = T::class.java.name,
    valuePost = valueBus,
    delayPost = delayPost
)

inline fun <reified T : Any> busEvent(
    scope: ViewModelStoreOwner,
    valueBus: T,
    delayPost: Long = 0L
) {
    FlowBus.getInstance().busEvent(
        eventName = T::class.java.name,
        valuePost = valueBus,
        delayPost = delayPost
    )
}