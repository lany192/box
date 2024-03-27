package com.lany192.box.sample.ui.settings.feedback;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ContentActivity;
import com.github.lany192.arch.utils.ListUtils;
import com.github.lany192.utils.JsonUtils;
import com.hjq.toast.Toaster;
import com.lany192.box.sample.databinding.ActivityFeedbackBinding;

import dagger.hilt.android.AndroidEntryPoint;
import github.leavesczy.matisse.DefaultMediaFilter;
import github.leavesczy.matisse.GlideImageEngine;
import github.leavesczy.matisse.Matisse;
import github.leavesczy.matisse.MatisseContract;
import github.leavesczy.matisse.MediaStoreCaptureStrategy;
import github.leavesczy.matisse.MediaType;

@AndroidEntryPoint
@Route(path = "/ui/feedback")
public class FeedbackActivity extends ContentActivity<ActivityFeedbackBinding> {

    private final ActivityResultLauncher<Matisse> matisseLauncher = registerForActivityResult(new MatisseContract(), result -> {
        if (!ListUtils.isEmpty(result)) {
            Toaster.show(JsonUtils.object2json(result));
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.button.setOnClickListener(v -> {
            matisseLauncher.launch(
                    new Matisse(1,
                            new GlideImageEngine(),
                            MediaType.ImageAndVideo.INSTANCE,
                            true,
                            new DefaultMediaFilter(),
                            new MediaStoreCaptureStrategy())
            );
        });
    }

}