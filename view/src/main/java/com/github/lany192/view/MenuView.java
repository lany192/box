package com.github.lany192.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

/**
 * 自定义菜单列表item视图
 */
public class MenuView extends FrameLayout {
    private ImageView mIconImg;

    @ColorInt
    private int titleColor;
    private float titleSize;
    private CharSequence title;
    private TextView titleText;

    @ColorInt
    private int subtitleColor;
    private float subtitleSize;
    private CharSequence subtitle;
    private TextView subtitleText;

    @ColorInt
    private int hintColor;
    private float hintSize;
    private CharSequence hint;
    private TextView hintText;

    private ImageView mArrowImg;
    @DrawableRes
    private int mIconResId;

    private boolean showArrow = true;
    private boolean showIcon = true;

    public MenuView(Context context) {
        super(context);
        init(null, 0);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MenuView, defStyle, 0);
        title = a.getString(R.styleable.MenuView_menu_title);
        titleSize = a.getDimension(R.styleable.MenuView_menu_title_size, 16);
        titleColor = a.getColor(R.styleable.MenuView_menu_title_color, getResources().getColor(R.color.text_2level));

        subtitle = a.getString(R.styleable.MenuView_menu_subtitle);
        subtitleSize = a.getDimension(R.styleable.MenuView_menu_subtitle_size, 11);
        subtitleColor = a.getColor(R.styleable.MenuView_menu_subtitle_color, getResources().getColor(R.color.text_4level));

        hintSize = a.getDimension(R.styleable.MenuView_menu_hint_size, 11);
        hintColor = a.getColor(R.styleable.MenuView_menu_hint_color, getResources().getColor(R.color.text_3level));
        hint = a.getString(R.styleable.MenuView_menu_hint);



        if (a.hasValue(R.styleable.MenuView_menu_icon)) {
            mIconResId = a.getResourceId(R.styleable.MenuView_menu_icon, R.drawable.vector_android);
            showIcon = true;
        } else {
            showIcon = false;
        }
        showArrow = a.getBoolean(R.styleable.MenuView_menu_arrow, true);

        a.recycle();
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_menu, this);
        mIconImg = view.findViewById(R.id.icon);
        mArrowImg = view.findViewById(R.id.arrow);
        titleText = view.findViewById(R.id.title);
        hintText = view.findViewById(R.id.hint);
        subtitleText = view.findViewById(R.id.sub_title);

        if (showIcon) {
            mIconImg.setImageResource(mIconResId);
            mIconImg.setVisibility(View.VISIBLE);
        } else {
            mIconImg.setVisibility(View.GONE);
        }


        titleText.setText(title);
        titleText.setTextColor(titleColor);
        titleText.setTextSize(titleSize);

        if (TextUtils.isEmpty(subtitle)) {
            subtitleText.setVisibility(View.GONE);
        } else {
            subtitleText.setText(subtitle);
            subtitleText.setTextColor(subtitleColor);
            subtitleText.setTextSize(subtitleSize);
            subtitleText.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(hint)) {
            hintText.setVisibility(View.GONE);
        } else {
            hintText.setText(hint);
            hintText.setTextColor(hintColor);
            hintText.setTextSize(hintSize);
            hintText.setVisibility(View.VISIBLE);
        }

        mArrowImg.setVisibility(showArrow ? View.VISIBLE : View.GONE);
    }

    public MenuView icon(@DrawableRes int iconResId) {
        if (showIcon) {
            this.mIconResId = iconResId;
            this.mIconImg.setImageResource(iconResId);
        }
        return this;
    }

    public MenuView name(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            this.title = text;
            this.titleText.setText(text);
        }
        return this;
    }

    public MenuView hint(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            this.hint = text;
            this.hintText.setText(text);
        }
        return this;
    }

    public MenuView hintColor(@ColorInt int color) {
        if (color > 0) {
            this.hintText.setTextColor(color);
        }
        return this;
    }

    public MenuView isShowArrow(boolean isShow) {
        this.showArrow = isShow;
        invalidate();
        return this;
    }


    public MenuView hintTextSize(float size) {
        if (!TextUtils.isEmpty(hint) && size > 0) {
            this.hintText.setTextSize(size);
        }
        return this;
    }

    public MenuView nameTextSize(float size) {
        if (size > 0) {
            this.titleText.setTextSize(size);
        }
        return this;
    }

    public MenuView nameColor(@ColorInt int color) {
        if (color > 0) {
            this.titleText.setTextColor(color);
        }
        return this;
    }
}
