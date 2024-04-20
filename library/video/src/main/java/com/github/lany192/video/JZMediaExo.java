package com.github.lany192.video;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.VideoSize;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.exoplayer.upstream.DefaultAllocator;
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter;

public class JZMediaExo extends JZMediaInterface {
    private ExoPlayer player;
    private Runnable callback;
    private String TAG = "JZMediaExo";
    private long previousSeek = 0;

    public JZMediaExo(Jzvd jzvd) {
        super(jzvd);
    }

    @Override
    public void start() {
        player.setPlayWhenReady(true);
    }

    @OptIn(markerClass = UnstableApi.class)
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
            player = new ExoPlayer.Builder(context, new DefaultRenderersFactory(context))
                    .setTrackSelector(new DefaultTrackSelector(context, new AdaptiveTrackSelection.Factory()))
                    .setLoadControl(new DefaultLoadControl.Builder()
                            .setAllocator(new DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE))
                            .setBufferDurationsMs(360000, 600000, 1000, 5000)
                            .setPrioritizeTimeOverSizeThresholds(false)
                            .setTargetBufferBytes(C.LENGTH_UNSET)
                            .build())
                    .setBandwidthMeter(new DefaultBandwidthMeter.Builder(context).build())
                    .build();
            DefaultHttpDataSource.Factory httpFactory = new DefaultHttpDataSource.Factory();
            httpFactory.setUserAgent(context.getResources().getString(R.string.app_name));
            httpFactory.setConnectTimeoutMs(5000);
            httpFactory.setReadTimeoutMs(5000);
            DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(context, httpFactory);

            String currUrl = jzvd.jzDataSource.getCurrentUrl().toString();
            MediaSource videoSource;
            if (currUrl.contains(".m3u8")) {
                videoSource = new HlsMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(new MediaItem
                                .Builder()
                                .setUri(Uri.parse(currUrl))
                                .setMimeType(MimeTypes.APPLICATION_M3U8)
                                .build());
                //addEventListener 这里只有两个参数都要传入值才可以成功设置
                // 否者会被断言 Assertions.checkArgument(handler != null && eventListener != null);
                // 并且报错  IllegalArgumentException()  所以不需要添加监听器时 注释掉
                //      videoSource .addEventListener( handler, null);
            } else {
                videoSource = new ProgressiveMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(new MediaItem
                                .Builder()
                                .setUri(Uri.parse(currUrl))
                                .setMimeType(MimeTypes.APPLICATION_M3U8)
                                .build());
            }
            player.addListener(new Player.Listener() {

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
                public void onPlayerError(PlaybackException error) {
                    Log.e(TAG, "onPlayerError" + error.toString());
                    handler.post(() -> jzvd.onError(1000, 1000));
                }

//                @Override
//                public void onSeekProcessed() {
//                    handler.post(() -> jzvd.onSeekComplete());
//                }

                @Override
                public void onVideoSizeChanged(VideoSize videoSize) {
                    handler.post(() -> jzvd.onVideoSizeChanged(videoSize.width, videoSize.height));
                }
            });

            Log.e(TAG, "URL Link = " + currUrl);


            Boolean isLoop = jzvd.jzDataSource.looping;
            if (isLoop) {
                player.setRepeatMode(Player.REPEAT_MODE_ONE);
            } else {
                player.setRepeatMode(Player.REPEAT_MODE_OFF);
            }
            player.setMediaSource(videoSource);
            player.prepare();
            player.setPlayWhenReady(true);
            callback = new onBufferingUpdate();

            if (jzvd.textureView != null) {
                SurfaceTexture surfaceTexture = jzvd.textureView.getSurfaceTexture();
                if (surfaceTexture != null) {
                    player.setVideoSurface(new Surface(surfaceTexture));
                }
            }
        });

    }

    @Override
    public void pause() {
        player.setPlayWhenReady(false);
    }

    @Override
    public boolean isPlaying() {
        return player.getPlayWhenReady();
    }

    @Override
    public void seekTo(long time) {
        if (player == null) {
            return;
        }
        if (time != previousSeek) {
            if (time >= player.getBufferedPosition()) {
                jzvd.onStatePreparingPlaying();
            }
            player.seekTo(time);
            previousSeek = time;
            jzvd.seekToInAdvance = time;

        }
    }

    @Override
    public void release() {
        if (mMediaHandler != null && mMediaHandlerThread != null && player != null) {//不知道有没有妖孽
            HandlerThread tmpHandlerThread = mMediaHandlerThread;
            ExoPlayer tmpMediaPlayer = player;
            JZMediaInterface.SAVED_SURFACE = null;

            mMediaHandler.post(() -> {
                tmpMediaPlayer.release();//release就不能放到主线程里，界面会卡顿
                tmpHandlerThread.quit();
            });
            player = null;
        }
    }

    @Override
    public long getCurrentPosition() {
        if (player != null) return player.getCurrentPosition();
        else return 0;
    }

    @Override
    public long getDuration() {
        if (player != null) return player.getDuration();
        else return 0;
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        player.setVolume(leftVolume);
        player.setVolume(rightVolume);
    }

    @Override
    public void setSpeed(float speed) {
        PlaybackParameters playbackParameters = new PlaybackParameters(speed, 1.0F);
        player.setPlaybackParameters(playbackParameters);
    }


    @Override
    public void setSurface(Surface surface) {
        if (player != null) {
            player.setVideoSurface(surface);
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
            if (player != null) {
                final int percent = player.getBufferedPercentage();
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
