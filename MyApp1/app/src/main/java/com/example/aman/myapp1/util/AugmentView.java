package com.example.aman.myapp1.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aman.myapp1.R;

/**
 * Created by aman on 20/10/15.
 */
public class AugmentView extends RelativeLayout {

    private TextView tittle;
    private TextView detail;
    private TextView timer;
    private ImageView icon;


    public AugmentView(Context context) {
        super(context);
        init();
    }

    public AugmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AugmentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        inflate(getContext(), R.layout.augment_view,this);
        this.tittle = (TextView) findViewById(R.id.augment_tittle_tv);
        this.detail = (TextView) findViewById(R.id.augment_detail_tv);
        this.setTimer((TextView) findViewById(R.id.augment_timer_text));
        this.icon = (ImageView) findViewById(R.id.augment_icon_image);

    }

    public TextView getTittle() {
        return tittle;
    }

    public void setTittle(TextView tittle) {
        this.tittle = tittle;
    }

    public TextView getDetail() {
        return detail;
    }

    public void setDetail(TextView detail) {
        this.detail = detail;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public TextView getTimer() {
        return timer;
    }

    public void setTimer(TextView timer) {
        this.timer = timer;
    }
}
