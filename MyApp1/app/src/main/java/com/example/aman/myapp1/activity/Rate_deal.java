package com.example.aman.myapp1.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.aman.myapp1.R;


public class Rate_deal extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_deal);
        ImageView image1 = (ImageView) findViewById(R.id.image);


       /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.index1);
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);

        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / m2, bitmap.getHeight() / m2, bitmap.getWidth() / m2, paint);

        image1.setImageBitmap(circleBitmap);*/



       //get bitmap of the image
       Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.index1);
        Bitmap resized = Bitmap.createScaledBitmap(bm, 90, 90, true);
        Bitmap conv_bm = getRoundedRectBitmap(resized, 90,90);
        image1.setImageBitmap(conv_bm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rate_deal, menu);
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


   /* private Bitmap decodeResource(Resources resources, int resId){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
          //  FileInputStream stream1=new FileInputStream(f);
           // BitmapFactory.decodeStream(stream1,null,o);
            //stream1.close();

            //Find the correct scale value. It should be the power of m2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=m1;
            while(true){
                if(width_tmp/m2<REQUIRED_SIZE || height_tmp/m2<REQUIRED_SIZE)
                    break;
                width_tmp/=m2;
                height_tmp/=m2;
                scale*=m2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
         //   FileInputStream stream2=new FileInputStream(f);
           // Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            //stream2.close();
          //  return bitmap;
      //  } catch (FileNotFoundException e) {
        //}
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/


    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap result = null, scaledBitmap = null;
      //  Canvas canvas;
        Paint paint;
        Rect rect;
        int color;
        RectF rectF;
        float roundPx;

        try {
           /* result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);
            canvas = new Canvas(result);

            color = 0xff424242;
            paint = new Paint();
            rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            rectF = new RectF(rect);
            roundPx = pixels;*/

            scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

            float ratioX = newWidth / (float) bitmap.getWidth();
            float ratioY = newHeight / (float) bitmap.getHeight();
            float middleX = newWidth / 2.0f;
            float middleY = newHeight / 2.0f;

            Matrix scaleMatrix = new Matrix();
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

            Canvas canvas = new Canvas(scaledBitmap);
            canvas.setMatrix(scaleMatrix);
            canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));


            /*paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);*/
        } catch (NullPointerException e) {
        // return bitmap;
        } catch (OutOfMemoryError o){}
        return scaledBitmap;
    }
}
