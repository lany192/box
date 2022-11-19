package com.github.lany192.cropper.ui;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.lany192.cropper.CropImage;
import com.github.lany192.cropper.entity.CropResult;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;


public class ResultFragment extends Fragment {
    private PublishSubject<CropResult> resultSubject = PublishSubject.create();
    private BehaviorSubject<Boolean> attachSubject = BehaviorSubject.create();
    public static final int REQUEST_CODE = 0x00100;

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    public PublishSubject<CropResult> getResultSubject() {
        return resultSubject;
    }

    public BehaviorSubject<Boolean> getAttachSubject() {
        return attachSubject;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            resultSubject.onNext(getActivityResult(data));
        }
    }

    private CropResult getActivityResult(@Nullable Intent data) {
        return data != null ? (CropResult) data.getParcelableExtra(CropImage.CROP_IMAGE_EXTRA_RESULT) : null;
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachSubject.onNext(true);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < 23) {
            attachSubject.onNext(true);
        }
    }
}