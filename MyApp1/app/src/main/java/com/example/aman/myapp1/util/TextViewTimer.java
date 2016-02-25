package com.example.aman.myapp1.util;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by aman on 17/1/16.
 */
public class TextViewTimer extends CountDownTimer {
    TextView textView;

    public TextViewTimer(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if(textView != null) {
            textView.setText("" + String.format(AppUtil.FORMAT,
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
        }
    }

    @Override
    public void onFinish() {
        if(textView != null) {
            textView.setText("Done !");
        }
    }

}