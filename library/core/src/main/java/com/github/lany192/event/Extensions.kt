package com.github.lany192.event

import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@MainThread
inline fun <reified T> LifecycleOwner.subscribeEvent(
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) {
    EventBus.getDefault().subscribe(
        lifecycleOwner = this,
        eventName = T::class.java.name,
        minState = minActiveState,
        dispatcher = dispatcher,
        isSticky = isSticky,
        onReceived = onReceived
    )
}

@OptIn(DelicateCoroutinesApi::class)
@MainThread
inline fun <reified T> subscribeEvent(
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job {
    return GlobalScope.launch(Dispatchers.Main.immediate) {
        EventBus.getDefault().subscribe(T::class.java.name, isSticky, onReceived)
    }
}

inline fun <reified T : Any> LifecycleOwner.postEvent(
    event: T,
    delayPost: Long = 0L
) {
    EventBus.getDefault().post(
        lifecycle.coroutineScope,
        eventName = T::class.java.name,
        event = event,
        delayPost = delayPost
    )
}

inline fun <reified T : Any> ViewModel.postEvent(
    event: T,
    delayPost: Long = 0L
) {
    EventBus.getDefault().post(
        viewModelScope,
        eventName = T::class.java.name,
        event = event,
        delayPost = delayPost
    )
}

@OptIn(DelicateCoroutinesApi::class)
inline fun <reified T : Any> postEvent(
    event: T,
    delayPost: Long = 0L
) = EventBus.getDefault().post(
    GlobalScope,
    eventName = T::class.java.name,
    event = event,
    delayPost = delayPost
)

inline fun <reified T> getSubscriberCount(): Int =
    EventBus.getDefault().getEventObserverCount(T::class.java.name)

fun <T> LifecycleOwner.launchWhenStateAtLeast(
    minState: Lifecycle.State,
    block: suspend CoroutineScope.() -> T
): Job = lifecycleScope.launch {
    lifecycle.repeatOnLifecycle(minState) {
        block()
    }
}