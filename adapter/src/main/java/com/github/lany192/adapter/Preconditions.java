package com.github.lany192.adapter;

import androidx.annotation.NonNull;


@SuppressWarnings("WeakerAccess")
public final class Preconditions {

    private Preconditions() {
    }

    @SuppressWarnings("ConstantConditions")
    public static @NonNull
    <T> T checkNotNull(@NonNull final T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }
}
