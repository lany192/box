package com.lany192.box.sample.ui.zxing

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lany192.arch.activity.ViewBindingActivity
import com.github.lany192.extension.addStatusBarPadding
import com.github.lany192.extension.toast
import com.google.zxing.BarcodeFormat
import com.king.camera.scan.CameraScan
import com.king.zxing.util.CodeUtils
import com.lany192.box.sample.MockUtils
import com.lany192.box.sample.R
import com.lany192.box.sample.databinding.ActivityZxingBinding
import dagger.hilt.android.AndroidEntryPoint
import org.apache.commons.lang3.RandomUtils
import java.util.concurrent.Executors

@AndroidEntryPoint
@Route(path = "/ui/zxing")
class ZxingActivity : ViewBindingActivity<ActivityZxingBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.addStatusBarPadding()
        binding.test1.setOnClickListener { startScan(MultiFormatScanActivity::class.java) }
        binding.test2.setOnClickListener { startScan(QRCodeScanActivity::class.java) }
        binding.test3.setOnClickListener { startScan(FullScreenQRCodeScanActivity::class.java) }
        binding.test4.setOnClickListener { startPickPhoto() }
        binding.test5.setOnClickListener { createQRCode(MockUtils.getImageUrl()) }
        binding.test6.setOnClickListener {
            createBarCode(RandomUtils.nextInt(1000000, 999999999).toString())
        }
    }

    private fun parsePhoto(data: Uri) {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data)
            //异步解析
            Executors.newSingleThreadExecutor().execute {
                val result = CodeUtils.parseCode(bitmap)
                runOnUiThread {
                    binding.result.text = String.format("识别结果：%s", result)
                }
            }
        } catch (e: Exception) {
            e.fillInStackTrace()
        }
    }

    /**
     * 扫码
     */
    private fun startScan(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivityForResult(intent) { t ->
            t?.let {
                if (it.resultCode == RESULT_OK && it.data != null) {
                    val result = CameraScan.parseScanResult(it.data)
                    binding.result.text = String.format("识别结果：%s", result)
                }
            }
        }
    }


    /**
     * 开始选择图片
     */
    private fun startPickPhoto() {
        startMediaPicker(
            { uri ->
                if (uri != null) {
                    parsePhoto(uri)
                } else {
                    toast("No media selected")
                }
            },
            PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                .build()
        )
    }

    /**
     * 生成二维码
     */
    private fun createQRCode(content: String) {
        Executors.newSingleThreadExecutor().execute {
            //生成二维码相关放在子线程里面
            val logo = BitmapFactory.decodeResource(resources, R.drawable.android)
            val bitmap = CodeUtils.createQRCode(content, 600, logo)
            runOnUiThread {
                //显示二维码
                binding.code1.setImageBitmap(bitmap)
            }
        }
    }

    /**
     * 生成条形码
     */
    private fun createBarCode(content: String) {
        Executors.newSingleThreadExecutor().execute {
            //生成条形码相关放在子线程里面
            val bitmap =
                CodeUtils.createBarCode(content, BarcodeFormat.CODE_128, 800, 200, null, true)
            runOnUiThread {
                //显示条形码
                binding.code2.setImageBitmap(bitmap)
            }
        }
    }
}