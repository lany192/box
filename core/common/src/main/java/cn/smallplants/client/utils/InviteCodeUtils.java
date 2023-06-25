package cn.smallplants.client.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 邀请码读取.
 */
public class InviteCodeUtils {

    /**
     * 从APK中读取邀请码
     */
    public static String getInviteCode(Context context) {
        if (context != null) {
            String absPath = context.getApplicationInfo().sourceDir;
            try {
                InputStream is = new BufferedInputStream(new FileInputStream(absPath));
                ZipInputStream zis = new ZipInputStream(is);
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    String name = entry.getName();
                    if (name.startsWith("invite_code_")) {
                        String code = name.replace("invite_code_", "").replace("/", "");
                        if (!TextUtils.isEmpty(code)) {
                            return code;
                        }
                    }
                }
                zis.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
