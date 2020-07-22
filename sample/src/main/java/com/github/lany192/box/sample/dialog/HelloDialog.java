package com.github.lany192.box.sample.dialog;

import com.github.lany192.box.dialog.DialogFragment;
import com.github.lany192.box.sample.R;

public class HelloDialog extends DialogFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_hello;
    }

    @Override
    protected boolean bottomStyle() {
        return true;
    }

    @Override
    protected void init() {

    }
}
