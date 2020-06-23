package com.github.lany192.box.sample.dialog;

import android.content.Context;

import com.github.lany192.box.dialog.BottomDialog;
import com.github.lany192.box.sample.R;

public class HelloDialog extends BottomDialog {

    public HelloDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_hello;
    }
}
