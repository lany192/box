package com.github.lany192.cropper;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.github.lany192.cropper.view.OverlayView;

/**
 * Animation to handle smooth cropping image matrix transformation change, specifically for
 * zoom-in/out.
 */
public final class ImageAnimation extends Animation implements Animation.AnimationListener {

    // region: Fields and Consts

    private final ImageView mImageView;

    private final OverlayView mOverlayView;

    private final float[] mStartBoundPoints = new float[8];

    private final float[] mEndBoundPoints = new float[8];

    private final RectF mStartCropWindowRect = new RectF();

    private final RectF mEndCropWindowRect = new RectF();

    private final float[] mStartImageMatrix = new float[9];

    private final float[] mEndImageMatrix = new float[9];

    private final RectF mAnimRect = new RectF();

    private final float[] mAnimPoints = new float[8];

    private final float[] mAnimMatrix = new float[9];
    // endregion

    public ImageAnimation(ImageView cropImageView, OverlayView OverlayView) {
        mImageView = cropImageView;
        mOverlayView = OverlayView;

        setDuration(300);
        setFillAfter(true);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setAnimationListener(this);
    }

    public void setStartState(float[] boundPoints, Matrix imageMatrix) {
        reset();
        System.arraycopy(boundPoints, 0, mStartBoundPoints, 0, 8);
        mStartCropWindowRect.set(mOverlayView.getCropWindowRect());
        imageMatrix.getValues(mStartImageMatrix);
    }

    public void setEndState(float[] boundPoints, Matrix imageMatrix) {
        System.arraycopy(boundPoints, 0, mEndBoundPoints, 0, 8);
        mEndCropWindowRect.set(mOverlayView.getCropWindowRect());
        imageMatrix.getValues(mEndImageMatrix);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        mAnimRect.left =
                mStartCropWindowRect.left
                        + (mEndCropWindowRect.left - mStartCropWindowRect.left) * interpolatedTime;
        mAnimRect.top =
                mStartCropWindowRect.top
                        + (mEndCropWindowRect.top - mStartCropWindowRect.top) * interpolatedTime;
        mAnimRect.right =
                mStartCropWindowRect.right
                        + (mEndCropWindowRect.right - mStartCropWindowRect.right) * interpolatedTime;
        mAnimRect.bottom =
                mStartCropWindowRect.bottom
                        + (mEndCropWindowRect.bottom - mStartCropWindowRect.bottom) * interpolatedTime;
        mOverlayView.setCropWindowRect(mAnimRect);

        for (int i = 0; i < mAnimPoints.length; i++) {
            mAnimPoints[i] =
                    mStartBoundPoints[i] + (mEndBoundPoints[i] - mStartBoundPoints[i]) * interpolatedTime;
        }
        mOverlayView.setBounds(mAnimPoints, mImageView.getWidth(), mImageView.getHeight());

        for (int i = 0; i < mAnimMatrix.length; i++) {
            mAnimMatrix[i] =
                    mStartImageMatrix[i] + (mEndImageMatrix[i] - mStartImageMatrix[i]) * interpolatedTime;
        }
        Matrix m = mImageView.getImageMatrix();
        m.setValues(mAnimMatrix);
        mImageView.setImageMatrix(m);

        mImageView.invalidate();
        mOverlayView.invalidate();
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mImageView.clearAnimation();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
