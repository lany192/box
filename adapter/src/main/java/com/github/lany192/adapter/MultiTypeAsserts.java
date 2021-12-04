package com.github.lany192.adapter;

import static com.github.lany192.adapter.Preconditions.checkNotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public final class MultiTypeAsserts {

    private MultiTypeAsserts() {
        throw new AssertionError();
    }


    /**
     * Makes the exception to occur in your class for debug and index.
     *
     * @param adapter the MultiTypeAdapter
     * @param items   the items list
     * @throws BinderNotFoundException  if check failed
     * @throws IllegalArgumentException if your Items/List is empty
     */
    @SuppressWarnings("unchecked")
    public static void assertAllRegistered(@NonNull MultiTypeAdapter adapter, @NonNull List<?> items)
            throws BinderNotFoundException, IllegalArgumentException, IllegalAccessError {
        checkNotNull(adapter);
        checkNotNull(items);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Your Items/List is empty.");
        }
        for (int i = 0; i < items.size(); i++) {
            adapter.indexInTypesOf(i, items.get(0));
        }
        /* All passed. */
    }


    /**
     * @param recyclerView the RecyclerView
     * @param adapter      the MultiTypeAdapter
     * @throws IllegalAccessError       The assertHasTheSameAdapter() method must be placed after
     *                                  recyclerView.setAdapter().
     * @throws IllegalArgumentException If your recyclerView's adapter.
     *                                  is not the sample with the argument adapter.
     */
    public static void assertHasTheSameAdapter(@NonNull RecyclerView recyclerView, @NonNull MultiTypeAdapter adapter)
            throws IllegalArgumentException, IllegalAccessError {
        checkNotNull(recyclerView);
        checkNotNull(adapter);
        if (recyclerView.getAdapter() == null) {
            throw new IllegalAccessError("The assertHasTheSameAdapter() method must " +
                    "be placed after recyclerView.setAdapter()");
        }
        if (recyclerView.getAdapter() != adapter) {
            throw new IllegalArgumentException(
                    "Your recyclerView's adapter is not the sample with the argument adapter.");
        }
    }
}
