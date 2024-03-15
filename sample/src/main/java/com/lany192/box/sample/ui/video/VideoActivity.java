package com.lany192.box.sample.ui.video;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.OptIn;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.exoplayer.upstream.DefaultAllocator;
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BoxActivity;
import com.github.lany192.video.JZMediaExo;
import com.github.lany192.video.Jzvd;
import com.github.lany192.video.JzvdStd;
import com.lany192.box.sample.R;
import com.lany192.box.sample.databinding.ActivityVideoBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/video")
public class VideoActivity extends BoxActivity<VideoViewModel, ActivityVideoBinding> {
    private final String url = "https://media.w3.org/2010/05/sintel/trailer.mp4";

    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Jzvd.releaseAllVideos();
        binding.videoPlayer.setUp(url, "测试", JzvdStd.SCREEN_NORMAL, JZMediaExo.class);
        binding.videoPlayer.startVideo();

        ExoPlayer exoPlayer = new ExoPlayer.Builder(this, new DefaultRenderersFactory(this))
                .setTrackSelector(new DefaultTrackSelector(this, new AdaptiveTrackSelection.Factory()))
                .setLoadControl(new DefaultLoadControl.Builder()
                        .setAllocator(new DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE))
                        .setBufferDurationsMs(360000, 600000, 1000, 5000)
                        .setPrioritizeTimeOverSizeThresholds(false)
                        .setTargetBufferBytes(C.LENGTH_UNSET)
                        .build())
                .setBandwidthMeter(new DefaultBandwidthMeter.Builder(this).build())
                .build();
        exoPlayer.setMediaSource(new ProgressiveMediaSource
                .Factory(new DefaultDataSource.Factory(this, new DefaultHttpDataSource
                .Factory()
                .setUserAgent(getString(R.string.app_name))
                .setConnectTimeoutMs(5000)
                .setReadTimeoutMs(5000)))
                .createMediaSource(new MediaItem
                        .Builder()
                        .setUri(Uri.parse(url))
                        .setMimeType(MimeTypes.APPLICATION_M3U8)
                        .build()));
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
        binding.playerView.setPlayer(exoPlayer);
    }
}