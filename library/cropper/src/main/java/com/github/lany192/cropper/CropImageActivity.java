

package com.github.lany192.cropper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.lany192.cropper.entity.CropOptions;
import com.github.lany192.cropper.entity.CropResult;
import com.github.lany192.cropper.listeners.OnCropImageCompleteListener;
import com.github.lany192.cropper.listeners.OnSetImageUriCompleteListener;
import com.github.lany192.cropper.view.CropImageView;

import java.io.File;
import java.io.IOException;

public class CropImageActivity extends AppCompatActivity implements OnSetImageUriCompleteListener, OnCropImageCompleteListener {
    private CropImageView mCropImageView;
    private CropOptions mOptions;
    private final String TAG = getClass().getSimpleName();

    public static Intent getIntent(Context context, Uri uri, CropOptions options) {
        Intent intent = new Intent(context, CropImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE, uri);
        bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS, options);
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE, bundle);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cropper_activity_crop);
        mCropImageView = findViewById(R.id.cropImageView);

        Bundle bundle = getIntent().getBundleExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE);
        Uri mCropImageUri = bundle.getParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE);
        mOptions = bundle.getParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS);

        if (savedInstanceState == null) {
            mCropImageView.setImageUriAsync(mCropImageUri);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            CharSequence title = mOptions != null && mOptions.getActivityTitle() != null && mOptions.getActivityTitle().length() > 0 ? mOptions.getActivityTitle() : getResources().getString(R.string.crop_image_activity_title);
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCropImageView.setOnSetImageUriCompleteListener(this);
        mCropImageView.setOnCropImageCompleteListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCropImageView.setOnSetImageUriCompleteListener(null);
        mCropImageView.setOnCropImageCompleteListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop_image_menu, menu);
        if (!mOptions.isAllowRotation()) {
            menu.removeItem(R.id.crop_image_menu_rotate_left);
            menu.removeItem(R.id.crop_image_menu_rotate_right);
        } else if (mOptions.isAllowCounterRotation()) {
            menu.findItem(R.id.crop_image_menu_rotate_left).setVisible(true);
        }
        if (!mOptions.isAllowFlipping()) {
            menu.removeItem(R.id.crop_image_menu_flip);
        }
        if (mOptions.getCropMenuCropButtonTitle() != null) {
            menu.findItem(R.id.crop_image_menu_crop).setTitle(mOptions.getCropMenuCropButtonTitle());
        }
        Drawable cropIcon = null;
        try {
            if (mOptions.getCropMenuCropButtonIcon() != 0) {
                cropIcon = ContextCompat.getDrawable(this, mOptions.getCropMenuCropButtonIcon());
                menu.findItem(R.id.crop_image_menu_crop).setIcon(cropIcon);
            }
        } catch (Exception e) {
            Log.w("AIC", "Failed to read menu crop drawable", e);
        }

        if (mOptions.getActivityMenuIconColor() != 0) {
            updateMenuItemIconColor(
                    menu, R.id.crop_image_menu_rotate_left, mOptions.getActivityMenuIconColor());
            updateMenuItemIconColor(
                    menu, R.id.crop_image_menu_rotate_right, mOptions.getActivityMenuIconColor());
            updateMenuItemIconColor(menu, R.id.crop_image_menu_flip, mOptions.getActivityMenuIconColor());
            if (cropIcon != null) {
                updateMenuItemIconColor(menu, R.id.crop_image_menu_crop, mOptions.getActivityMenuIconColor());
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.crop_image_menu_crop) {
            cropImage();
            return true;
        }
        if (item.getItemId() == R.id.crop_image_menu_rotate_left) {
            rotateImage(-mOptions.getRotationDegrees());
            return true;
        }
        if (item.getItemId() == R.id.crop_image_menu_rotate_right) {
            rotateImage(mOptions.getRotationDegrees());
            return true;
        }
        if (item.getItemId() == R.id.crop_image_menu_flip_horizontally) {
            mCropImageView.flipImageHorizontally();
            return true;
        }
        if (item.getItemId() == R.id.crop_image_menu_flip_vertically) {
            mCropImageView.flipImageVertically();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            setResultCancel();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResultCancel();
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        if (error == null) {
            if (mOptions.getInitialCropWindowRectangle() != null) {
                mCropImageView.setCropRect(mOptions.getInitialCropWindowRectangle());
            }
            if (mOptions.getInitialRotation() > -1) {
                mCropImageView.setRotatedDegrees(mOptions.getInitialRotation());
            }
        } else {
            setResult(null, error, 1);
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropResult result) {
        setResult(result.getUri(), result.getError(), result.getSampleSize());
    }

    // region: Private methods

    /**
     * Execute crop image and save the result tou output uri.
     */
    protected void cropImage() {
        if (mOptions.isNoOutputImage()) {
            setResult(null, null, 1);
        } else {
            Uri outputUri = getOutputUri();
            mCropImageView.saveCroppedImageAsync(
                    outputUri,
                    mOptions.getOutputCompressFormat(),
                    mOptions.getOutputCompressQuality(),
                    mOptions.getOutputRequestWidth(),
                    mOptions.getOutputRequestHeight(),
                    mOptions.getOutputRequestSizeOptions());
        }
    }

    /**
     * Rotate the image in the crop image view.
     */
    protected void rotateImage(int degrees) {
        mCropImageView.rotateImage(degrees);
    }

    /**
     * Get Android uri to save the cropped image into.<br>
     * Use the given in options or create a temp file.
     */
    protected Uri getOutputUri() {
        Uri outputUri = mOptions.getOutputUri();
        if (outputUri == null || outputUri.equals(Uri.EMPTY)) {
            try {
                String ext =
                        mOptions.getOutputCompressFormat() == Bitmap.CompressFormat.JPEG
                                ? ".jpg"
                                : mOptions.getOutputCompressFormat() == Bitmap.CompressFormat.PNG ? ".png" : ".webp";
                outputUri = Uri.fromFile(File.createTempFile("cropped", ext, getCacheDir()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to create temp file for output image", e);
            }
        }
        return outputUri;
    }

    /**
     * Result with cropped image data or error if failed.
     */
    protected void setResult(Uri uri, Exception error, int sampleSize) {
        int resultCode = error == null ? RESULT_OK : CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;
        setResult(resultCode, getResultIntent(uri, error, sampleSize));
        finish();
    }

    /**
     * Cancel of cropping activity.
     */
    protected void setResultCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    /**
     * Get intent instance to be used for the result of this activity.
     */
    protected Intent getResultIntent(Uri uri, Exception error, int sampleSize) {
        CropResult result = new CropResult();
        result.setOriginalUri(mCropImageView.getImageUri());
        result.setUri(uri);
        result.setError(error);
        result.setCropPoints(mCropImageView.getCropPoints());
        result.setCropRect(mCropImageView.getCropRect());
        result.setRotation(mCropImageView.getRotatedDegrees());
        result.setWholeImageRect(mCropImageView.getWholeImageRect());
        result.setSampleSize(sampleSize);
        result.setCropShape(mCropImageView.getCropShape());
        Intent intent = new Intent();
        intent.putExtras(getIntent());
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_RESULT, result);
        return intent;
    }

    /**
     * Update the color of a specific menu item to the given color.
     */
    private void updateMenuItemIconColor(Menu menu, int itemId, int color) {
        MenuItem menuItem = menu.findItem(itemId);
        if (menuItem != null) {
            Drawable menuItemIcon = menuItem.getIcon();
            if (menuItemIcon != null) {
                try {
                    menuItemIcon.mutate();
                    menuItemIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                    menuItem.setIcon(menuItemIcon);
                } catch (Exception e) {
                    Log.w(TAG, "Failed to update menu item color", e);
                }
            }
        }
    }
}
