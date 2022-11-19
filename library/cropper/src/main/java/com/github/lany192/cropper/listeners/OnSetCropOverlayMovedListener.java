package com.github.lany192.cropper.listeners;

import android.graphics.Rect;

/**
 * Interface definition for a callback to be invoked when the crop overlay is released.
 */
public interface OnSetCropOverlayMovedListener {

    /**
     * Called when the crop overlay is moved
     *
     * @param rect The rect coordinates of the cropped overlay
     */
    void onCropOverlayMoved(Rect rect);
}