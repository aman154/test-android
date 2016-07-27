package com.ibusl.android.register.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.ibusl.android.register.utilities.AndroidUtil;

public class NavDrawerDivider extends View {

    private static Paint paint;

    public NavDrawerDivider(Context context) {
        super(context);
        if (paint == null) {
            paint = new Paint();
            paint.setColor(0xffd9d9d9);
            paint.setStrokeWidth(1);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), AndroidUtil.dp(12) + 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(getPaddingLeft(), AndroidUtil.dp(5), getWidth() - getPaddingRight(), AndroidUtil.dp(5), paint);
    }
}
