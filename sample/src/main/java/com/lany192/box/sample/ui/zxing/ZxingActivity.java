package com.lany192.box.sample.ui.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.github.lany192.arch.activity.VBActivity;
import com.github.lany192.arch.databinding.ToolbarDefaultBinding;
import com.hjq.toast.Toaster;
import com.king.camera.scan.CameraScan;
import com.king.camera.scan.util.LogUtils;
import com.king.zxing.util.CodeUtils;
import com.lany192.box.sample.R;
import com.lany192.box.sample.databinding.ActivityZxingBinding;

import java.util.concurrent.Executors;

public class ZxingActivity extends VBActivity<ActivityZxingBinding, ToolbarDefaultBinding> {
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_IS_QR_CODE = "key_code";
    public static final int REQUEST_CODE_SCAN = 0x01;
    public static final int REQUEST_CODE_PHOTO = 0x02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    String result = CameraScan.parseScanResult(data);
                    Toaster.show(result);
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
                    Toaster.show(result);
                });

            });

        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /**
     * 扫码
     *
     * @param cls
     */
    private void startScan(Class<?> cls) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.in, R.anim.out);
        Intent intent = new Intent(this, cls);
        ActivityCompat.startActivityForResult(this, intent, REQUEST_CODE_SCAN, optionsCompat.toBundle());
    }

    /**
     * 生成二维码/条形码
     *
     * @param isQRCode
     */
    private void startGenerateCodeActivity(boolean isQRCode, String title) {
        Intent intent = new Intent(this, CodeActivity.class);
        intent.putExtra(KEY_IS_QR_CODE, isQRCode);
        intent.putExtra(KEY_TITLE, title);
        startActivity(intent);
    }

    /**
     * 开始选择图片
     */
    private void startPickPhoto() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, REQUEST_CODE_PHOTO);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMultiFormat:
                startScan(MultiFormatScanActivity.class);
                break;
            case R.id.btnQRCode:
                startScan(QRCodeScanActivity.class);
                break;
            case R.id.btnFullQRCode:
                startScan(FullScreenQRCodeScanActivity.class);
                break;
            case R.id.btnPickPhoto:
                startPickPhoto();
                break;
            case R.id.btnGenerateQrCode:
                startGenerateCodeActivity(true, ((Button) v).getText().toString());
                break;
            case R.id.btnGenerateBarcode:
                startGenerateCodeActivity(false, ((Button) v).getText().toString());
                break;
        }

    }
}