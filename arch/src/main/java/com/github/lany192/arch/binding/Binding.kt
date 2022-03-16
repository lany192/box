package com.github.lany192.arch.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

internal fun <V : ViewBinding> Class<*>.getBinding(layoutInflater: LayoutInflater): V {
    return try {
        @Suppress("UNCHECKED_CAST")
        getMethod(
            "inflate",
            LayoutInflater::class.java
        ).invoke(null, layoutInflater) as V
    } catch (ex: Exception) {
        throw RuntimeException("The ViewBinding inflate function has been changed.", ex)
    }
}

internal fun <V : ViewBinding> Class<*>.getBinding(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
): V {
    return try {
        @Suppress("UNCHECKED_CAST")
        getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, layoutInflater, container, false) as V
    } catch (ex: Exception) {
        throw RuntimeException("The ViewBinding inflate function has been changed.", ex)
    }
}

internal fun Class<*>.checkMethod(): Boolean {
    return try {
        getMethod(
            "inflate",
            LayoutInflater::class.java
        )
        true
    } catch (ex: Exception) {
        false
    }
}

internal fun Any.findClass(): Class<*> {
    var javaClass: Class<*> = this.javaClass
    var result: Class<*>? = null
    while (result == null || !result.checkMethod()) {
        result = (javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments?.firstOrNull {
                if (it is Class<*>) {
                    it.checkMethod()
                } else {
                    false
                }
            } as? Class<*>
        javaClass = javaClass.superclass
    }
    return result
}


fun <VB : ViewBinding> inflateWithGeneric(
    genericOwner: Any,
    layoutInflater: LayoutInflater
): VB =
    withGenericBindingClass<VB>(genericOwner) { clazz ->
        clazz.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, layoutInflater) as VB
    }.also { binding ->
        if (genericOwner is ComponentActivity && binding is ViewDataBinding) {
            binding.lifecycleOwner = genericOwner
        }
    }

fun <VB : ViewBinding> inflateWithGeneric(genericOwner: Any, parent: ViewGroup): VB =
    inflateWithGeneric(genericOwner, LayoutInflater.from(parent.context), parent, false)

fun <VB : ViewBinding> inflateWithGeneric(
    genericOwner: Any,
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    attachToParent: Boolean
): VB =
    withGenericBindingClass<VB>(genericOwner) { clazz ->
        clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
            .invoke(null, layoutInflater, parent, attachToParent) as VB
    }.also { binding ->
        if (genericOwner is Fragment && binding is ViewDataBinding) {
            binding.lifecycleOwner = genericOwner.viewLifecycleOwner
        }
    }

fun <VB : ViewBinding> bindWithGeneric(genericOwner: Any, view: View): VB =
    withGenericBindingClass<VB>(genericOwner) { clazz ->
        clazz.getMethod("bind", View::class.java).invoke(null, view) as VB
    }.also { binding ->
        if (genericOwner is Fragment && binding is ViewDataBinding) {
            binding.lifecycleOwner = genericOwner.viewLifecycleOwner
        }
    }

private fun <VB : ViewBinding> withGenericBindingClass(
    genericOwner: Any,
    block: (Class<VB>) -> VB
): VB {
    var genericSuperclass = genericOwner.javaClass.genericSuperclass
    var superclass = genericOwner.javaClass.superclass
    while (superclass != null) {
        if (genericSuperclass is ParameterizedType) {
            genericSuperclass.actualTypeArguments.forEach {
                try {
                    return block.invoke(it as Class<VB>)
                } catch (e: NoSuchMethodException) {
                } catch (e: ClassCastException) {
                } catch (e: InvocationTargetException) {
                    throw e.targetException
                }
            }
        }
        genericSuperclass = superclass.genericSuperclass
        superclass = superclass.superclass
    }
    throw IllegalArgumentException("There is no generic of ViewBinding.")
}