package com.example.aman.myapp1.gif_image;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aman.myapp1.R;


public class GifMainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "GifMainActivity";
    private GifImageView gifImageView,gifImageView1;
    private Button btnToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_activity_main);

        gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        gifImageView1 = (GifImageView) findViewById(R.id.gifImageView1);
        btnToggle = (Button) findViewById(R.id.btnToggle);

        btnToggle.setOnClickListener(this);

        new GifDataDownloader(){

            @Override
            protected void onPostExecute(byte[] bytes) {
                gifImageView.setBytes(bytes);
                gifImageView.startAnimation();
            }
        }.execute("http://gifs.joelglovier.com/badass/split.gif");

        new GifDataDownloader(){

            @Override
            protected void onPostExecute(byte[] bytes) {
                gifImageView1.setBytes(bytes);
                gifImageView1.startAnimation();
            }
        }.execute("http://gifs.joelglovier.com/big-lebowski/gun.gif");

    }


    @Override
    public void onClick(final View v) {
        if (v.equals(btnToggle)) {

            if (gifImageView.isAnimating() && gifImageView1.isAnimating()){
                gifImageView.stopAnimation(); gifImageView1.stopAnimation();}
             else{
                gifImageView.startAnimation();  gifImageView1.startAnimation();}
        }
    }

    private class GifDataDownloader extends AsyncTask<String,Void,byte []> {

        public GifDataDownloader(){

        }

        @Override
        protected byte[] doInBackground(String... params) {
            final String gifUrl = params[0];

            if (gifUrl == null)
                return null;

            byte[] gif = null;
            try {
                gif = ByteArrayHttpClient.get(gifUrl);
            } catch (OutOfMemoryError e) {
                Log.e(TAG, "GifDecode OOM: " + gifUrl, e);
            }

            return gif;
        }

    }
}
