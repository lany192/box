package com.lany192.box.sample.ui.video

import android.net.Uri
import android.os.Bundle
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.upstream.DefaultAllocator
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewModelActivity
import com.github.lany192.video.JZMediaExo
import com.github.lany192.video.Jzvd
import com.github.lany192.video.JzvdStd
import com.lany192.box.demo.R
import com.lany192.box.demo.databinding.ActivityVideoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/ui/video")
class VideoActivity : ViewModelActivity<VideoViewModel, ActivityVideoBinding>() {
    private val url = "https://media.w3.org/2010/05/sintel/trailer.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Jzvd.releaseAllVideos()
        binding.videoPlayer.setUp(url, "测试", JzvdStd.SCREEN_NORMAL, JZMediaExo::class.java)
        binding.videoPlayer.startVideo()

        val exoPlayer = ExoPlayer.Builder(this, DefaultRenderersFactory(this))
            .setTrackSelector(DefaultTrackSelector(this, AdaptiveTrackSelection.Factory()))
            .setLoadControl(
                DefaultLoadControl.Builder()
                    .setAllocator(DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE))
                    .setBufferDurationsMs(360000, 600000, 1000, 5000)
                    .setPrioritizeTimeOverSizeThresholds(false)
                    .setTargetBufferBytes(C.LENGTH_UNSET)
                    .build()
            )
            .setBandwidthMeter(DefaultBandwidthMeter.Builder(this).build())
            .build()
        exoPlayer.setMediaSource(
            ProgressiveMediaSource.Factory(
                DefaultDataSource.Factory(
                    this, DefaultHttpDataSource.Factory()
                        .setUserAgent(getString(R.string.app_name))
                        .setConnectTimeoutMs(5000)
                        .setReadTimeoutMs(5000)
                )
            )
                .createMediaSource(
                    MediaItem.Builder()
                        .setUri(Uri.parse(url))
                        .setMimeType(MimeTypes.APPLICATION_M3U8)
                        .build()
                )
        )
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        binding.playerView.player = exoPlayer
    }
}