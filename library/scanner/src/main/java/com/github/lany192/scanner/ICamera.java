package com.github.lany192.scanner;


import androidx.annotation.Nullable;
import androidx.camera.core.Camera;

/**
 * @author lany192
 */
public interface ICamera {

    /**
     * 启动相机预览
     */
    void startCamera();

    /**
     * 停止相机预览
     */
    void stopCamera();

    /**
     * 获取{@link Camera}
     *
     * @return
     */
    @Nullable
    Camera getCamera();

    /**
     * 释放
     */
    void release();

}
