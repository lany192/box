package com.lany192.box.sample.ui.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.lany192.arch.activity.ViewBindingActivity;
import com.google.zxing.BarcodeFormat;
import com.king.camera.scan.CameraScan;
import com.king.camera.scan.util.LogUtils;
import com.king.zxing.util.CodeUtils;
import com.lany192.box.sample.MockUtils;
import com.lany192.box.sample.R;
import com.lany192.box.sample.databinding.ActivityZxingBinding;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.Executors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@Route(path = "/ui/zxing")
public class ZxingActivity extends ViewBindingActivity<ActivityZxingBinding> {
    public static final int REQUEST_CODE_SCAN = 0x01;
    public static final int REQUEST_CODE_PHOTO = 0x02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.test1.setOnClickListener(v -> startScan(MultiFormatScanActivity.class));
        binding.test2.setOnClickListener(v -> startScan(QRCodeScanActivity.class));
        binding.test3.setOnClickListener(v -> startScan(FullScreenQRCodeScanActivity.class));
        binding.test4.setOnClickListener(v -> startPickPhoto());
        binding.test5.setOnClickListener(v -> createQRCode(MockUtils.getImageUrl()));
        binding.test6.setOnClickListener(v -> createBarCode(String.valueOf(RandomUtils.nextInt(1000000, 999999999))));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    String result = CameraScan.parseScanResult(data);
                    binding.result.setText(String.format("识别结果：%s", result));
                    break;
                case REQUEST_CODE_PHOTO:
                    parsePhoto(data);
                    break;
            }
        }
    }

    private void parsePhoto(Intent data) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            //异步解析
            Executors.newSingleThreadExecutor().execute(() -> {
                final String result = CodeUtils.parseCode(bitmap);
                runOnUiThread(() -> {
                    LogUtils.d("result:" + result);
                    binding.result.setText(String.format("识别结果：%s", result));
                });
            });
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /**
     * 扫码
     */
    private void startScan(Class<?> cls) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.in, R.anim.out);
        Intent intent = new Intent(this, cls);
        ActivityCompat.startActivityForResult(this, intent, REQUEST_CODE_SCAN, optionsCompat.toBundle());
    }


    /**
     * 开始选择图片
     */
    private void startPickPhoto() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }

    /**
     * 生成二维码
     */
    private void createQRCode(String content) {
        Executors.newSingleThreadExecutor().execute(() -> {
            //生成二维码相关放在子线程里面
            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.android);
            Bitmap bitmap = CodeUtils.createQRCode(content, 600, logo);
            runOnUiThread(() -> {
                //显示二维码
                binding.code1.setImageBitmap(bitmap);
            });
        });
    }

    /**
     * 生成条形码
     */
    private void createBarCode(String content) {
        Executors.newSingleThreadExecutor().execute(() -> {
            //生成条形码相关放在子线程里面
            Bitmap bitmap = CodeUtils.createBarCode(content, BarcodeFormat.CODE_128, 800, 200, null, true);
            runOnUiThread(() -> {
                //显示条形码
                binding.code2.setImageBitmap(bitmap);
            });
        });
    }
}