package com.github.lany192.arch.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.github.lany192.arch.BuildConfig
import com.github.lany192.arch.adapter.BindingHolder
import com.github.lany192.arch.entity.ApiResult
import com.github.lany192.binding.getBinding
import com.github.lany192.utils.NetUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import java.lang.reflect.ParameterizedType

abstract class ItemBinder<T, VB : ViewBinding> : BaseBinder<T, BindingHolder<VB>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<VB> {
        return BindingHolder(getViewBinding(parent))
    }

    /**
     * 获取内容的ViewBinding实例。
     * 如果不想用反射，复写该方法，去反射。
     */
    open fun getViewBinding(parent: ViewGroup): VB {
        return getClass<VB>(1).getBinding(LayoutInflater.from(context), parent)
    }

    /**
     * 获取第几个泛型的class
     */
    open fun <T> getClass(index: Int): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>
    }

    override fun convert(holder: BindingHolder<VB>, item: T) {
        convert(holder.binding, item, holder.bindingAdapterPosition)
    }

    abstract fun convert(binding: VB, item: T, position: Int)

    fun getColor(@ColorRes colorResId: Int): Int {
        return ContextCompat.getColor(context, colorResId)
    }

    /**
     * 执行接口请求，Flow方式
     */
    fun <T> request(block: suspend () -> ApiResult<T>): Flow<ApiResult<T>> {
        return flow {
            if (NetUtils.isAvailable()) {
                emit(block())
            } else {
                log.e("请求异常:无网络")
                emit(ApiResult.network())
            }
        }.flowOn(Dispatchers.IO)
            .onCompletion { exception ->
                exception?.let {
                    log.e("onCompletion：$it")
                }
            }
            .catch {
                log.e("请求异常:${it.message}")
                if (BuildConfig.DEBUG) {
                    emit(ApiResult.network(it.message))
                } else {
                    emit(ApiResult.network())
                }
            }
    }
}