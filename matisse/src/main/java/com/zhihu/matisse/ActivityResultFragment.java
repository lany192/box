package com.zhihu.matisse;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
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
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Intent intent;
    private ActivityResultCallback<ActivityResult> resultCallback;

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (intent != null && resultCallback != null) {
            this.activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultCallback);
            this.activityResultLauncher.launch(intent);
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