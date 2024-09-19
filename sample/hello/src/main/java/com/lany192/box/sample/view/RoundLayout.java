package com.lany192.box.sample.view;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;

/**
 * 圆角容器
 */
public class RoundLayout extends FrameLayout {
    private final Path path;
    private final Paint paint;
    private final RectF rectF;
    private final float[] radius;

    public RoundLayout(@NonNull Context var1) {
        this(var1, (AttributeSet) null);
    }

    public RoundLayout(@NonNull Context var1, @Nullable AttributeSet var2) {
        this(var1, var2, 0);
    }

    public RoundLayout(@NonNull Context var1, @Nullable AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.path = new Path();
        this.rectF = new RectF();
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        this.radius = new float[8];
    }

    public void path(float var1, float var2, float var3, float var4) {
        this.radius[0] = var1;
        this.radius[1] = var1;
        this.radius[2] = var2;
        this.radius[3] = var2;
        this.radius[4] = var4;
        this.radius[5] = var4;
        this.radius[6] = var3;
        this.radius[7] = var3;
        this.postInvalidate();
    }

    public void setRadius(float var1) {
        Arrays.fill(this.radius, var1);
        this.postInvalidate();
    }

    public void setTopLeftRadius(float var1) {
        this.radius[0] = var1;
        this.radius[1] = var1;
        this.postInvalidate();
    }

    public void setTopRightRadius(float var1) {
        this.radius[2] = var1;
        this.radius[3] = var1;
        this.postInvalidate();
    }

    public void setBottomRightRadius(float var1) {
        this.radius[5] = var1;
        this.radius[6] = var1;
        this.postInvalidate();
    }

    public void setBottomLeftRadius(float var1) {
        this.radius[6] = var1;
        this.radius[7] = var1;
        this.postInvalidate();
    }

    protected void onSizeChanged(int var1, int var2, int var3, int var4) {
        super.onSizeChanged(var1, var2, var3, var4);
        this.rectF.set(0.0F, 0.0F, (float) var1, (float) var2);
    }

    public void draw(Canvas var1) {
        if (VERSION.SDK_INT >= 28) {
            var1.save();
            var1.clipPath(this.path());
            super.draw(var1);
        } else {
            var1.saveLayer(this.rectF, (Paint) null, ALL_SAVE_FLAG);
            super.draw(var1);
            var1.drawPath(this.path(), this.paint);
        }

        var1.restore();
    }

    protected void dispatchDraw(Canvas var1) {
        if (VERSION.SDK_INT >= 28) {
            var1.save();
            var1.clipPath(this.path());
            super.dispatchDraw(var1);
        } else {
            var1.saveLayer(this.rectF, (Paint) null, ALL_SAVE_FLAG);
            super.dispatchDraw(var1);
            var1.drawPath(this.path(), this.paint);
        }

        var1.restore();
    }

    private Path path() {
        this.path.reset();
        this.path.addRoundRect(this.rectF, this.radius, Direction.CW);
        return this.path;
    }
}
