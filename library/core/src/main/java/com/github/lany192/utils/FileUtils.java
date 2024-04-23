package com.github.lany192.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static boolean copyFile(String srcFilePath, String destFilePath) {
        try {
            File srcFile = new File(srcFilePath);
            File destFile = new File(destFilePath);
            FileInputStream fileInputStream = new FileInputStream(srcFile);
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            fileInputStream.close();
            fileOutputStream.close();

            return true;
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        return false;
    }
}
