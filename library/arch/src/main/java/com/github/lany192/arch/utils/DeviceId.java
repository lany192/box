package com.github.lany192.arch.utils;

import static android.Manifest.permission;

import android.os.Environment;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.github.lany192.log.XLog;
import com.github.lany192.utils.ContextUtils;
import com.github.lany192.utils.KVUtils;
import com.github.lany192.utils.MD5Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 维护一个唯一不变的设备id
 * 注：
 * 1、目前做不到百分百正确，只能尽量保证唯一不变
 * 2、KV和SD上保存的值是加密保存的
 * 3、id稳定性主要取决是否有SD读写权限
 */
public class DeviceId {
    private volatile static DeviceId instance = null;
    private final XLog log = XLog.tag("DeviceId");
    private final String KEY_DEVICE_ID = "BOX_DEVICE_ID";
    private String deviceId;

    private DeviceId() {
    }

    public static DeviceId getInstance() {
        if (instance == null) {
            synchronized (DeviceId.class) {
                if (instance == null) {
                    instance = new DeviceId();
                }
            }
        }
        return instance;
    }

    /**
     * 获得了SD卡权限，需要将sd上的id替换到KV和内存上
     */
    public void grantedSDPermission() {
        if (checkPermission()) {
            String sdId = getDeviceIdFromSD();
            //校验两个ID：如果KV中可以读取到id，与从SD卡中读取id进行比较是否相等
            if (!TextUtils.isEmpty(sdId) && !sdId.equals(deviceId)) {
                //如果能读取到SD卡上的id，保存到KV中
                KVUtils.putString(KEY_DEVICE_ID, sdId);
                //修改内存中的值
                deviceId = sdId;
            } else {
                saveDeviceId(deviceId);
            }
        } else {
            log.i("未授权，忽略校正动作");
        }
    }

    /**
     * 获取唯一设备码
     */
    public synchronized String getDeviceId() {
        if (TextUtils.isEmpty(deviceId)) {
            String sdId = getDeviceIdFromSD();
            String kvId = KVUtils.getString(KEY_DEVICE_ID);
            log.i("KV的值:" + kvId + "，SD的值:" + sdId);
            if (TextUtils.isEmpty(sdId)) {
                if (TextUtils.isEmpty(kvId)) {
                    deviceId = createDeviceId();
                    log.i("获取随机值:" + deviceId);
                    saveDeviceId(deviceId);
                } else {
                    deviceId = kvId;
                    log.i("保存到SD:" + deviceId);
                    save2SD(deviceId);
                    log.i("获取KV值:" + deviceId);
                }
            } else {
                deviceId = sdId;
                if (TextUtils.isEmpty(kvId) || !kvId.equals(sdId)) {
                    log.i("保存到KV:" + deviceId);
                    KVUtils.putString(KEY_DEVICE_ID, deviceId);
                }
                log.i("获取SD值:" + deviceId);
            }
        }
        return deviceId;
    }

    /**
     * 保存设备id
     */
    private void saveDeviceId(String deviceId) {
        if (!TextUtils.isEmpty(deviceId)) {
            log.i("保存到KV:" + deviceId);
            KVUtils.putString(KEY_DEVICE_ID, deviceId);
            log.i("保存到SD:" + deviceId);
            save2SD(deviceId);
        } else {
            log.i("空值不保存");
        }
    }

    /**
     * 从外部存储的公共目录上读取,
     * 读取两个sd上的id，校验是否被修改
     */
    private String getDeviceIdFromSD() {
        String obbPath = getDeviceIdFileObbPath();
        String sdPath = getDeviceIdFileSDPath();

        String obbId = getIdByFilePath(obbPath, false);
        String sdId = getIdByFilePath(sdPath, true);

        log.i("SD读取的设备码:设备码sdId：" + sdId + ",设备码obbId:" + obbId);
        if (TextUtils.isEmpty(sdId) && !TextUtils.isEmpty(obbId)) {
            log.i("obbId有数据，sdId未发现");
            save2file(obbId, sdPath, true);
        }
        if (!TextUtils.isEmpty(sdId) && TextUtils.isEmpty(obbId)) {
            log.i("sdId有数据，obbId未发现");
            save2file(sdId, obbPath, false);
        }
        if (TextUtils.isEmpty(sdId) && TextUtils.isEmpty(obbId)) {
            log.i("未读取到sdId和obbId");
            return "";
        }
        if (!sdId.equals(obbId)) {
            log.i("两个sd上的id不相同");
            return "";
        }
        return sdId;
    }

    private void save2SD(String id) {
        save2file(id, getDeviceIdFileSDPath(), true);
        save2file(id, getDeviceIdFileObbPath(), false);
    }

    /**
     * 设备号生成规则：先生成一个UUID，签名31位md5后取第6位替换uuid最后位作为校验码
     */
    private String createDeviceId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String tmp = uuid.substring(0, 31);
        String md5 = MD5Utils.md5(tmp);
        String id = tmp + md5.toCharArray()[5];
        return id.toLowerCase();
    }

    /**
     * 校验id是否正确
     */
    private boolean checkDeviceId(String id) {
        if (!TextUtils.isEmpty(id) && id.length() == 32) {
            char a = id.toCharArray()[31];
            char b = MD5Utils.md5(id.substring(0, 31)).toCharArray()[5];
            return a == b;
        }
        return false;
    }

    /**
     * 保存内容到文件中
     */
    private void save2file(String text, String filePath, boolean needPermission) {
        if (needPermission && !checkPermission()) {
            log.i("没有SD卡权限");
            return;
        }
        BufferedWriter writer = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                boolean result = file.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8);
            writer = new BufferedWriter(write);
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从文件中读取内容
     */
    private String getIdByFilePath(String filePath, boolean needPermission) {
        String id = "";
        if (needPermission && !checkPermission()) {
            log.i("没有SD卡权限");
            return id;
        }
        if (TextUtils.isEmpty(filePath)) {
            log.i("路径为空");
            return id;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                boolean result = file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            id = sb.toString();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        //校验id
        if (!checkDeviceId(id)) {
            log.i("非法id：" + id);
            id = "";
        }
        return id;
    }

    /**
     * 获取外部存在的完整文件路径
     * /storage/emulated/0/Documents/.id
     */
    private String getDeviceIdFileSDPath() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
        File file = new File(path);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            log.i("目录不存在，创建目录。创建结果：" + result);
        }
        return path + File.separator + ".id";
    }

    /**
     * 获取OBB文件路径
     * /storage/emulated/0/Android/obb/com.xxx.xx/.id
     */
    private String getDeviceIdFileObbPath() {
        String path = ContextUtils.getContext().getObbDir().getPath();
        File file = new File(path);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            log.i("目录不存在，创建目录。创建结果：" + result);
        }
        return path + File.separator + ".id";
    }

    /**
     * 是否有SD卡权限
     */
    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(ContextUtils.getContext(), permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(ContextUtils.getContext(), permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED;
    }
}