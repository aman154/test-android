package com.example.aman.myapp1.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by aman on 18/5/16.
 */
public class RoundedImageDrawble extends Drawable {
    private boolean mRoundTop;
    private boolean mRoundBottom;
    private float mRadius;
    private int mMargin;
    private Paint mPaint;
    private RectF mRectF = new RectF();
    private BitmapShader mBitmapShader;


    public RoundedImageDrawble(Bitmap bitmap, float radius, boolean roundTop, boolean roundBottom,int margin){
        mRadius = radius;
        mRoundTop = roundTop;
        mRoundBottom = roundBottom;
        mMargin = margin;

        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(mBitmapShader);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mRectF.set(mMargin, mMargin, bounds.width() - mMargin, bounds.height() - mMargin);

        RadialGradient vignette = new RadialGradient(
                mRectF.centerX(), mRectF.centerY() * 1.0f / 0.7f, mRectF.centerX() * 1.3f,
                new int[] { 0, 0, 0x7f000000 }, new float[] { 0.0f, 0.7f, 1.0f },
                Shader.TileMode.CLAMP);

        Matrix oval = new Matrix();
        oval.setScale(1.0f, 0.7f);
        vignette.setLocalMatrix(oval);

        mPaint.setShader(
                new ComposeShader(mBitmapShader, vignette, PorterDuff.Mode.SRC_OVER));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
