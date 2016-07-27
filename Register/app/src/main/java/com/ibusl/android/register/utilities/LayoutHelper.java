package com.ibusl.android.register.utilities;

import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ibusl.android.register.utilities.AndroidUtil;

/**
 * Created by aman on 5/4/16.
 */
public class LayoutHelper {

    public static final int MATCH_PARENT = -1;
    public static final int WRAP_CONTENT = -2;

    private static int getSize(float size) {
        return (int) (size < 0 ? size : AndroidUtil.dp(size));
    }

    public static FrameLayout.LayoutParams createFrame(int width, int height){
        return new FrameLayout.LayoutParams(getSize(width),getSize(height));
    }

    public static FrameLayout.LayoutParams createFrame(int width, int height,int gravity){
        return new FrameLayout.LayoutParams(getSize(width),getSize(height),gravity);
    }

    public static FrameLayout.LayoutParams createFrame(int width, int height,int gravity,int leftMargin,int topMargin,int rightMargin, int bottomMargin){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getSize(width),getSize(height),gravity);
        layoutParams.setMargins(AndroidUtil.dp(leftMargin),AndroidUtil.dp(topMargin),AndroidUtil.dp(rightMargin),AndroidUtil.dp(bottomMargin));
        return layoutParams;
    }

    public static RelativeLayout.LayoutParams createRelative(int width, int height){
        return createRelative(width,height,0,0,0,0,0,0,0);
    }

    public static RelativeLayout.LayoutParams createRelative(int width, int height,int leftMargin,int topMargin,int rightMargin, int bottomMargin){
        return createRelative(width,height,leftMargin,topMargin,rightMargin,bottomMargin,0,0,0);
    }

    public static RelativeLayout.LayoutParams createRelative(int width, int height,int leftMargin,int topMargin,int rightMargin, int bottomMargin,int alignParent){
        return createRelative(width,height,leftMargin,topMargin,rightMargin,bottomMargin,alignParent,0,0);
    }

    public static RelativeLayout.LayoutParams createRelative(float width, float height, int leftMargin, int topMargin, int rightMargin, int bottomMargin, int alignParent, int alignRelative, int anchorRelative) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(getSize(width), getSize(height));
        if (alignParent >= 0) {
            layoutParams.addRule(alignParent);
        }
        if (alignRelative >= 0 && anchorRelative >= 0) {
            layoutParams.addRule(alignRelative, anchorRelative);
        }
        layoutParams.leftMargin = AndroidUtil.dp(leftMargin);
        layoutParams.topMargin = AndroidUtil.dp(topMargin);
        layoutParams.rightMargin = AndroidUtil.dp(rightMargin);
        layoutParams.bottomMargin = AndroidUtil.dp(bottomMargin);
        return layoutParams;
    }


}
