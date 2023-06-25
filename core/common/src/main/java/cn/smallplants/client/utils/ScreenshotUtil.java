package cn.smallplants.client.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;


public class ScreenshotUtil {

    /**
     * 截取指定View显示内容
     * <p>
     * 需要读写权限
     */

    public static String saveScreenshotFromView(View view, Activity context) {
        Bitmap bitmap = createBitmap(view);
        return saveImageToGallery(bitmap, context);
    }

    public static Bitmap createBitmap(View v) {
        Bitmap bmp = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        v.draw(c);
        return bmp;
    }

    /**
     * 保存图片至相册
     * <p>
     * 需要读写权限
     */
    public static String saveImageToGallery(Bitmap bmp, Activity context) {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        File file = new File(appDir, System.currentTimeMillis() + ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        return file.getPath();
    }
}
