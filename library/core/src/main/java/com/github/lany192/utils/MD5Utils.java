package com.github.lany192.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具
 */
public class MD5Utils {

    public static String md5(String str) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
        StringBuilder builder = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                builder.append("0");
            }
            builder.append(Integer.toHexString(b & 0xFF));
        }
        return builder.toString().toUpperCase();
    }


    /**
     * 获取单个文件的MD5值。解决首位0被省略问题，解决超大文件问题
     */
    public static String md5(File file) {
        StringBuffer buffer;
        try {
            char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            long fileSize = ch.size();
            int bufferCount = (int) Math.ceil((double) fileSize / (double) Integer.MAX_VALUE);
            MappedByteBuffer[] mappedByteBuffers = new MappedByteBuffer[bufferCount];
            long preLength = 0;
            long regionSize = Integer.MAX_VALUE;
            for (int i = 0; i < bufferCount; i++) {
                if (fileSize - preLength < Integer.MAX_VALUE) {
                    regionSize = fileSize - preLength;
                }
                mappedByteBuffers[i] = ch.map(FileChannel.MapMode.READ_ONLY, preLength, regionSize);
                preLength += regionSize;
            }
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            for (int i = 0; i < bufferCount; i++) {
                messagedigest.update(mappedByteBuffers[i]);
            }
            byte[] bytes = messagedigest.digest();
            int n = bytes.length;
            buffer = new StringBuffer(2 * n);
            for (byte bt : bytes) {
                char c0 = hexDigits[(bt & 0xf0) >> 4];
                char c1 = hexDigits[bt & 0xf];
                buffer.append(c0);
                buffer.append(c1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return buffer.toString();
    }
}
