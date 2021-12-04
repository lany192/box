package com.github.lany192.adapter;

import androidx.annotation.NonNull;

/**
 * An ordered collection to hold the types, binders and linkers.
 */
public interface TypePool {

    /**
     * Registers a type class and its item view binder.
     *
     * @param clazz  the class of a item
     * @param binder the item view binder
     * @param linker the linker to link the class and view binder
     * @param <T>    the item data type
     */
    <T> void register(
            @NonNull Class<? extends T> clazz,
            @NonNull ItemViewBinder<T, ?> binder,
            @NonNull Linker<T> linker);

    /**
     * Unregister all items with the specified class.
     *
     * @param clazz the class of items
     * @return true if any items are unregistered from the pool
     */
    boolean unregister(@NonNull Class<?> clazz);

    /**
     * Returns the number of items in this pool.
     *
     * @return the number of items in this pool
     */
    int size();

    /**
     * For getting index of the item class. If the subclass is already registered,
     * the registered mapping is used. If the subclass is not registered, then look
     * for its parent class if is registered, if the parent class is registered,
     * the subclass is regarded as the parent class.
     *
     * @param clazz the item class.
     * @return The index of the first occurrence of the specified class
     * in this pool, or -1 if this pool does not contain the class.
     */
    int firstIndexOf(@NonNull Class<?> clazz);

    /**
     * Gets the class at the specified index.
     *
     * @param index the item index
     * @return the class at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @NonNull
    Class<?> getClass(int index);

    /**
     * Gets the item view binder at the specified index.
     *
     * @param index the item index
     * @return the item class at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @NonNull
    ItemViewBinder<?, ?> getItemViewBinder(int index);

    /**
     * Gets the linker at the specified index.
     *
     * @param index the item index
     * @return the linker at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @NonNull
    Linker<?> getLinker(int index);
}
