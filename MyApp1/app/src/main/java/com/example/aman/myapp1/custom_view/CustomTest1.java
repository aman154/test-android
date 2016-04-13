package com.example.aman.myapp1.custom_view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by aman on 14/3/16.
 */
public class CustomTest1 extends View {

    public CustomTest1(Context context) {
        super(context);
    }

    public CustomTest1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTest1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) public CustomTest1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
