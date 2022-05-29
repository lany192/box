package com.github.lany192.arch.interfaces;

import android.text.Editable;
import android.text.TextWatcher;

public interface SimpleTextWatcher extends TextWatcher {

    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    default void afterTextChanged(Editable s) {

    }
}
