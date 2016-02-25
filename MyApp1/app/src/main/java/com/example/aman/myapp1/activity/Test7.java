package com.example.aman.myapp1.activity;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aman.myapp1.R;

public class Test7 extends ActionBarActivity {

    ImageView mProgressBar,progressBar_dot;
    TextView mTextView;
    Button mButton;
    Animation animation;
    FrameLayout progressBar_dot_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test72);

        progressBar_dot_rl  = (FrameLayout) findViewById(R.id.progressbar_circle_fl);
        mButton = (Button) findViewById(R.id.button_test7);
        mTextView = (TextView) findViewById(R.id.progressbar_counter_text);
      /*  mProgressBar = (ImageView) findViewById(R.id.progressBar7);
        progressBar_dot = (ImageView) findViewById(R.id.progressBar_dot);*/

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCountDownTimer countDownTimer = new MyCountDownTimer(30000,1000);
                countDownTimer.start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test7, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyCountDownTimer extends CountDownTimer{

        public MyCountDownTimer(long startTime, long interval){
            super(startTime,interval);
            animation = AnimationUtils.loadAnimation(Test7.this, R.anim.otp_screen_progressbar_rotate_anim);
            progressBar_dot_rl.startAnimation(animation);

        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setText("" + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {

        }
    }
}
