package com.github.lany192.adapter;

import androidx.annotation.NonNull;

import java.util.Arrays;


final class ClassLinkerWrapper<T> implements Linker<T> {

    private final @NonNull
    ClassLinker<T> classLinker;
    private final @NonNull
    ItemViewBinder<T, ?>[] binders;


    private ClassLinkerWrapper(
            @NonNull ClassLinker<T> classLinker,
            @NonNull ItemViewBinder<T, ?>[] binders) {
        this.classLinker = classLinker;
        this.binders = binders;
    }


    static @NonNull
    <T> ClassLinkerWrapper<T> wrap(
            @NonNull ClassLinker<T> classLinker,
            @NonNull ItemViewBinder<T, ?>[] binders) {
        return new ClassLinkerWrapper<T>(classLinker, binders);
    }


    @Override
    public int index(int position, @NonNull T t) {
        Class<?> userIndexClass = classLinker.index(position, t);
        for (int i = 0; i < binders.length; i++) {
            if (binders[i].getClass().equals(userIndexClass)) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException(
                String.format("%s is out of your registered binders'(%s) bounds.",
                        userIndexClass.getName(), Arrays.toString(binders))
        );
    }
}
