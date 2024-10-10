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

//Should be listen MainThread,Follow by lifecycle owner
@MainThread
inline fun <reified T> LifecycleOwner.collectFlowBus(
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job = FlowBus.getInstance().collectFlowBus(
    lifecycleOwner = this,
    eventName = T::class.java.name,
    minState = minActiveState,
    dispatcher = dispatcher,
    isSticky = isSticky,
    onReceived = onReceived
)

// Follow by Fragment Scope
inline fun <reified T> collectFlowBus(
    scope: Fragment,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
) = FlowBus.getInstance().collectFlowBus(
    lifecycleOwner = scope,
    eventName = T::class.java.name,
    minState = minActiveState,
    dispatcher = dispatcher,
    isSticky = isSticky,
    onReceived = onReceived
)

//Follow by collect without lifecycle
@MainThread
inline fun <reified T> collectFlowBus(
    coroutineScope: CoroutineScope,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job {
    return coroutineScope.launch {
        FlowBus.getInstance()
            .collectWithoutLifecycle(
                T::class.java.name,
                isSticky,
                onReceived
            )
    }
}

inline fun <reified T : Any> busEvent(
    valueBus: T,
    delayPost: Long = 0L
) = FlowBus.getInstance().busEvent(
    GlobalScope,
    eventName = T::class.java.name,
    valuePost = valueBus,
    delayPost = delayPost
)

inline fun <reified T : Any> LifecycleOwner.busEvent(
    valueBus: T,
    delayPost: Long = 0L
) {
    FlowBus.getInstance().busEvent(
        lifecycle.coroutineScope,
        eventName = T::class.java.name,
        valuePost = valueBus,
        delayPost = delayPost
    )
}

inline fun <reified T : Any> ViewModel.busEvent(
    valueBus: T,
    delayPost: Long = 0L
) {
    FlowBus.getInstance().busEvent(
        viewModelScope,
        eventName = T::class.java.name,
        valuePost = valueBus,
        delayPost = delayPost
    )
}

inline fun <reified T> getFlowCollectCount(event: Class<T>): Int =
    FlowBus.getInstance()
        .getEventObserverCount(event.name)

inline fun <reified T> getFlowCollectCountScope(scope: ViewModelStoreOwner, event: Class<T>) =
    FlowBus.getInstance().getEventObserverCount(event.name)

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