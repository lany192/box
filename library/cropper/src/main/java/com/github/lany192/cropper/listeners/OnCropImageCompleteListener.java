package com.github.lany192.cropper.listeners;

import com.github.lany192.cropper.entity.CropResult;
import com.github.lany192.cropper.view.CropImageView;

/**
 * Interface definition for a callback to be invoked when image async crop is complete.
 */
public interface OnCropImageCompleteListener {

    /**
     * Called when a crop image view has completed cropping image.<br>
     * Result object contains the cropped bitmap, saved cropped image uri, crop points data or the
     * error occured during cropping.
     *
     * @param view   The crop image view that cropping of image was complete.
     * @param result the crop image result data (with cropped image or error)
     */
    void onCropImageComplete(CropImageView view, CropResult result);
}