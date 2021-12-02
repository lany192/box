package com.github.lany192.box.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.box.R;
import com.github.lany192.box.network.NetworkHelper;
import com.github.lany192.dialog.LoadingDialog;

public class BasicActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected Logger.Builder log = XLog.tag(TAG);
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(NetworkHelper.getInstance());
    }

    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.loading));
    }

    public void showLoadingDialog(CharSequence message) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog();
        }
        loadingDialog.setMessage(message);
        loadingDialog.show(this);
    }

    public void cancelLoadingDialog() {
        loadingDialog.cancel();
        loadingDialog = null;
    }
}
