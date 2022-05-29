package com.github.lany192.text;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StyleableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;

import com.github.lany192.view.R;
import com.google.android.material.textview.MaterialTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 带icon的TextView
 * 1.自定义图标位置
 * 2.自定义图标颜色
 * 3.自定义图标图标和文字之间的距离
 * 4.自定义图标图标大小
 */
public class IconTextView extends MaterialTextView {

    /**
     * Gravity used to position the icon at the start of the view.
     *
     * @see #setIconGravity(int)
     * @see #getIconGravity()
     */
    public static final int ICON_GRAVITY_START = 0x1;

    /**
     * Gravity used to position the icon in the center of the view at the start of the text
     *
     * @see #setIconGravity(int)
     * @see #getIconGravity()
     */
    public static final int ICON_GRAVITY_TEXT_START = 0x2;

    /**
     * Gravity used to position the icon at the end of the view.
     *
     * @see #setIconGravity(int)
     * @see #getIconGravity()
     */
    public static final int ICON_GRAVITY_END = 0x3;

    /**
     * Gravity used to position the icon in the center of the view at the end of the text
     *
     * @see #setIconGravity(int)
     * @see #getIconGravity()
     */
    public static final int ICON_GRAVITY_TEXT_END = 0x4;

    /**
     * Gravity used to position the icon at the top of the view.
     *
     * @see #setIconGravity(int)
     * @see #getIconGravity()
     */
    public static final int ICON_GRAVITY_TOP = 0x10;

    /**
     * Gravity used to position the icon in the center of the view at the top of the text
     *
     * @see #setIconGravity(int)
     * @see #getIconGravity()
     */
    public static final int ICON_GRAVITY_TEXT_TOP = 0x20;
    @Nullable
    private Mode iconTintMode;
    @Nullable
    private ColorStateList iconTint;
    @Nullable
    private Drawable icon;
    @Px
    private int iconSize;
    @Px
    private int iconLeft;
    @Px
    private int iconTop;
    @Px
    private int iconPadding;
    @IconGravity
    private int iconGravity = ICON_GRAVITY_START;
    /**
     * 是否中等粗细
     */
    private boolean middleBold;

    public IconTextView(@NonNull Context context) {
        this(context, null);
    }

    public IconTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.IconTextView, 0, 0);
        if (attributes != null) {
            iconPadding = attributes.getDimensionPixelSize(R.styleable.IconTextView_icon_padding, 0);
            iconTintMode = parseTintMode(attributes.getInt(R.styleable.IconTextView_icon_tint_mode, -1));
            iconTint = getColorStateList(getContext(), attributes, R.styleable.IconTextView_icon_color);
            icon = getDrawable(getContext(), attributes, R.styleable.IconTextView_icon);
            iconGravity = attributes.getInteger(R.styleable.IconTextView_icon_gravity, ICON_GRAVITY_START);
            iconSize = attributes.getDimensionPixelSize(R.styleable.IconTextView_icon_size, 0);
            middleBold = attributes.getBoolean(R.styleable.IconTextView_text_style_middle_bold, false);
            attributes.recycle();
        }
        setText(getText());
        setCompoundDrawablePadding(iconPadding);
        updateIcon(/*needsIconReset=*/icon != null);
    }

    private Drawable getDrawable(
            @NonNull Context context, @NonNull TypedArray attributes, @StyleableRes int index) {
        if (attributes.hasValue(index)) {
            int resourceId = attributes.getResourceId(index, 0);
            if (resourceId != 0) {
                Drawable value = AppCompatResources.getDrawable(context, resourceId);
                if (value != null) {
                    return value;
                }
            }
        }
        return attributes.getDrawable(index);
    }

    private ColorStateList getColorStateList(
            @NonNull Context context, @NonNull TypedArray attributes, @StyleableRes int index) {
        if (attributes.hasValue(index)) {
            int resourceId = attributes.getResourceId(index, 0);
            if (resourceId != 0) {
                ColorStateList value = AppCompatResources.getColorStateList(context, resourceId);
                if (value != null) {
                    return value;
                }
            }
        }
        return attributes.getColorStateList(index);
    }

    private Mode parseTintMode(int value) {
        switch (value) {
            case 3:
                return Mode.SRC_OVER;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                return Mode.ADD;
            default:
                return Mode.SRC_IN;
        }
    }

    /**
     * 是否中等粗细
     */
    public void setTextMiddleBold(boolean middleBold) {
        this.middleBold = middleBold;
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        //防止空指针
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        super.setText(text, type);
        TextPaint paint = getPaint();
        paint.setFakeBoldText(middleBold);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateIconPosition(w, h);
    }

    @Override
    protected void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        super.onTextChanged(charSequence, i, i1, i2);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public void refreshDrawableState() {
        super.refreshDrawableState();
        if (this.icon != null) {
            final int[] state = getDrawableState();
            boolean changed = icon.setState(state);

            // Force the view to draw if icon state has changed.
            if (changed) {
                invalidate();
            }
        }
    }

    private void updateIconPosition(int buttonWidth, int buttonHeight) {
        if (icon == null || getLayout() == null) {
            return;
        }

        if (isIconStart() || isIconEnd()) {
            iconTop = 0;
            if (iconGravity == ICON_GRAVITY_START || iconGravity == ICON_GRAVITY_END) {
                iconLeft = 0;
                updateIcon(/* needsIconReset = */ false);
                return;
            }

            int localIconSize = iconSize == 0 ? icon.getIntrinsicWidth() : iconSize;
            int newIconLeft =
                    (buttonWidth
                            - getTextWidth()
                            - ViewCompat.getPaddingEnd(this)
                            - localIconSize
                            - iconPadding
                            - ViewCompat.getPaddingStart(this))
                            / 2;

            // Only flip the bound value if either isLayoutRTL() or iconGravity is textEnd, but not both
            if (isLayoutRTL() != (iconGravity == ICON_GRAVITY_TEXT_END)) {
                newIconLeft = -newIconLeft;
            }

            if (iconLeft != newIconLeft) {
                iconLeft = newIconLeft;
                updateIcon(/* needsIconReset = */ false);
            }
        } else if (isIconTop()) {
            iconLeft = 0;
            if (iconGravity == ICON_GRAVITY_TOP) {
                iconTop = 0;
                updateIcon(/* needsIconReset = */ false);
                return;
            }

            int localIconSize = iconSize == 0 ? icon.getIntrinsicHeight() : iconSize;
            int newIconTop =
                    (buttonHeight
                            - getTextHeight()
                            - getPaddingTop()
                            - localIconSize
                            - iconPadding
                            - getPaddingBottom())
                            / 2;

            if (iconTop != newIconTop) {
                iconTop = newIconTop;
                updateIcon(/* needsIconReset = */ false);
            }
        }
    }

    private int getTextWidth() {
        Paint textPaint = getPaint();
        String buttonText = getText().toString();
        if (getTransformationMethod() != null) {
            // if text is transformed, add that transformation to to ensure correct calculation
            // of icon padding.
            buttonText = getTransformationMethod().getTransformation(buttonText, this).toString();
        }

        return Math.min((int) textPaint.measureText(buttonText), getLayout().getEllipsizedWidth());
    }

    private int getTextHeight() {
        Paint textPaint = getPaint();
        String buttonText = getText().toString();
        if (getTransformationMethod() != null) {
            // if text is transformed, add that transformation to to ensure correct calculation
            // of icon padding.
            buttonText = getTransformationMethod().getTransformation(buttonText, this).toString();
        }

        Rect bounds = new Rect();
        textPaint.getTextBounds(buttonText, 0, buttonText.length(), bounds);

        return Math.min(bounds.height(), getLayout().getHeight());
    }

    private boolean isLayoutRTL() {
        return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
    }

    /**
     * Gets the padding between the button icon and the button text, if icon is present.
     *
     * @return Padding between the button icon and the button text, if icon is present.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_iconPadding
     * @see #setIconPadding(int)
     */
    @Px
    public int getIconPadding() {
        return iconPadding;
    }

    /**
     * Sets the padding between the button icon and the button text, if icon is present.
     *
     * @param iconPadding Padding between the button icon and the button text, if icon is present.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_iconPadding
     * @see #getIconPadding()
     */
    public void setIconPadding(@Px int iconPadding) {
        if (this.iconPadding != iconPadding) {
            this.iconPadding = iconPadding;
            setCompoundDrawablePadding(iconPadding);
        }
    }

    /**
     * Returns the size of the icon if it was set.
     *
     * @return Returns the size of the icon if it was set in pixels, 0 otherwise.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_iconSize
     * @see #setIconSize(int)
     */
    @Px
    public int getIconSize() {
        return iconSize;
    }

    /**
     * Sets the width and height of the icon. Use 0 to use source Drawable size.
     *
     * @param iconSize new dimension for width and height of the icon in pixels.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_iconSize
     * @see #getIconSize()
     */
    public void setIconSize(@Px int iconSize) {
        if (iconSize < 0) {
            throw new IllegalArgumentException("iconSize cannot be less than 0");
        }

        if (this.iconSize != iconSize) {
            this.iconSize = iconSize;
            updateIcon(/* needsIconReset = */ true);
        }
    }

    /**
     * Gets the icon shown for this button, if present.
     *
     * @return Icon shown for this button, if present.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_icon
     * @see #setIcon(Drawable)
     * @see #setIcon(int)
     */
    public Drawable getIcon() {
        return icon;
    }

    /**
     * Sets the icon drawable resource to show for this button. By default, this icon will be shown on
     * the left side of the button.
     *
     * @param iconResourceId Drawable resource ID to use for the button's icon.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_icon
     * @see #setIcon(Drawable)
     * @see #getIcon()
     */
    public void setIcon(@DrawableRes int iconResourceId) {
        Drawable icon = null;
        if (iconResourceId != 0) {
            icon = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setIcon(icon);
    }

    /**
     * Sets the icon to show for this button. By default, this icon will be shown on the left side of
     * the button.
     *
     * @param icon Drawable to use for the button's icon.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_icon
     * @see #setIcon(int)
     * @see #getIcon()
     */
    public void setIcon(@Nullable Drawable icon) {
        if (this.icon != icon) {
            this.icon = icon;
            updateIcon(/* needsIconReset = */ true);
            updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    /**
     * Sets the tint list color resource for the icon shown for this button.
     *
     * @param iconTintResourceId Tint list color resource for the icon shown for this button.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_iconTint
     * @see #setIconTint(ColorStateList)
     * @see #getIconTint()
     */
    public void setIconTintResource(@ColorRes int iconTintResourceId) {
        setIconTint(AppCompatResources.getColorStateList(getContext(), iconTintResourceId));
    }

    /**
     * Gets the tint list for the icon shown for this button.
     *
     * @return Tint list for the icon shown for this button.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_iconTint
     * @see #setIconTint(ColorStateList)
     * @see #setIconTintResource(int)
     */
    public ColorStateList getIconTint() {
        return iconTint;
    }

    /**
     * Sets the tint list for the icon shown for this button.
     *
     * @param iconTint Tint list for the icon shown for this button.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_iconTint
     * @see #setIconTintResource(int)
     * @see #getIconTint()
     */
    public void setIconTint(@Nullable ColorStateList iconTint) {
        if (this.iconTint != iconTint) {
            this.iconTint = iconTint;
            updateIcon(/* needsIconReset = */ false);
        }
    }

    /**
     * Gets the tint mode for the icon shown for this button.
     *
     * @return Tint mode for the icon shown for this button.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_iconTintMode
     * @see #setIconTintMode(Mode)
     */
    public Mode getIconTintMode() {
        return iconTintMode;
    }

    /**
     * Sets the tint mode for the icon shown for this button.
     *
     * @param iconTintMode Tint mode for the icon shown for this button.
     * @attr ref com.google.android.material.R.styleable#MaterialButton_iconTintMode
     * @see #getIconTintMode()
     */
    public void setIconTintMode(Mode iconTintMode) {
        if (this.iconTintMode != iconTintMode) {
            this.iconTintMode = iconTintMode;
            updateIcon(/* needsIconReset = */ false);
        }
    }

    /**
     * Updates the icon, icon tint, and icon tint mode for this button.
     *
     * @param needsIconReset Whether to force the drawable to be set
     */
    private void updateIcon(boolean needsIconReset) {
        if (icon != null) {
            icon = DrawableCompat.wrap(icon).mutate();
            DrawableCompat.setTintList(icon, iconTint);
            if (iconTintMode != null) {
                DrawableCompat.setTintMode(icon, iconTintMode);
            }
            int width = iconSize != 0 ? iconSize : icon.getIntrinsicWidth();
            int height = iconSize != 0 ? iconSize : icon.getIntrinsicHeight();
            icon.setBounds(iconLeft, iconTop, iconLeft + width, iconTop + height);
            icon.setVisible(true, needsIconReset);
        }

        // Forced icon update
        if (needsIconReset) {
            resetIconDrawable();
            return;
        }

        // Otherwise only update if the icon or the position has changed
        Drawable[] existingDrawables = TextViewCompat.getCompoundDrawablesRelative(this);
        Drawable drawableStart = existingDrawables[0];
        Drawable drawableTop = existingDrawables[1];
        Drawable drawableEnd = existingDrawables[2];
        boolean hasIconChanged =
                (isIconStart() && drawableStart != icon)
                        || (isIconEnd() && drawableEnd != icon)
                        || (isIconTop() && drawableTop != icon);

        if (hasIconChanged) {
            resetIconDrawable();
        }
    }

    private void resetIconDrawable() {
        if (isIconStart()) {
            TextViewCompat.setCompoundDrawablesRelative(this, icon, null, null, null);
        } else if (isIconEnd()) {
            TextViewCompat.setCompoundDrawablesRelative(this, null, null, icon, null);
        } else if (isIconTop()) {
            TextViewCompat.setCompoundDrawablesRelative(this, null, icon, null, null);
        }
    }

    private boolean isIconStart() {
        return iconGravity == ICON_GRAVITY_START || iconGravity == ICON_GRAVITY_TEXT_START;
    }

    private boolean isIconEnd() {
        return iconGravity == ICON_GRAVITY_END || iconGravity == ICON_GRAVITY_TEXT_END;
    }

    private boolean isIconTop() {
        return iconGravity == ICON_GRAVITY_TOP || iconGravity == ICON_GRAVITY_TEXT_TOP;
    }

    @IconGravity
    public int getIconGravity() {
        return iconGravity;
    }

    public void setIconGravity(@IconGravity int iconGravity) {
        if (this.iconGravity != iconGravity) {
            this.iconGravity = iconGravity;
            updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    @IntDef({
            ICON_GRAVITY_START,
            ICON_GRAVITY_TEXT_START,
            ICON_GRAVITY_END,
            ICON_GRAVITY_TEXT_END,
            ICON_GRAVITY_TOP,
            ICON_GRAVITY_TEXT_TOP
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface IconGravity {
    }
}
