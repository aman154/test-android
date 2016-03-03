package com.example.aman.myapp1.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aman.myapp1.R;

public class CameraPhotoView extends ActionBarActivity {

    private String imageFileUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_photo_view);

        Intent intent = getIntent();
        if(intent != null){
            imageFileUrl = intent.getStringExtra("image_url");
        }

        ImageView imageView = (ImageView) findViewById(R.id.camera_preview_iv);
        if(imageFileUrl != null && imageFileUrl.length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFileUrl);
            bitmap = rotate(bitmap,90);
            imageView.setImageBitmap(bitmap);
        }else {

            Toast.makeText(CameraPhotoView.this, "photo not available", Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.retake_photo_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraPhotoView.this,CameraTest.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public static Bitmap rotate(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera_photo_view, menu);
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
