package com.github.lany192.adapter;

import androidx.annotation.NonNull;

/**
 * End-operators for one-to-many.
 */
public interface OneToManyEndpoint<T> {

    /**
     * Sets a linker to link the items and binders by array index.
     *
     * @param linker the row linker
     * @see Linker
     */
    void withLinker(@NonNull Linker<T> linker);

    /**
     * Sets a class linker to link the items and binders by the class instance of binders.
     *
     * @param classLinker the class linker
     * @see ClassLinker
     */
    void withClassLinker(@NonNull ClassLinker<T> classLinker);
}
