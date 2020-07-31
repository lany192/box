package com.github.lany192.box.sample.dialog;

import com.github.lany192.box.dialog.DialogFragment;
import com.github.lany192.box.sample.R;

import butterknife.OnClick;

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

    @OnClick(R.id.button1)
    void button1Clicked() {
        showEmpty();
    }

    @OnClick(R.id.button2)
    void button2Clicked() {
        showError();
    }

    @OnClick(R.id.button3)
    void button3Clicked() {
        showNoWifi();
    }
}
