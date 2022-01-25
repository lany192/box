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

object ViewBindingUtil {

    @JvmStatic
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

    @JvmStatic
    fun <VB : ViewBinding> inflateWithGeneric(genericOwner: Any, parent: ViewGroup): VB =
        inflateWithGeneric(genericOwner, LayoutInflater.from(parent.context), parent, false)

    @JvmStatic
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

    @JvmStatic
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
}
