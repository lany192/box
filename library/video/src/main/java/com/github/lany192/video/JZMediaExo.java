package com.github.lany192.video;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

public class JZMediaExo extends JZMediaInterface implements Player.EventListener, VideoListener {
    private SimpleExoPlayer simpleExoPlayer;
    private Runnable callback;
    private String TAG = "JZMediaExo";
    private long previousSeek = 0;

    public JZMediaExo(Jzvd jzvd) {
        super(jzvd);
    }

    @Override
    public void start() {
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void prepare() {
        Log.e(TAG, "prepare");
        Context context = jzvd.getContext();

        release();
        mMediaHandlerThread = new HandlerThread("JZVD");
        mMediaHandlerThread.start();
        mMediaHandler = new Handler(context.getMainLooper());//主线程还是非主线程，就在这里
        handler = new Handler();
        mMediaHandler.post(() -> {

            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory();
            TrackSelector trackSelector = new DefaultTrackSelector(context, videoTrackSelectionFactory);

            LoadControl loadControl = new DefaultLoadControl.Builder().setAllocator(new DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE)).setBufferDurationsMs(360000, 600000, 1000, 5000).setPrioritizeTimeOverSizeThresholds(false).setTargetBufferBytes(C.LENGTH_UNSET).build();


            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(context).build();
            // 2. Create the player

            RenderersFactory renderersFactory = new DefaultRenderersFactory(context);
            simpleExoPlayer = new SimpleExoPlayer.Builder(context, renderersFactory).setTrackSelector(trackSelector).setLoadControl(loadControl).setBandwidthMeter(bandwidthMeter).build();
            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getResources().getString(R.string.app_name)));

            String currUrl = jzvd.jzDataSource.getCurrentUrl().toString();
            MediaSource videoSource;
            if (currUrl.contains(".m3u8")) {
                videoSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(new MediaItem.Builder().setUri(Uri.parse(currUrl)).setMimeType(MimeTypes.APPLICATION_M3U8).build());
                //addEventListener 这里只有两个参数都要传入值才可以成功设置
                // 否者会被断言 Assertions.checkArgument(handler != null && eventListener != null);
                // 并且报错  IllegalArgumentException()  所以不需要添加监听器时 注释掉
                //      videoSource .addEventListener( handler, null);
            } else {
                videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(new MediaItem.Builder().setUri(Uri.parse(currUrl)).setMimeType(MimeTypes.APPLICATION_M3U8).build());
            }
            simpleExoPlayer.addVideoListener(this);

            Log.e(TAG, "URL Link = " + currUrl);

            simpleExoPlayer.addListener(this);
            Boolean isLoop = jzvd.jzDataSource.looping;
            if (isLoop) {
                simpleExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
            } else {
                simpleExoPlayer.setRepeatMode(Player.REPEAT_MODE_OFF);
            }
            simpleExoPlayer.setMediaSource(videoSource);
            simpleExoPlayer.prepare();
            simpleExoPlayer.setPlayWhenReady(true);
            callback = new onBufferingUpdate();

            if (jzvd.textureView != null) {
                SurfaceTexture surfaceTexture = jzvd.textureView.getSurfaceTexture();
                if (surfaceTexture != null) {
                    simpleExoPlayer.setVideoSurface(new Surface(surfaceTexture));
                }
            }
        });

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        handler.post(() -> jzvd.onVideoSizeChanged((int) (width * pixelWidthHeightRatio), height));
    }

    @Override
    public void onRenderedFirstFrame() {
        Log.e(TAG, "onRenderedFirstFrame");
    }

    @Override
    public void pause() {
        simpleExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public boolean isPlaying() {
        return simpleExoPlayer.getPlayWhenReady();
    }

    @Override
    public void seekTo(long time) {
        if (simpleExoPlayer == null) {
            return;
        }
        if (time != previousSeek) {
            if (time >= simpleExoPlayer.getBufferedPosition()) {
                jzvd.onStatePreparingPlaying();
            }
            simpleExoPlayer.seekTo(time);
            previousSeek = time;
            jzvd.seekToInAdvance = time;

        }
    }

    @Override
    public void release() {
        if (mMediaHandler != null && mMediaHandlerThread != null && simpleExoPlayer != null) {//不知道有没有妖孽
            HandlerThread tmpHandlerThread = mMediaHandlerThread;
            SimpleExoPlayer tmpMediaPlayer = simpleExoPlayer;
            JZMediaInterface.SAVED_SURFACE = null;

            mMediaHandler.post(() -> {
                tmpMediaPlayer.release();//release就不能放到主线程里，界面会卡顿
                tmpHandlerThread.quit();
            });
            simpleExoPlayer = null;
        }
    }

    @Override
    public long getCurrentPosition() {
        if (simpleExoPlayer != null) return simpleExoPlayer.getCurrentPosition();
        else return 0;
    }

    @Override
    public long getDuration() {
        if (simpleExoPlayer != null) return simpleExoPlayer.getDuration();
        else return 0;
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        simpleExoPlayer.setVolume(leftVolume);
        simpleExoPlayer.setVolume(rightVolume);
    }

    @Override
    public void setSpeed(float speed) {
        PlaybackParameters playbackParameters = new PlaybackParameters(speed, 1.0F);
        simpleExoPlayer.setPlaybackParameters(playbackParameters);
    }

    @Override
    public void onPlayerStateChanged(final boolean playWhenReady, final int playbackState) {
        Log.e(TAG, "onPlayerStateChanged" + playbackState + "/ready=" + String.valueOf(playWhenReady));
        handler.post(() -> {
            switch (playbackState) {
                case Player.STATE_IDLE: {
                }
                break;
                case Player.STATE_BUFFERING: {
                    jzvd.onStatePreparingPlaying();
                    handler.post(callback);
                }
                break;
                case Player.STATE_READY: {
                    if (playWhenReady) {
                        jzvd.onStatePlaying();
                    } else {
                    }
                }
                break;
                case Player.STATE_ENDED: {
                    jzvd.onCompletion();
                }
                break;
            }
        });
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.e(TAG, "onPlayerError" + error.toString());
        handler.post(() -> jzvd.onError(1000, 1000));
    }

    @Override
    public void onSeekProcessed() {
        handler.post(() -> jzvd.onSeekComplete());
    }

    @Override
    public void setSurface(Surface surface) {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setVideoSurface(surface);
        } else {
            Log.e("AGVideo", "simpleExoPlayer为空");
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (SAVED_SURFACE == null) {
            SAVED_SURFACE = surface;
            prepare();
        } else {
            jzvd.textureView.setSurfaceTexture(SAVED_SURFACE);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {

    }

    private class onBufferingUpdate implements Runnable {
        @Override
        public void run() {
            if (simpleExoPlayer != null) {
                final int percent = simpleExoPlayer.getBufferedPercentage();
                handler.post(() -> jzvd.setBufferProgress(percent));
                if (percent < 100) {
                    handler.postDelayed(callback, 300);
                } else {
                    handler.removeCallbacks(callback);
                }
            }
        }
    }
}
