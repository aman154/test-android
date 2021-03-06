package com.example.aman.myapp1.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by aman on 26/m6/m15.
 */

class CustomProgressBar extends ProgressBar {

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomProgressBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        canvas.save();

        //now we change the matrix
        //We need to rotate around the center of our text
        //Otherwise it rotates around the origin, and that's bad.
        float py = this.getHeight()/2.0f;
        float px = this.getWidth()/2.0f;
        canvas.rotate(180, px, py);

        //draw the text with the matrix applied.
        super.onDraw(canvas);

        //restore the old matrix.
        canvas.restore();
    }
}