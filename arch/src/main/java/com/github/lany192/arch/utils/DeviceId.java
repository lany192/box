package com.github.lany192.arch.utils;

import static android.Manifest.permission;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.github.lany192.utils.ContextUtils;
import com.github.lany192.utils.KVUtils;
import com.github.lany192.utils.MD5Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class DeviceId {
    private volatile static DeviceId instance = null;
    private final Logger.Builder log = XLog.tag("DeviceId");
    private final String KEY_DEVICE_ID = "APP_DEVICE_ID";
    private String deviceId;

    private DeviceId() {
    }

    public static DeviceId get() {
        if (instance == null) {
            synchronized (DeviceId.class) {
                if (instance == null) {
                    instance = new DeviceId();
                }
            }
        }
        return instance;
    }

    public String getDeviceId() {
        //1.先判断内存中是否已经有id，如果有值，直接读取
        if (!TextUtils.isEmpty(deviceId)) {
            log.i("获取内存中的ID值:" + deviceId);
            return deviceId;
        }
        //2.判断SP中是否有值，优先获取SP里的值是因为不需要权限
        deviceId = KVUtils.get().getString(KEY_DEVICE_ID);
        if (!TextUtils.isEmpty(deviceId)) {
            String id = getDeviceIdFromSD();
            //校验两个ID：如果SP中可以读取到id，与从SD卡中读取id进行比较是否相等
            if (TextUtils.isEmpty(id) || !deviceId.equals(id)) {
                //如果两个id不相等，用SP上的id覆盖SD卡上的id，防止SD卡上的值被人为修改，SP上的值是加密保存的改不了
                save2SD(deviceId);
            }
            log.i("获取SP上的ID值:" + deviceId);
            return deviceId;
        }
        //3.如果内存中没有id,读取设备系统自带id
        deviceId = getAndroidDeviceId(ContextUtils.getContext());
        log.i("获取系统自带的ID值:" + deviceId);
        //如果系统有自带id，保存起来，防止权限被关后不能再次读取
        if (!TextUtils.isEmpty(deviceId)) {
            saveDeviceId(deviceId);
            return deviceId;
        }
        //4.如果系统不自带、SP中又没有ID，读取SD卡上的id
        deviceId = getDeviceIdFromSD();
        log.i("获取SD卡上的ID值:" + deviceId);
        if (!TextUtils.isEmpty(deviceId)) {
            //如果能读取到SD卡上的id，保存到SP中
            KVUtils.get().putString(KEY_DEVICE_ID, deviceId);
            return deviceId;
        }
        //5.如果系统不自带id、SP和SD卡上都没有id，生成随机id。保存到SP和SD卡中
        deviceId = getUUID();
        //新生成的id要保存起来，保存到SP、SD
        KVUtils.get().putString(KEY_DEVICE_ID, deviceId);
        save2SD(deviceId);
        log.i("获取随机生成ID值:" + deviceId);
        return deviceId;
    }

    /**
     * 保存设备id
     */
    private void saveDeviceId(String deviceId) {
        //保存到SP
        KVUtils.get().putString(KEY_DEVICE_ID, deviceId);
        //保存到SD卡
        save2SD(deviceId);
    }

    /**
     * 获取系统自带的deviceId
     */
    private String getAndroidDeviceId(Context context) {
        if (ContextCompat.checkSelfPermission(context, permission.READ_PHONE_STATE)
                == PermissionChecker.PERMISSION_GRANTED) {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                @SuppressLint("HardwareIds") String deviceId = manager.getDeviceId();
                if (!TextUtils.isEmpty(deviceId)) {
                    StringBuilder builder = new StringBuilder(MD5Utils.md5(deviceId));
                    //前面两位换成KP
                    builder.replace(0, 2, "kp");
                    return builder.toString().toLowerCase();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.i("没有读取设备信息权限");
        return "";
    }

    /**
     * 获取外部存在的完整文件路径
     * /storage/emulated/0/Documents/.info
     */
    private String getDeviceIdFilePath() {
        String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + File.separator;
        File file = new File(dirPath + ".info");
        if (!file.exists()) {
            try {
                boolean result = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.i("设备id文件路径:" + file.getPath());
        return file.getPath();
    }

    /**
     * 从外部存储的公共目录上读取
     */
    private String getDeviceIdFromSD() {
        if (ContextCompat.checkSelfPermission(ContextUtils.getContext(), permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
            String filePath = getDeviceIdFilePath();
            File file = new File(filePath);
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString;
                while ((tempString = reader.readLine()) != null) {
                    sb.append(tempString);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e1) {

                    }
                }
                return sb.toString();
            }
        } else {
            log.i("没有读SD卡权限");
            return "";
        }
    }

    private void save2SD(String content) {
        if (ContextCompat.checkSelfPermission(ContextUtils.getContext(), permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
            String filePath = getDeviceIdFilePath();
            BufferedWriter writer = null;
            try {
                File file = new File(filePath);
                OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8);
                writer = new BufferedWriter(write);
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.flush();
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            log.i("没有写SD卡权限");
        }
    }

    private String getUUID() {
        String id = UUID.randomUUID().toString().replace("-", "");
        StringBuilder builder = new StringBuilder(id);
        //前面两位换成KP
        builder.replace(0, 2, "ly");
        return builder.toString().toLowerCase();
    }
}
