package com.github.lany192.event

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.github.lany192.extension.log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * 基于协程Flow实现的EventBus
 */
class EventBus private constructor() {
    companion object {
        private val instance = EventBus()
        fun getDefault(): EventBus {
            return instance
        }
    }

    private val eventFlows: HashMap<String, MutableSharedFlow<Any>> = HashMap()
    private val stickyEventFlows: HashMap<String, MutableSharedFlow<Any>> = HashMap()

    fun getEventObserverCount(eventName: String): Int {
        val stickyObserverCount = stickyEventFlows[eventName]?.subscriptionCount?.value ?: 0
        val normalObserverCount = eventFlows[eventName]?.subscriptionCount?.value ?: 0
        return stickyObserverCount + normalObserverCount
    }

    private fun getMutableSharedFlowByTag(
        eventName: String,
        isSticky: Boolean
    ): MutableSharedFlow<Any> {
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

    fun <T : Any> subscribe(
        lifecycleOwner: LifecycleOwner,
        eventName: String,
        minState: Lifecycle.State,
        dispatcher: CoroutineDispatcher,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ) {
        val job = lifecycleOwner.launchWhenStateAtLeast(minState) {
            log("$lifecycleOwner 添加订阅事件: $eventName")
            getMutableSharedFlowByTag(eventName = eventName, isSticky = isSticky)
                .collect {
                    this.launch(dispatcher) {
                        invokeReceived(value = it, onReceived = onReceived)
                    }
                }
        }
        lifecycleOwner.launchWhenStateAtLeast(Lifecycle.State.DESTROYED) {
            log("$lifecycleOwner 取消订阅事件: $eventName")
            job.cancel()
        }
    }

    suspend fun <T : Any> subscribe(
        eventName: String,
        isSticky: Boolean,
        onReceived: (T) -> Unit
    ) {
        getMutableSharedFlowByTag(eventName = eventName, isSticky = isSticky).collect {
            invokeReceived(value = it, onReceived = onReceived)
        }
    }

    // 使用 emit 调用这样的共享流（SharedFlow）会挂起，直到所有订阅者接收到发出的值；如果没有订阅者，则会立即返回。因此，只有在没有订阅者的情况下（在这种情况下，发出的值将立即丢失），tryEmit 调用才会成功并返回 true。
    fun post(
        coroutineScope: CoroutineScope,
        eventName: String,
        event: Any,
        delayPost: Long
    ) {
        log("发送事件:$eventName")
        listOfNotNull(
            getMutableSharedFlowByTag(eventName = eventName, isSticky = true),
            getMutableSharedFlowByTag(eventName = eventName, isSticky = false)
        ).forEach { flowBus ->
            coroutineScope.launch {
                delay(delayPost)
                flowBus.emit(value = event)
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
        } catch (e: Exception) {
            log("接受值时发生异常 :$value")
        }
    }
}