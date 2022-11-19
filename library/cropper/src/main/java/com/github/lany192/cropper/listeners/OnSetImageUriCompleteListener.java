package com.github.lany192.cropper.listeners;

import android.net.Uri;

import com.github.lany192.cropper.view.CropImageView;

/**
 * Interface definition for a callback to be invoked when image async loading is complete.
 */
public interface OnSetImageUriCompleteListener {

    /**
     * Called when a crop image view has completed loading image for cropping.<br>
     * If loading failed error parameter will contain the error.
     *
     * @param view  The crop image view that loading of image was complete.
     * @param uri   the URI of the image that was loading
     * @param error if error occurred during loading will contain the error, otherwise null.
     */
    void onSetImageUriComplete(CropImageView view, Uri uri, Exception error);
}
