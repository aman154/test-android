package com.example.aman.myapp1.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aman.myapp1.R;

public class CountDownTimerActivity extends ActionBarActivity {

    private TextView timer_text,finish_text;

    private ImageView timer_image,image_cover;
    private Button start_button;

    private Animation imageAnimation;
    private AnimationDrawable frameAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_timer);

        timer_text = (TextView) findViewById(R.id.timer_text);
        finish_text = (TextView) findViewById(R.id.finish_text);
        timer_image = (ImageView) findViewById(R.id.timer_circle);
        image_cover = (ImageView) findViewById(R.id.timer_circle_cover);

        start_button = (Button) findViewById(R.id.start_button);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.start();
            }
        });
    }

    CountDownTimer countDownTimer = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

            finish_text.setVisibility(View.GONE);
            image_cover.setVisibility(View.GONE);
            timer_text.setText(""+millisUntilFinished/1000);
            timer_text.setVisibility(View.VISIBLE);
            imageAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.scale_anim);
            timer_text.startAnimation(imageAnimation);
            blink_bg();
        }

        @Override
        public void onFinish() {

            timer_text.setVisibility(View.GONE);
            image_cover.setVisibility(View.VISIBLE);
            finish_text.setVisibility(View.VISIBLE);
            imageAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.scale_anim);
            finish_text.startAnimation(imageAnimation);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_count_down_timer, menu);
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


    private void blink_bg() {

        timer_image.setBackgroundResource(R.drawable.blink_bg_anim);
        timer_image.setVisibility(View.VISIBLE);
        frameAnimation = (AnimationDrawable) timer_image.getBackground();
        frameAnimation.start();
    }
}
