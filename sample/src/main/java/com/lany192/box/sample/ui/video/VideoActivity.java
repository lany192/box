package com.lany192.box.sample.ui.video;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BoxActivity;
import com.github.lany192.video.JZMediaExo;
import com.github.lany192.video.Jzvd;
import com.github.lany192.video.JzvdStd;
import com.lany192.box.sample.databinding.ActivityVideoBinding;

import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/video")
public class VideoActivity extends BoxActivity<VideoViewModel, ActivityVideoBinding> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Jzvd.releaseAllVideos();
        binding.playerView.setUp("https://media.w3.org/2010/05/sintel/trailer.mp4", "测试", JzvdStd.SCREEN_NORMAL, JZMediaExo.class);
        binding.playerView.startVideo();
    }
}