package com.example.aman.myapp1.activity;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aman.myapp1.R;


public class Feature6 extends ActionBarActivity {


    Button start;
    ProgressBar progressBar,progressBar2;
    MyCountDownTimer countDownTimer;
    TextView text;
    int i= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature6);

        start = (Button) findViewById(R.id.start);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        text = (TextView) findViewById(R.id.text);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(100);
                progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar1));
                progressBar2.setRotation((-100 / 100f * 360f) - 90f);
                progressBar2.setVisibility(View.VISIBLE);
                progressBar2.setProgress(100);
                countDownTimer = new MyCountDownTimer(10000, 500);
                countDownTimer.start();
            }
        });


    }

    private class MyCountDownTimer extends CountDownTimer {

                public MyCountDownTimer(long millisInFuture, long countDownInterval) {
                    super(millisInFuture, countDownInterval);
                }

                @Override
                public void onTick(long millisUntilFinished) {
                    text.setText(String.valueOf(millisUntilFinished));
                    int progress = (int) (millisUntilFinished/100);
                    progressBar.setProgress(progress);
                    progressBar2.setProgress(progress);
                }

                @Override
                public void onFinish() {
                    text.setText("Task completed");
                    progressBar.setProgress(0);
                    progressBar2.setProgress(0);
                }

            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feature6, menu);
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
}
