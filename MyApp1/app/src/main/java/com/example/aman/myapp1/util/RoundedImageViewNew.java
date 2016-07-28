package com.example.aman.myapp1.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by aman on 18/5/16.
 */
public class RoundedImageViewNew extends ImageView {

    public RoundedImageViewNew(Context context) {
        super(context);
    }

    public RoundedImageViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageViewNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
    }
}
