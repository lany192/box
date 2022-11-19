package com.github.lany192.scanner;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 用于回调结果，中转界面
 *
 * @author Administrator
 */
public class ActivityResultFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private final ActivityResultLauncher<Intent> activityResultLauncher;
    private final Intent intent;

    public ActivityResultFragment(Intent intent, ActivityResultCallback<ActivityResult> resultCallback) {
        this.intent = intent;
        this.activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultCallback);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activityResultLauncher.launch(intent);
    }

    public void start(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.findFragmentByTag(TAG) != null) {
            transaction.remove(this);
        }
        transaction.add(this, TAG);
        transaction.commit();
    }
}