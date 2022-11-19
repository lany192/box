package com.github.lany192.cropper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.github.lany192.cropper.entity.CropOptions;
import com.github.lany192.cropper.entity.CropResult;
import com.github.lany192.cropper.enums.CropShape;
import com.github.lany192.cropper.enums.Guidelines;
import com.github.lany192.cropper.enums.RequestSizeOptions;
import com.github.lany192.cropper.enums.ScaleType;
import com.github.lany192.cropper.ui.CropImageActivity;
import com.github.lany192.cropper.ui.ResultFragment;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

public final class RxCropper {
    private final String TAG = "RxCropper";
    private CropOptions mOptions;
    @Nullable
    private Uri mSourceUri;

    private RxCropper() {
        this.mOptions = new CropOptions();
    }

    public static RxCropper of() {
        return new RxCropper();
    }

    public RxCropper setSourceUri(Uri uri) {
        this.mSourceUri = uri;
        return this;
    }

    public Observable<CropResult> start(FragmentActivity activity) {
        return start(activity.getSupportFragmentManager());
    }

    public Observable<CropResult> start(Fragment fragment) {
        return start(fragment.getFragmentManager());
    }

    private Observable<CropResult> start(FragmentManager fragmentManager) {
        ResultFragment fragment = (ResultFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = ResultFragment.newInstance();
            fragmentManager.beginTransaction().add(fragment, TAG).commitAllowingStateLoss();
        } else if (fragment.isDetached()) {
            fragmentManager.beginTransaction().attach(fragment).commitAllowingStateLoss();
        }
        return getResult(fragment);
    }

    private Observable<CropResult> getResult(final ResultFragment fragment) {
        return fragment.getAttachSubject()
                .filter(aBoolean -> aBoolean)
                .flatMap((Function<Boolean, ObservableSource<CropResult>>) aBoolean -> {
                    Intent intent = new Intent(fragment.getActivity(), CropImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE, mSourceUri);
                    bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS, mOptions);
                    intent.putExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE, bundle);
                    fragment.startActivityForResult(intent, ResultFragment.REQUEST_CODE);
                    return fragment.getResultSubject();
                }).take(1);
    }


    public RxCropper resetOptions(@NonNull CropOptions options) {
        mOptions = options;
        return this;
    }

    /**
     * The shape of the cropping window.<br>
     * To set square/circle crop shape set aspect ratio to 1:1.<br>
     * <i>Default: RECTANGLE</i>
     */
    public RxCropper setCropShape(@NonNull CropShape shape) {
        mOptions.setCropShape(shape);
        return this;
    }

    /**
     * An edge of the crop window will snap to the corresponding edge of a specified bounding box
     * when the crop window edge is less than or equal to this distance (in pixels) away from the
     * bounding box edge (in pixels).<br>
     * <i>Default: 3dp</i>
     */
    public RxCropper setSnapRadius(float snapRadius) {
        mOptions.setSnapRadius(snapRadius);
        return this;
    }

    /**
     * The radius of the touchable area around the handle (in pixels).<br>
     * We are basing this value off of the recommended 48dp Rhythm.<br>
     * See: http://developer.android.com/design/style/metrics-grids.html#48dp-rhythm<br>
     * <i>Default: 48dp</i>
     */
    public RxCropper setTouchRadius(float touchRadius) {
        mOptions.setTouchRadius(touchRadius);
        return this;
    }


    /**
     * whether the guidelines should be on, off, or only showing when resizing.<br>
     * <i>Default: ON_TOUCH</i>
     */
    public RxCropper setGuidelines(@NonNull Guidelines guidelines) {
        mOptions.setGuidelines(guidelines);
        return this;
    }

    /**
     * The initial scale type of the image in the crop image view<br>
     * <i>Default: FIT_CENTER</i>
     */
    public RxCropper setScaleType(@NonNull ScaleType scaleType) {
        mOptions.setScaleType(scaleType);
        return this;
    }

    /**
     * if to show crop overlay UI what contains the crop window UI surrounded by background over the
     * cropping image.<br>
     * <i>default: true, may disable for animation or frame transition.</i>
     */
    public RxCropper setShowCropOverlay(boolean showCropOverlay) {
        mOptions.setShowCropOverlay(showCropOverlay);
        return this;
    }

    /**
     * if auto-zoom functionality is enabled.<br>
     * default: true.
     */
    public RxCropper setAutoZoomEnabled(boolean autoZoomEnabled) {
        mOptions.setAutoZoomEnabled(autoZoomEnabled);
        return this;
    }

    /**
     * if multi touch functionality is enabled.<br>
     * default: true.
     */
    public RxCropper setMultiTouchEnabled(boolean multiTouchEnabled) {
        mOptions.setMultiTouchEnabled(multiTouchEnabled);
        return this;
    }

    /**
     * The max zoom allowed during cropping.<br>
     * <i>Default: 4</i>
     */
    public RxCropper setMaxZoom(int maxZoom) {
        mOptions.setMaxZoom(maxZoom);
        return this;
    }

    /**
     * The initial crop window padding from image borders in percentage of the cropping image
     * dimensions.<br>
     * <i>Default: 0.1</i>
     */
    public RxCropper setInitialCropWindowPaddingRatio(float initialCropWindowPaddingRatio) {
        mOptions.setInitialCropWindowPaddingRatio(initialCropWindowPaddingRatio);
        return this;
    }

    /**
     * whether the width to height aspect ratio should be maintained or free to change.<br>
     * <i>Default: false</i>
     */
    public RxCropper setFixAspectRatio(boolean fixAspectRatio) {
        mOptions.setFixAspectRatio(fixAspectRatio);
        return this;
    }

    /**
     * the X,Y value of the aspect ratio.<br>
     * Also sets fixes aspect ratio to TRUE.<br>
     * <i>Default: 1/1</i>
     *
     * @param aspectRatioX the width
     * @param aspectRatioY the height
     */
    public RxCropper setAspectRatio(int aspectRatioX, int aspectRatioY) {
        mOptions.setAspectRatioX(aspectRatioX);
        mOptions.setAspectRatioY(aspectRatioY);
        mOptions.setFixAspectRatio(true);
        return this;
    }

    /**
     * the thickness of the guidelines lines (in pixels).<br>
     * <i>Default: 3dp</i>
     */
    public RxCropper setBorderLineThickness(float borderLineThickness) {
        mOptions.setBorderLineThickness(borderLineThickness);
        return this;
    }

    /**
     * the color of the guidelines lines.<br>
     * <i>Default: Color.argb(170, 255, 255, 255)</i>
     */
    public RxCropper setBorderLineColor(int borderLineColor) {
        mOptions.setBorderLineColor(borderLineColor);
        return this;
    }

    /**
     * thickness of the corner line (in pixels).<br>
     * <i>Default: 2dp</i>
     */
    public RxCropper setBorderCornerThickness(float borderCornerThickness) {
        mOptions.setBorderLineThickness(borderCornerThickness);
        return this;
    }

    /**
     * the offset of corner line from crop window border (in pixels).<br>
     * <i>Default: 5dp</i>
     */
    public RxCropper setBorderCornerOffset(float borderCornerOffset) {
        mOptions.setBorderCornerOffset(borderCornerOffset);
        return this;
    }

    /**
     * the length of the corner line away from the corner (in pixels).<br>
     * <i>Default: 14dp</i>
     */
    public RxCropper setBorderCornerLength(float borderCornerLength) {
        mOptions.setBorderCornerLength(borderCornerLength);
        return this;
    }

    /**
     * the color of the corner line.<br>
     * <i>Default: WHITE</i>
     */
    public RxCropper setBorderCornerColor(int borderCornerColor) {
        mOptions.setBorderCornerColor(borderCornerColor);
        return this;
    }

    /**
     * the thickness of the guidelines lines (in pixels).<br>
     * <i>Default: 1dp</i>
     */
    public RxCropper setGuidelinesThickness(float guidelinesThickness) {
        mOptions.setGuidelinesThickness(guidelinesThickness);
        return this;
    }

    /**
     * the color of the guidelines lines.<br>
     * <i>Default: Color.argb(170, 255, 255, 255)</i>
     */
    public RxCropper setGuidelinesColor(int guidelinesColor) {
        mOptions.setGuidelinesColor(guidelinesColor);
        return this;
    }

    /**
     * the color of the overlay background around the crop window cover the image parts not in the
     * crop window.<br>
     * <i>Default: Color.argb(119, 0, 0, 0)</i>
     */
    public RxCropper setBackgroundColor(int backgroundColor) {
        mOptions.setBackgroundColor(backgroundColor);
        return this;
    }

    /**
     * the min size the crop window is allowed to be (in pixels).<br>
     * <i>Default: 42dp, 42dp</i>
     */
    public RxCropper setMinCropWindowSize(int minCropWindowWidth, int minCropWindowHeight) {
        mOptions.setMinCropResultWidth(minCropWindowWidth);
        mOptions.setMaxCropResultHeight(minCropWindowHeight);
        return this;
    }

    /**
     * the min size the resulting cropping image is allowed to be, affects the cropping window
     * limits (in pixels).<br>
     * <i>Default: 40px, 40px</i>
     */
    public RxCropper setMinCropResultSize(int minCropResultWidth, int minCropResultHeight) {
        mOptions.setMinCropResultWidth(minCropResultWidth);
        mOptions.setMinCropResultHeight(minCropResultHeight);
        return this;
    }

    /**
     * the max size the resulting cropping image is allowed to be, affects the cropping window
     * limits (in pixels).<br>
     * <i>Default: 99999, 99999</i>
     */
    public RxCropper setMaxCropResultSize(int maxCropResultWidth, int maxCropResultHeight) {
        mOptions.setMaxCropResultWidth(maxCropResultWidth);
        mOptions.setMaxCropResultHeight(maxCropResultHeight);
        return this;
    }

    /**
     * the title of the {@link CropImageActivity}.<br>
     * <i>Default: ""</i>
     */
    public RxCropper setActivityTitle(CharSequence activityTitle) {
        mOptions.setActivityTitle(activityTitle);
        return this;
    }

    /**
     * the color to use for action bar items icons.<br>
     * <i>Default: NONE</i>
     */
    public RxCropper setActivityMenuIconColor(int activityMenuIconColor) {
        mOptions.setActivityMenuIconColor(activityMenuIconColor);
        return this;
    }

    /**
     * the Android Uri to save the cropped image to.<br>
     * <i>Default: NONE, will create a temp file</i>
     */
    public RxCropper setOutputUri(Uri outputUri) {
        mOptions.setOutputUri(outputUri);
        return this;
    }

    /**
     * the compression format to use when writting the image.<br>
     * <i>Default: JPEG</i>
     */
    public RxCropper setOutputCompressFormat(Bitmap.CompressFormat outputCompressFormat) {
        mOptions.setOutputCompressFormat(outputCompressFormat);
        return this;
    }

    /**
     * the quility (if applicable) to use when writting the image (0 - 100).<br>
     * <i>Default: 90</i>
     */
    public RxCropper setOutputCompressQuality(int outputCompressQuality) {
        mOptions.setOutputCompressQuality(outputCompressQuality);
        return this;
    }


    /**
     * the size to resize the cropped image to.<br>
     * Uses {@link RequestSizeOptions#RESIZE_INSIDE} option.<br>
     * <i>Default: 0, 0 - not set, will not resize</i>
     */
    public RxCropper setRequestedSize(int reqWidth, int reqHeight) {
        return setRequestedSize(reqWidth, reqHeight, RequestSizeOptions.RESIZE_INSIDE);
    }

    /**
     * the size to resize the cropped image to.<br>
     * <i>Default: 0, 0 - not set, will not resize</i>
     */
    public RxCropper setRequestedSize(int reqWidth, int reqHeight, RequestSizeOptions options) {
        mOptions.setOutputRequestWidth(reqWidth);
        mOptions.setOutputRequestHeight(reqHeight);
        mOptions.setOutputRequestSizeOptions(options);
        return this;
    }

    /**
     * if the result of crop image activity should not save the cropped image bitmap.<br>
     * Used if you want to crop the image manually and need only the crop rectangle and rotation
     * data.<br>
     * <i>Default: false</i>
     */
    public RxCropper setNoOutputImage(boolean noOutputImage) {
        mOptions.setNoOutputImage(noOutputImage);
        return this;
    }

    /**
     * the initial rectangle to set on the cropping image after loading.<br>
     * <i>Default: NONE - will initialize using initial crop window padding ratio</i>
     */
    public RxCropper setInitialCropWindowRectangle(Rect initialCropWindowRectangle) {
        mOptions.setInitialCropWindowRectangle(initialCropWindowRectangle);
        return this;
    }

    /**
     * the initial rotation to set on the cropping image after loading (0-360 degrees clockwise).
     * <br>
     * <i>Default: NONE - will read image exif data</i>
     */
    public RxCropper setInitialRotation(int initialRotation) {
        mOptions.setInitialRotation((initialRotation + 360) % 360);
        return this;
    }

    /**
     * if to allow rotation during cropping.<br>
     * <i>Default: true</i>
     */
    public RxCropper setAllowRotation(boolean allowRotation) {
        mOptions.setAllowRotation(allowRotation);
        return this;
    }

    /**
     * if to allow flipping during cropping.<br>
     * <i>Default: true</i>
     */
    public RxCropper setAllowFlipping(boolean allowFlipping) {
        mOptions.setAllowFlipping(allowFlipping);
        return this;
    }

    /**
     * if to allow counter-clockwise rotation during cropping.<br>
     * Note: if rotation is disabled this option has no effect.<br>
     * <i>Default: false</i>
     */
    public RxCropper setAllowCounterRotation(boolean allowCounterRotation) {
        mOptions.setAllowCounterRotation(allowCounterRotation);
        return this;
    }

    /**
     * The amount of degreees to rotate clockwise or counter-clockwise (0-360).<br>
     * <i>Default: 90</i>
     */
    public RxCropper setRotationDegrees(int rotationDegrees) {
        mOptions.setRotationDegrees((rotationDegrees + 360) % 360);
        return this;
    }

    /**
     * whether the image should be flipped horizontally.<br>
     * <i>Default: false</i>
     */
    public RxCropper setFlipHorizontally(boolean flipHorizontally) {
        mOptions.setFlipHorizontally(flipHorizontally);
        return this;
    }

    /**
     * whether the image should be flipped vertically.<br>
     * <i>Default: false</i>
     */
    public RxCropper setFlipVertically(boolean flipVertically) {
        mOptions.setFlipVertically(flipVertically);
        return this;
    }

    /**
     * optional, set crop menu crop button title.<br>
     * <i>Default: null, will use resource string: crop_image_menu_crop</i>
     */
    public RxCropper setCropMenuCropButtonTitle(CharSequence title) {
        mOptions.setCropMenuCropButtonTitle(title);
        return this;
    }

    /**
     * Image resource id to use for crop icon instead of text.<br>
     * <i>Default: 0</i>
     */
    public RxCropper setCropMenuCropButtonIcon(@DrawableRes int drawableResource) {
        mOptions.setCropMenuCropButtonIcon(drawableResource);
        return this;
    }
}
