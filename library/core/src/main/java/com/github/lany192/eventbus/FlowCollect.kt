package com.github.lany192.eventbus

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
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
): Job = FlowBus.getDefault().collectFlowBus(
    lifecycleOwner = this,
    eventName = T::class.java.name,
    minState = minActiveState,
    dispatcher = dispatcher,
    isSticky = isSticky,
    onReceived = onReceived
)

inline fun <reified T> subscribeEvent(
    scope: Fragment,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) = FlowBus.getDefault().collectFlowBus(
    lifecycleOwner = scope,
    eventName = T::class.java.name,
    minState = minActiveState,
    dispatcher = dispatcher,
    isSticky = isSticky,
    onReceived = onReceived
)

@MainThread
inline fun <reified T> subscribeEvent(
    coroutineScope: CoroutineScope,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job {
    return coroutineScope.launch {
        FlowBus.getDefault()
            .collectWithoutLifecycle(
                T::class.java.name,
                isSticky,
                onReceived
            )
    }
}

inline fun <reified T : Any> postEvent(
    event: T,
    delayPost: Long = 0L
) = FlowBus.getDefault().busEvent(
    GlobalScope,
    eventName = T::class.java.name,
    valuePost = event,
    delayPost = delayPost
)

inline fun <reified T : Any> LifecycleOwner.postEvent(
    event: T,
    delayPost: Long = 0L
) {
    FlowBus.getDefault().busEvent(
        lifecycle.coroutineScope,
        eventName = T::class.java.name,
        valuePost = event,
        delayPost = delayPost
    )
}

inline fun <reified T : Any> ViewModel.postEvent(
    event: T,
    delayPost: Long = 0L
) {
    FlowBus.getDefault().busEvent(
        viewModelScope,
        eventName = T::class.java.name,
        valuePost = event,
        delayPost = delayPost
    )
}

inline fun <reified T> getFlowCollectCount(event: Class<T>): Int =
    FlowBus.getDefault().getEventObserverCount(event.name)

inline fun <reified T> getFlowCollectCountScope(scope: ViewModelStoreOwner, event: Class<T>) =
    FlowBus.getDefault().getEventObserverCount(event.name)

/**
 * repeatOnLifecycle
 */
fun <T> LifecycleOwner.launchWhenStateAtLeast(
    minState: Lifecycle.State,
    block: suspend CoroutineScope.() -> T
): Job = lifecycleScope.launch {
    lifecycle.repeatOnLifecycle(minState) {
        block()
    }
}