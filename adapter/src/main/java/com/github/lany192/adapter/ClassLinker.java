package com.github.lany192.adapter;

import androidx.annotation.NonNull;

/**
 * An interface to link the items and binders by the classes of binders.
 */
public interface ClassLinker<T> {

    /**
     * Returns the class of your registered binders for your item.
     *
     * @param position The position in items
     * @param t        The item
     * @return The index of your registered binders
     * @see OneToManyEndpoint#withClassLinker(ClassLinker)
     */
    @NonNull
    Class<? extends ItemViewBinder<T, ?>> index(int position, @NonNull T t);
}
