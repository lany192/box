package com.lany192.box.sample.ui.zxing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.king.zxing.util.CodeUtils;
import com.lany192.box.sample.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 生成条形码/二维码示例
 */
public class CodeActivity extends AppCompatActivity {
    private TextView tvBarcodeFormat;
    private ImageView ivCode;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_activity);
        ivCode = findViewById(R.id.ivCode);
        tvBarcodeFormat = findViewById(R.id.tvBarcodeFormat);
        boolean isQRCode = getIntent().getBooleanExtra(ZxingActivity.KEY_IS_QR_CODE, false);

        if (isQRCode) {
            tvBarcodeFormat.setText("BarcodeFormat: QR_CODE");
            createQRCode(getString(R.string.app_name));
        } else {
            tvBarcodeFormat.setText("BarcodeFormat: CODE_128");
            createBarCode("1234567890");
        }
    }

    /**
     * 生成二维码
     *
     * @param content
     */
    private void createQRCode(String content) {
        executor.execute(() -> {
            //生成二维码相关放在子线程里面
            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.android);
            Bitmap bitmap = CodeUtils.createQRCode(content, 600, logo);
            runOnUiThread(() -> {
                //显示二维码
                ivCode.setImageBitmap(bitmap);
            });
        });

    }

    /**
     * 生成条形码
     *
     * @param content
     */
    private void createBarCode(String content) {
        executor.execute(() -> {
            //生成条形码相关放在子线程里面
            Bitmap bitmap = CodeUtils.createBarCode(content, BarcodeFormat.CODE_128, 800, 200, null, true);
            runOnUiThread(() -> {
                //显示条形码
                ivCode.setImageBitmap(bitmap);
            });
        });
    }
}
