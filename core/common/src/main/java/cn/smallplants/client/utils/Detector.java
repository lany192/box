package cn.smallplants.client.utils;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Keep;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * 检测器，检查是否存在模拟器
 */
@Keep

public class Detector {
    private volatile static Detector instance;
    private final String TAG = getClass().getSimpleName();

    private Detector() {

    }

    public static Detector of() {
        if (instance == null) {
            synchronized (Detector.class) {
                if (instance == null) {
                    instance = new Detector();
                }
            }
        }
        return instance;
    }

    private static String getProperty(String propName) {
        String value = null;
        Object roSecureObj;
        try {
            roSecureObj = Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke(null, propName);
            if (roSecureObj != null) {
                value = (String) roSecureObj;
            }
        } catch (Exception e) {
            value = null;
        } finally {
            return value;
        }
    }

    private static String exec(String command) {
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("sh");
            bufferedOutputStream = new BufferedOutputStream(process.getOutputStream());
            bufferedInputStream = new BufferedInputStream(process.getInputStream());
            bufferedOutputStream.write(command.getBytes());
            bufferedOutputStream.write('\n');
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            process.waitFor();
            String outputStr = getStrFromBufferInputSteam(bufferedInputStream);
            return outputStr;
        } catch (Exception e) {
            return null;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

    private static String getStrFromBufferInputSteam(BufferedInputStream bufferedInputStream) {
        if (null == bufferedInputStream) {
            return "";
        }
        int BUFFER_SIZE = 512;
        byte[] buffer = new byte[BUFFER_SIZE];
        StringBuilder result = new StringBuilder();
        try {
            while (true) {
                int read = bufferedInputStream.read(buffer);
                if (read > 0) {
                    result.append(new String(buffer, 0, read));
                }
                if (read < BUFFER_SIZE) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 检测是否是模拟器
     *
     * @return true 为模拟器
     */
    public boolean isEmulator(boolean debug) {
        if (debug) {
            Log.i(TAG, "调试模式不检查模拟器");
            return false;
        }
        int suspectCount = 0;
        //读基带信息
        String baseBandVersion = getProperty("gsm.version.baseband");
        if (TextUtils.isEmpty(baseBandVersion) | (baseBandVersion != null && baseBandVersion.contains(
                "1.0.0.0"))) {
            ++suspectCount;
        }
        //读渠道信息，针对一些基于vbox的模拟器
        String buildFlavor = getProperty("ro.build.flavor");
        if (TextUtils.isEmpty(buildFlavor) | (buildFlavor != null && buildFlavor.contains("vbox"))) {
            ++suspectCount;
        }
        //读处理器信息，这里经常会被处理
        String productBoard = getProperty("ro.product.board");
        if (TextUtils.isEmpty(productBoard) | (productBoard != null && productBoard.contains(
                "android"))) {
            ++suspectCount;
        }
        //读处理器平台，这里不常会处理
        String boardPlatform = getProperty("ro.board.platform");
        if (TextUtils.isEmpty(boardPlatform) | (boardPlatform != null && boardPlatform.contains(
                "android"))) {
            ++suspectCount;
        }
        //高通的cpu两者信息一般是一致的
        if (!TextUtils.isEmpty(productBoard)
                && !TextUtils.isEmpty(boardPlatform)
                && !productBoard.equals(boardPlatform)) {
            ++suspectCount;
        }
        //一些模拟器读取不到进程租信息
        String filter = exec("cat /proc/self/cgroup");
        if (filter == null || filter.length() == 0) {
            ++suspectCount;
        }
        return suspectCount > 2;
    }
}
