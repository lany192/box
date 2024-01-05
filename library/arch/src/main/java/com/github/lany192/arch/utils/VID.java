package com.github.lany192.arch.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.github.lany192.log.XLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class VID {
    private final XLog log = XLog.tag(getClass().getSimpleName());
    private final String FILE_NAME = ".android.mp3";

    private Uri findUri(Context context) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID},
                MediaStore.Audio.Media.DISPLAY_NAME + "=?",
                new String[]{FILE_NAME}, null);
        Uri audioUri = null;
        if (cursor != null && cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
            long id = cursor.getLong(idColumn);
            audioUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
            cursor.close();
        }
        return audioUri;
    }

    public String getVid(Context context) {
        if (isExist(context)) {
            return "";
        }
        try {
            Uri uri = findUri(context);
            if (uri == null) {
                log.i("==uri null");
                return "";
            }
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            if (inputStream != null) {
                // 在这里处理输入流
                byte[] lengthBytes = new byte[2];
                int bytesRead = inputStream.read(lengthBytes);
                if (bytesRead == 2) {
                    // 使用ByteBuffer将两个字节转换为short
                    ByteBuffer buffer = ByteBuffer.wrap(lengthBytes);
                    short dataLength = buffer.getShort();
                    byte[] datas = new byte[dataLength];
                    inputStream.read(datas);
                    String s = new String(datas);
                    log.i("Vid " + s);
                    if (!TextUtils.isEmpty(s)) {
                        return s;
                    }
                }
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isExist(Context context) {
        // 构建查询条件
        String selection = MediaStore.Audio.Media.DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[]{FILE_NAME};
        // 执行查询
        try (Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.DATA}, // 获取文件路径
                selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                // 获取文件的实际路径
                int columnIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                String filePath = cursor.getString(columnIndex);
                // 检查文件在文件系统中是否存在
                File file = new File(filePath);
                return file.exists();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void write(Context context, String vid) {
        try {
            if (isExist(context)) {//存在的情况下无需写入
                return;
            }
            log.i("write vid " + vid);
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Media.DISPLAY_NAME, FILE_NAME);
            values.put(MediaStore.Audio.Media.RELATIVE_PATH, Environment.DIRECTORY_ALARMS);
            Uri uri = context.getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    byte[] dataToByte = getDataToByte(vid);
                    if (dataToByte != null) {
                        outputStream.write(dataToByte);
                    }
                    outputStream.close();
                }
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                context.sendBroadcast(intent);
            }
        } catch (Exception e) {
            log.i("写入失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private byte[] getDataToByte(String vid) {
        byte[] data = vid.getBytes(StandardCharsets.UTF_8);
        int dataLength = data.length;
        ByteBuffer buffer = ByteBuffer.allocate(2 + dataLength);
        buffer.putShort((short) dataLength);
        buffer.put(data);
        return buffer.array();
    }
}
