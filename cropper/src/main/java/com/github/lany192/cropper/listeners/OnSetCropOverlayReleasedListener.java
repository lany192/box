package com.github.lany192.cropper.listeners;

import android.graphics.Rect;

/**
 * Interface definition for a callback to be invoked when the crop overlay is released.
 */
public interface OnSetCropOverlayReleasedListener {

    /**
     * Called when the crop overlay changed listener is called and inProgress is false.
     *
     * @param rect The rect coordinates of the cropped overlay
     */
    void onCropOverlayReleased(Rect rect);
}