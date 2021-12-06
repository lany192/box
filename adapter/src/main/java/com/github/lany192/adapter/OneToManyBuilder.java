package com.github.lany192.adapter;

import static com.github.lany192.adapter.Preconditions.checkNotNull;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;


class OneToManyBuilder<T> implements OneToManyFlow<T>, OneToManyEndpoint<T> {
    @NonNull
    private final MultiTypeAdapter adapter;
    @NonNull
    private final Class<? extends T> clazz;
    private ItemViewBinder<T, ?>[] binders;

    OneToManyBuilder(@NonNull MultiTypeAdapter adapter, @NonNull Class<? extends T> clazz) {
        this.clazz = clazz;
        this.adapter = adapter;
    }

    @Override
    @CheckResult
    @SafeVarargs
    public final @NonNull
    OneToManyEndpoint<T> to(@NonNull ItemViewBinder<T, ?>... binders) {
        checkNotNull(binders);
        this.binders = binders;
        return this;
    }


    @Override
    public void withLinker(@NonNull Linker<T> linker) {
        checkNotNull(linker);
        doRegister(linker);
    }


    @Override
    public void withClassLinker(@NonNull ClassLinker<T> classLinker) {
        checkNotNull(classLinker);
        doRegister(ClassLinkerWrapper.wrap(classLinker, binders));
    }


    private void doRegister(@NonNull Linker<T> linker) {
        for (ItemViewBinder<T, ?> binder : binders) {
            adapter.register(clazz, binder, linker);
        }
    }
}
