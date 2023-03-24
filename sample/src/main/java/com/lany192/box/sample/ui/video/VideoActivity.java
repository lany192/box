package com.lany192.box.sample.ui.video;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.BoxActivity;
import com.lany192.box.sample.databinding.ActivityVideoBinding;

import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/video")
public class VideoActivity extends BoxActivity<VideoViewModel, ActivityVideoBinding> {
    private final String[] videoUrls = {
            "http://jzvd.nathen.cn/6ea7357bc3fa4658b29b7933ba575008/fbbba953374248eb913cb1408dc61d85-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/35b3dc97fbc240219961bd1fccc6400b/8d9b76ab5a584bce84a8afce012b72d3-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/df6096e7878541cbbea3f7298683fbed/ef76450342914427beafe9368a4e0397-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/384d341e000145fb82295bdc54ecef88/103eab5afca34baebc970378dd484942-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/f55530ba8a59403da0621cbf4faef15e/adae4f2e3ecf4ea780beb057e7bce84c-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/6340efd1962946ad80eeffd19b3be89c/65b499c0f16e4dd8900497e51ffa0949-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/f07fa9fddd1e45a6ae1570c7fe7967c1/c6db82685b894e25b523b1cb28d79f2e-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/d2e969f2ec734520b46ab0965d2b68bd/f124edfef6c24be8b1a7b7f996ccc5e0-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/4f965ad507ef4194a60a943a34cfe147/32af151ea132471f92c9ced2cff785ea-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/623f75c3beea4b1781ea37940e70bbe4/b9cee3fd1a09487ca99ef789cdc41312-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/d8c137ceba9849f8b2f454a55a96266f/910c8381ff894905b5bc272f8194382a-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/b8a589e5f12c45fdad96674d08affd31/f1d7229f553f414283033af3e292c6c9-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/8abcdf98ec6a418b945a70fe9dd6fc7f/5cb36416a23a4da8b15d3eaa5e19a1e6-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/1b61da23555d4ce28c805ea303711aa5/7a33ac2af276441bb4b9838f32d8d710-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/d525f756aabf4b0588c2152fb94e07f5/d9f59bef829a472a9ca066620d9b871a-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/6e2fdec45dfa44a6802e95f8e4bc3280/a6a5273ac4244333923991be0583ffc7-5287d2089db37e62345123a1be272f8b.mp4",
            "http://jzvd.nathen.cn/22b4de0e2b1245959c5baa77fe0bf14e/896a137559084b7eb879f5441faff20d-5287d2089db37e62345123a1be272f8b.mp4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExoPlayer player = new ExoPlayer.Builder(this).build();
        player.addListener(new Player.Listener() {
            @Override
            public void onEvents(@NonNull Player player, @NonNull Player.Events events) {

            }
        });

        binding.playerView.setPlayer(player);

        int index = new Random().nextInt(videoUrls.length);
        //填充媒体数据
        player.addMediaItem(MediaItem.fromUri(videoUrls[index]));
//准备播放
        player.prepare();
//准备完成就开始播放
        player.setPlayWhenReady(true);
    }
}