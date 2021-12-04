package com.github.lany192.adapter;

import androidx.annotation.NonNull;


final class DefaultLinker<T> implements Linker<T> {

    @Override
    public int index(int position, @NonNull T t) {
        return 0;
    }
}
