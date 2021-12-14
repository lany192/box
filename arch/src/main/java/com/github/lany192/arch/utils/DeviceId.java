package com.github.lany192.arch.utils;

import static android.Manifest.permission;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;


import com.github.lany192.kv.KVUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 服务维护一个唯一不变的设备id（目前做不到百分百正确，只能尽量保证唯一不变）
 */
public class DeviceId {
    private volatile static DeviceId instance = null;
    private final String TAG = getClass().getSimpleName();
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
        //先判断内存中是否已经有id，如果直接读取
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        //如果内存中没有id,读取设备系统自带id
        deviceId = getAndroidDeviceId(ContextUtils.getContext());
        //如果系统有自带id，保存到SP中
        String KEY_DEVICE_ID = "ANDROID_DEVICE_ID";
        if (!TextUtils.isEmpty(deviceId)) {
            KVUtils.get().putString(KEY_DEVICE_ID, deviceId);
            return deviceId;
        }
        //如果系统没有自带id，从SP中读取id
        deviceId = KVUtils.get().getString(KEY_DEVICE_ID);
        if (!TextUtils.isEmpty(deviceId)) {
            String id = getDeviceIdFromExternalStorage();
            //如果SP中可以读取到id，与从存储卡中读取id进行比较是否相等
            if (TextUtils.isEmpty(id) || !deviceId.equals(id)) {
                //如果两个id不相等，用SP上的id覆盖存储卡上的id
                save2ExternalStorage(deviceId);
            }
            return deviceId;
        } else {
            //如果系统不自带、SP中又没有ID，读取存储卡上的id
            deviceId = getDeviceIdFromExternalStorage();
            if (!TextUtils.isEmpty(deviceId)) {
                //如果能读取到存储卡上的id，保存到SP中
                KVUtils.get().putString(KEY_DEVICE_ID, deviceId);
                return deviceId;
            }
        }
        //如果系统不自带id、SP和存储卡上都没有id，生成随机id。保存到SP和存储卡中
        deviceId = getUUID();
        if (!TextUtils.isEmpty(deviceId)) {
            //新生成的id要保存起来
            save2ExternalStorage(deviceId);
            KVUtils.get().putString(KEY_DEVICE_ID, deviceId);
        }
        return deviceId;
    }

    /**
     * 获取系统自带的deviceId
     */
    private String getAndroidDeviceId(Context context) {
        if (ContextCompat.checkSelfPermission(context, permission.READ_PHONE_STATE)
                == PermissionChecker.PERMISSION_GRANTED) {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = manager.getDeviceId();
            if (!TextUtils.isEmpty(deviceId)) {
                return deviceId;
            }
        }
        return "";
    }

    /**
     * 获取外部存在的完整文件路径
     */
    private String getDeviceIdFilePath() {
        String dirPath;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + File.separator;
        } else {
            dirPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "Documents" + File.separator;
        }
        File file = new File(dirPath + ".basics_info");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "设备id文件路径:" + file.getPath());
        return file.getPath();
    }

    /**
     * 从外部存储的公共目录上读取
     */
    private String getDeviceIdFromExternalStorage() {
        String filePath = getDeviceIdFilePath();
        File file = new File(filePath);
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
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
    }

    private void save2ExternalStorage(String content) {
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
    }

    private String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
