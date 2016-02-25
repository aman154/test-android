package com.example.aman.myapp1.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.widget.ImageView;

/**
 * Created by aman on 2/2/16.
 */
public class CustomImageView extends ImageView {

    static DisplayMetrics displayMetrics;
    private int img_height;
    private int img_weight;
    private int img_screen_ratio;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomImageView(Context context) {
        super(context);
    }

    public static int dpToPx(Activity activity, int dp) {
        if (displayMetrics == null) displayMetrics = activity.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public static int[] get_display_size(Activity mActivity) {
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int[] arr = new int[2];
        arr[0] = width;
        arr[1] = height;
        return arr;
    }


    public int getImg_height() {
        return img_height;
    }

    public void setImg_height(int img_height) {
        this.img_height = img_height;
    }

    public int getImg_weight() {
        return img_weight;
    }

    public void setImg_weight(int img_weight) {
        this.img_weight = img_weight;
    }

    public int getImg_screen_ratio() {
        return img_screen_ratio;
    }

    public void setImg_screen_ratio(int img_screen_ratio) {
        this.img_screen_ratio = img_screen_ratio;
    }
}
