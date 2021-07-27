package com.github.lany192.box.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 用于回调结果，中转界面
 *
 * @author Administrator
 */
public class ActivityResultFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private Intent intent;
    private ActivityResultCallback<ActivityResult> resultCallback;

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (intent != null && resultCallback != null) {
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultCallback).launch(intent);
        } else {
            Log.e(TAG, "没有配置Intent或者ActivityResultCallback,请检查！");
        }
    }

    public void start(FragmentActivity activity, ActivityResultCallback<ActivityResult> resultCallback) {
        this.resultCallback = resultCallback;
        start(activity.getSupportFragmentManager());
    }

    public void start(Fragment fragment, ActivityResultCallback<ActivityResult> resultCallback) {
        this.resultCallback = resultCallback;
        start(fragment.getParentFragmentManager());
    }

    private void start(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.findFragmentByTag(TAG) != null) {
            transaction.remove(this);
        }
        transaction.add(this, TAG);
        transaction.commitAllowingStateLoss();
    }
}