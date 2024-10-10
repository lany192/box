package com.github.lany192.eventbus

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class FlowBus private constructor() {
    companion object {
        private const val TAG = "FlowBusCollect"
        private val instance = FlowBus()
        fun getInstance(): FlowBus {
            return instance
        }
    }

    // event without replay
    private val eventFlows: HashMap<String, MutableSharedFlow<Any>> = HashMap()

    // multiple collect look like broadcast
    private val stickyEventFlows: HashMap<String, MutableSharedFlow<Any>> = HashMap()

    fun getEventObserverCount(eventName: String): Int {
        val stickyObserverCount = stickyEventFlows[eventName]?.subscriptionCount?.value ?: 0
        val normalObserverCount = eventFlows[eventName]?.subscriptionCount?.value ?: 0
        return stickyObserverCount + normalObserverCount
    }

    private fun getFlowEventByTag(eventName: String, isSticky: Boolean): MutableSharedFlow<Any> {
        return if (isSticky) {
            stickyEventFlows[eventName]
        } else {
            eventFlows[eventName]
        } ?: MutableSharedFlow<Any>(
            replay = if (isSticky) 1 else 0,
            extraBufferCapacity = Int.MAX_VALUE
        ).also { state ->
            if (isSticky) {
                stickyEventFlows[eventName] = state
            } else {
                eventFlows[eventName] = state
            }
        }
    }

    fun <T : Any> collectFlowBus(
        lifecycleOwner: LifecycleOwner,
        eventName: String,
        minState: Lifecycle.State,
        dispatcher: CoroutineDispatcher,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ): Job {
        Log.d(TAG, "collect event : $eventName")
        return lifecycleOwner.launchWhenStateAtLeast(minState) {
            getFlowEventByTag(eventName = eventName, isSticky = isSticky).collect { valueCollect ->
                this.launch(dispatcher) {
                    invokeReceived(value = valueCollect, onReceived = onReceived)
                }
            }
        }
    }

    suspend fun <T : Any> collectWithoutLifecycle(
        eventName: String,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ) {
        getFlowEventByTag(eventName = eventName, isSticky = isSticky).collect { valueCollect ->
            invokeReceived(value = valueCollect, onReceived = onReceived)
        }
    }

    // Using : emit call to such a shared flow suspends until all subscribers receive the emitted value and returns immediately if there are no subscribers.
    // Thus, tryEmit call succeeds and returns true only if there are no subscribers (in which case the emitted value is immediately lost)
    fun busEvent(
        coroutineScope: CoroutineScope,
        eventName: String,
        valuePost: Any,
        delayPost: Long
    ) {
        Log.d(TAG, "post flow bus ==> :$eventName")
        listOfNotNull(
            getFlowEventByTag(eventName = eventName, isSticky = true),
            getFlowEventByTag(eventName = eventName, isSticky = false)
        ).forEach { flowBus ->
            coroutineScope.launch {
                delay(delayPost)
                flowBus.emit(value = valuePost)
            }
        }
    }

    fun removeStickyEvent(stickyEvent: String) = stickyEventFlows.remove(stickyEvent)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun clearStickyEvent(stickyEvent: String) = stickyEventFlows[stickyEvent]?.resetReplayCache()

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> invokeReceived(value: Any, onReceived: (T) -> Unit) {
        try {
            onReceived.invoke(value as T)
        } catch (e: ClassCastException) {
            Log.d(TAG, "class cast error when received value ==> :$value")
        } catch (e: Exception) {
            Log.d(TAG, "exception error when received value ==> :$value")
        }
    }
}