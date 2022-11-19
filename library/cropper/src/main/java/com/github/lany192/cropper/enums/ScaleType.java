package com.github.lany192.cropper.enums;

/**
 * Options for scaling the bounds of cropping image to the bounds of Crop Image View.<br>
 * Note: Some options are affected by auto-zoom, if enabled.
 */
public enum ScaleType {

    /**
     * Scale the image uniformly (maintain the image's aspect ratio) to fit in crop image view.<br>
     * The largest dimension will be equals to crop image view and the second dimension will be
     * smaller.
     */
    FIT_CENTER,

    /**
     * Center the image in the view, but perform no scaling.<br>
     * Note: If auto-zoom is enabled and the source image is smaller than crop image view then it
     * will be scaled uniformly to fit the crop image view.
     */
    CENTER,

    /**
     * Scale the image uniformly (maintain the image's aspect ratio) so that both dimensions (width
     * and height) of the image will be equal to or <b>larger</b> than the corresponding dimension
     * of the view (minus padding).<br>
     * The image is then centered in the view.
     */
    CENTER_CROP,

    /**
     * Scale the image uniformly (maintain the image's aspect ratio) so that both dimensions (width
     * and height) of the image will be equal to or <b>less</b> than the corresponding dimension of
     * the view (minus padding).<br>
     * The image is then centered in the view.<br>
     * Note: If auto-zoom is enabled and the source image is smaller than crop image view then it
     * will be scaled uniformly to fit the crop image view.
     */
    CENTER_INSIDE
}