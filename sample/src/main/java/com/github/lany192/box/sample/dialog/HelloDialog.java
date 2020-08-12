package com.github.lany192.box.sample.dialog;

import android.os.Handler;

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

    @OnClick(R.id.button0)
    void button0Clicked() {
        showLoadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cancelLoadingDialog();
            }
        }, 2000);
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
