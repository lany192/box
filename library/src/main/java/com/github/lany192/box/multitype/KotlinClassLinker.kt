package com.github.lany192.box.multitype

import kotlin.reflect.KClass

/**
 * An interface to link the items and delegates by the classes of delegates.
 *
 *
 */
interface KotlinClassLinker<T> {

    /**
     * Returns the class of your registered delegates for your item.
     *
     * @param position The position in items
     * @param item The item
     * @return The index of your registered delegates
     * @see OneToManyEndpoint.withJavaClassLinker
     */
    fun index(position: Int, item: T): KClass<out ItemViewDelegate<T, *>>
}
