package com.github.lany192.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.github.lany192.binding.getBinding
import java.lang.reflect.ParameterizedType

abstract class BaseDialog<VB : ViewBinding> : DialogFragment() {
    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

    /**
     * 获取第几个泛型的class
     */
    @Suppress("UNCHECKED_CAST")
    open fun <T> getClass(index: Int): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<T>
    }

    open fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB {
        return getClass<VB>(0).getBinding(inflater, container)
    }
}
