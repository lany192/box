package com.github.lany192.box.multitype

import androidx.annotation.CheckResult


internal class OneToManyBuilder<T>(
    private val adapter: MultiTypeAdapter,
    private val clazz: Class<T>
) : OneToManyFlow<T>, OneToManyEndpoint<T> {

    private var delegates: Array<ItemViewDelegate<T, *>>? = null

    @SafeVarargs
    @CheckResult(suggest = "#withLinker(Linker)")
    override fun to(vararg delegates: ItemViewDelegate<T, *>) = apply {
        @Suppress("UNCHECKED_CAST")
        this.delegates = delegates as Array<ItemViewDelegate<T, *>>
    }

    @SafeVarargs
    @CheckResult(suggest = "#withLinker(Linker)")
    override fun to(vararg binders: ItemViewBinder<T, *>) = apply {
        @Suppress("UNCHECKED_CAST")
        this.delegates = binders as Array<ItemViewDelegate<T, *>>
    }

    override fun withLinker(linker: Linker<T>) {
        doRegister(linker)
    }

    override fun withJavaClassLinker(javaClassLinker: JavaClassLinker<T>) {
        withLinker(ClassLinkerBridge.toLinker(javaClassLinker, delegates!!))
    }

    private fun doRegister(linker: Linker<T>) {
        for (delegate in delegates!!) {
            adapter.register(Type(clazz, delegate, linker))
        }
    }
}
