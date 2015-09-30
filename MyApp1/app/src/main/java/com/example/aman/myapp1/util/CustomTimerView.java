package com.example.aman.myapp1.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.aman.myapp1.R;

/**
 * Created by aman on 8/9/15.
 */
public class CustomTimerView extends View {

    private float circle_radius;
    private float text_size;
    private int text_color;
    private int timer_count;


    public CustomTimerView(Context context) {
        this(context, null);
    }

    public CustomTimerView(Context context,AttributeSet attrSet){
        this(context, attrSet, R.attr.CustomTimerViewStyle);
    }


    public CustomTimerView(Context context, AttributeSet attrSet, int defStyle) {
        super(context, attrSet, defStyle);


        TypedArray typedArray = context.obtainStyledAttributes(attrSet,R.styleable.CustomTimerView,defStyle,0);

        circle_radius = typedArray.getDimension(R.styleable.CustomTimerView_circle_radius,10);
        text_size = typedArray.getDimension(R.styleable.CustomTimerView_text_size,8);
        text_color = typedArray.getColor(R.styleable.CustomTimerView_text_color,getResources().getColor(R.color.textcolor1));
        timer_count = typedArray.getInteger(R.styleable.CustomTimerView_timer_counts,1);


        typedArray.recycle();
    }

    public float getCircle_radius() {
        return circle_radius;
    }

    public void setCircle_radius(float circle_radius) {
        this.circle_radius = circle_radius;
    }

    public int getTimer_count() {
        return timer_count;
    }

    public void setTimer_count(int timer_count) {
        this.timer_count = timer_count;
    }

    public float getText_size() {
        return text_size;
    }


    public void setText_size(float text_size) {
        this.text_size = text_size;
    }

    public int getText_color() {
        return text_color;
    }

    public void setText_color(int text_color) {
        this.text_color = text_color;
    }

    public void textScaleAnim(TextView textView){

      ScaleAnimation scaleAnimation = new ScaleAnimation(0,0,1,1,0.5f,0.5f);
        scaleAnimation.setDuration(300);
        textView.startAnimation(scaleAnimation);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
