package cn.smallplants.client.utils;


import android.graphics.Bitmap;
import android.text.TextUtils;

import com.github.lany192.arch.utils.ListUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import cn.smallplants.client.common.Constants;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class OtherUtils {

    /**
     * 获取文件类型
     */
    public static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    /**
     * 生成简单二维码  https://blog.csdn.net/xch_yang/article/details/82147461
     *
     * @param content                字符串内容
     * @param size                   二维码大小
     * @param error_correction_level 容错率 L：7% M：15% Q：25% H：35%
     * @param margin                 空白边距（二维码与边框的空白区域）
     * @param color_black            黑色色块
     * @param color_white            白色色块
     * @return BitMap
     */
    public static Bitmap createQRCodeBitmap(String content, int size, String error_correction_level,
                                            String margin, int color_black, int color_white) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (size < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * size + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * size + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MultipartBody getMultipartBody(List<String> paths) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.addFormDataPart("secretKey", Constants.FILER_SERVER_API_KEY);
        if (!ListUtils.isEmpty(paths)) {
            for (String path : paths) {
                File file = new File(path);
                builder.addPart(MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file)));
            }
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    /**
     * 获取版权提示
     */
    public static String getCopyright() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return "Copyright @" + year + " smallplants.cn All Rights Reserved";
    }

    public static float random() {
        List<Float> items = new ArrayList<>();
        items.add(0.01F);
        items.add(1.23F);
        items.add(2.34F);
        items.add(3.45F);
        items.add(4.56F);
        items.add(5.67F);
        items.add(6.78F);
        items.add(7.89F);
        items.add(8.90F);
        items.add(1.11F);
        items.add(2.22F);
        items.add(3.33F);
        items.add(4.44F);
        items.add(5.55F);
        items.add(6.66F);
        items.add(7.77F);
        items.add(8.88F);
        items.add(9.99F);
        items.add(13.14F);
        items.add(5.20F);
        items.add(9.20F);
        return items.get(new Random().nextInt(items.size() - 1));
    }
}
