package com.github.lany192.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 手写板
 */
public class FingerPaintView extends View {
    private Path path;
    private Bitmap drawingBitmap;
    private Canvas drawingCanvas;
    private Paint drawingPaint;
    private float penX = 0.0f;
    private float penY = 0.0f;
    private Paint paint;
    private boolean isEmpty = true;

    public FingerPaintView(Context context) {
        super(context);
        init();
    }

    public FingerPaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FingerPaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        drawingPaint = new Paint(Paint.DITHER_FLAG);
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(36f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawingBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawingCanvas = new Canvas(drawingBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas != null) {
            canvas.drawBitmap(drawingBitmap, 0f, 0f, drawingPaint);
            canvas.drawPath(path, paint);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event == null)
            return false;
        isEmpty = false;
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(x, y);
                penX = x;
                penY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - penX);
                float dy = Math.abs(y - penY);
                float touchTolerance = 4f;
                if (dx >= touchTolerance || dy >= touchTolerance) {
                    path.quadTo(penX, penY, (x + penX) / 2, (y + penY) / 2);
                    penX = x;
                    penY = y;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(penX, penY);
                drawingCanvas.drawPath(path, paint);
                path.reset();
                performClick();
                invalidate();
                break;
        }
        super.onTouchEvent(event);
        return true;
    }

    public void clear() {
        path.reset();
        drawingBitmap = Bitmap.createBitmap(drawingBitmap.getWidth(), drawingBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        drawingCanvas = new Canvas(drawingBitmap);
        isEmpty = true;
        invalidate();
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Bitmap exportToBitmap(int width, int height) {
        Bitmap rawBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(rawBitmap);
        Drawable bgDrawable = getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        draw(canvas);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(rawBitmap, width, height, false);
        rawBitmap.recycle();
        return scaledBitmap;
    }
}
