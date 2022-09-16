
package com.github.lany192.cropper.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import com.github.lany192.cropper.entity.BitmapSampled;
import com.github.lany192.cropper.enums.RequestSizeOptions;
import com.github.lany192.cropper.utils.BitmapUtils;
import com.github.lany192.cropper.view.CropImageView;

import java.lang.ref.WeakReference;

/**
 * Task to crop bitmap asynchronously from the UI thread.
 */
public final class CropTask extends AsyncTask<Void, Void, CropTask.Result> {

    // region: Fields and Consts

    /**
     * Use a WeakReference to ensure the ImageView can be garbage collected
     */
    private final WeakReference<CropImageView> mCropImageViewReference;

    /**
     * the bitmap to crop
     */
    private final Bitmap mBitmap;

    /**
     * The Android URI of the image to load
     */
    private final Uri mUri;

    /**
     * The context of the crop image view widget used for loading of bitmap by Android URI
     */
    private final Context mContext;

    /**
     * Required cropping 4 points (x0,y0,x1,y1,x2,y2,x3,y3)
     */
    private final float[] mCropPoints;

    /**
     * Degrees the image was rotated after loading
     */
    private final int mDegreesRotated;

    /**
     * the original width of the image to be cropped (for image loaded from URI)
     */
    private final int mOrgWidth;

    /**
     * the original height of the image to be cropped (for image loaded from URI)
     */
    private final int mOrgHeight;

    /**
     * is there is fixed aspect ratio for the crop rectangle
     */
    private final boolean mFixAspectRatio;

    /**
     * the X aspect ration of the crop rectangle
     */
    private final int mAspectRatioX;

    /**
     * the Y aspect ration of the crop rectangle
     */
    private final int mAspectRatioY;

    /**
     * required width of the cropping image
     */
    private final int mReqWidth;

    /**
     * required height of the cropping image
     */
    private final int mReqHeight;

    /**
     * is the image flipped horizontally
     */
    private final boolean mFlipHorizontally;

    /**
     * is the image flipped vertically
     */
    private final boolean mFlipVertically;

    /**
     * The option to handle requested width/height
     */
    private final RequestSizeOptions mReqSizeOptions;

    /**
     * the Android Uri to save the cropped image to
     */
    private final Uri mSaveUri;

    /**
     * the compression format to use when writing the image
     */
    private final Bitmap.CompressFormat mSaveCompressFormat;

    /**
     * the quality (if applicable) to use when writing the image (0 - 100)
     */
    private final int mSaveCompressQuality;
    // endregion

    public CropTask(
            CropImageView cropImageView,
            Bitmap bitmap,
            float[] cropPoints,
            int degreesRotated,
            boolean fixAspectRatio,
            int aspectRatioX,
            int aspectRatioY,
            int reqWidth,
            int reqHeight,
            boolean flipHorizontally,
            boolean flipVertically,
            RequestSizeOptions options,
            Uri saveUri,
            Bitmap.CompressFormat saveCompressFormat,
            int saveCompressQuality) {

        mCropImageViewReference = new WeakReference<>(cropImageView);
        mContext = cropImageView.getContext();
        mBitmap = bitmap;
        mCropPoints = cropPoints;
        mUri = null;
        mDegreesRotated = degreesRotated;
        mFixAspectRatio = fixAspectRatio;
        mAspectRatioX = aspectRatioX;
        mAspectRatioY = aspectRatioY;
        mReqWidth = reqWidth;
        mReqHeight = reqHeight;
        mFlipHorizontally = flipHorizontally;
        mFlipVertically = flipVertically;
        mReqSizeOptions = options;
        mSaveUri = saveUri;
        mSaveCompressFormat = saveCompressFormat;
        mSaveCompressQuality = saveCompressQuality;
        mOrgWidth = 0;
        mOrgHeight = 0;
    }

    public CropTask(
            CropImageView cropImageView,
            Uri uri,
            float[] cropPoints,
            int degreesRotated,
            int orgWidth,
            int orgHeight,
            boolean fixAspectRatio,
            int aspectRatioX,
            int aspectRatioY,
            int reqWidth,
            int reqHeight,
            boolean flipHorizontally,
            boolean flipVertically,
            RequestSizeOptions options,
            Uri saveUri,
            Bitmap.CompressFormat saveCompressFormat,
            int saveCompressQuality) {

        mCropImageViewReference = new WeakReference<>(cropImageView);
        mContext = cropImageView.getContext();
        mUri = uri;
        mCropPoints = cropPoints;
        mDegreesRotated = degreesRotated;
        mFixAspectRatio = fixAspectRatio;
        mAspectRatioX = aspectRatioX;
        mAspectRatioY = aspectRatioY;
        mOrgWidth = orgWidth;
        mOrgHeight = orgHeight;
        mReqWidth = reqWidth;
        mReqHeight = reqHeight;
        mFlipHorizontally = flipHorizontally;
        mFlipVertically = flipVertically;
        mReqSizeOptions = options;
        mSaveUri = saveUri;
        mSaveCompressFormat = saveCompressFormat;
        mSaveCompressQuality = saveCompressQuality;
        mBitmap = null;
    }

    /**
     * The Android URI that this task is currently loading.
     */
    public Uri getUri() {
        return mUri;
    }

    /**
     * Crop image in background.
     *
     * @param params ignored
     * @return the decoded bitmap data
     */
    @Override
    protected Result doInBackground(Void... params) {
        try {
            if (!isCancelled()) {

                BitmapSampled bitmapSampled;
                if (mUri != null) {
                    bitmapSampled =
                            BitmapUtils.cropBitmap(
                                    mContext,
                                    mUri,
                                    mCropPoints,
                                    mDegreesRotated,
                                    mOrgWidth,
                                    mOrgHeight,
                                    mFixAspectRatio,
                                    mAspectRatioX,
                                    mAspectRatioY,
                                    mReqWidth,
                                    mReqHeight,
                                    mFlipHorizontally,
                                    mFlipVertically);
                } else if (mBitmap != null) {
                    bitmapSampled =
                            BitmapUtils.cropBitmapObjectHandleOOM(
                                    mBitmap,
                                    mCropPoints,
                                    mDegreesRotated,
                                    mFixAspectRatio,
                                    mAspectRatioX,
                                    mAspectRatioY,
                                    mFlipHorizontally,
                                    mFlipVertically);
                } else {
                    return new Result((Bitmap) null, 1);
                }

                Bitmap bitmap =
                        BitmapUtils.resizeBitmap(bitmapSampled.getBitmap(), mReqWidth, mReqHeight, mReqSizeOptions);

                if (mSaveUri == null) {
                    return new Result(bitmap, bitmapSampled.getSampleSize());
                } else {
                    BitmapUtils.writeBitmapToUri(
                            mContext, bitmap, mSaveUri, mSaveCompressFormat, mSaveCompressQuality);
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    return new Result(mSaveUri, bitmapSampled.getSampleSize());
                }
            }
            return null;
        } catch (Exception e) {
            return new Result(e, mSaveUri != null);
        }
    }

    /**
     * Once complete, see if ImageView is still around and set bitmap.
     *
     * @param result the result of bitmap cropping
     */
    @Override
    protected void onPostExecute(Result result) {
        if (result != null) {
            boolean completeCalled = false;
            if (!isCancelled()) {
                CropImageView cropImageView = mCropImageViewReference.get();
                if (cropImageView != null) {
                    completeCalled = true;
                    cropImageView.onImageCroppingAsyncComplete(result);
                }
            }
            if (!completeCalled && result.bitmap != null) {
                // fast release of unused bitmap
                result.bitmap.recycle();
            }
        }
    }

    // region: Inner class: Result

    /**
     * The result of CropTask async loading.
     */
    public static final class Result {

        /**
         * The cropped bitmap
         */
        public final Bitmap bitmap;

        /**
         * The saved cropped bitmap uri
         */
        public final Uri uri;

        /**
         * The error that occurred during async bitmap cropping.
         */
        public final Exception error;

        /**
         * is the cropping request was to get a bitmap or to save it to uri
         */
        public final boolean isSave;

        /**
         * sample size used creating the crop bitmap to lower its size
         */
        public final int sampleSize;

        public Result(Bitmap bitmap, int sampleSize) {
            this.bitmap = bitmap;
            this.uri = null;
            this.error = null;
            this.isSave = false;
            this.sampleSize = sampleSize;
        }

        public Result(Uri uri, int sampleSize) {
            this.bitmap = null;
            this.uri = uri;
            this.error = null;
            this.isSave = true;
            this.sampleSize = sampleSize;
        }

        public Result(Exception error, boolean isSave) {
            this.bitmap = null;
            this.uri = null;
            this.error = error;
            this.isSave = isSave;
            this.sampleSize = 1;
        }
    }
    // endregion
}
