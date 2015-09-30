package com.example.aman.myapp1.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.aman.myapp1.R;


public class CircularImageView extends ImageView
{

    private int borderWidth = 1;
    private int viewWidth;
    private int viewHeight;
    private Bitmap image;
    private Paint paint;
    private Paint paintBorder;
    private BitmapShader shader;

    public CircularImageView(Context context)
    {

        super(context);
        setup();
    }

    public CircularImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setup();
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        setup();
    }

    private void setup()
    {
        // init paint
        paint = new Paint();
        paint.setAntiAlias(true);

        paintBorder = new Paint();
        int color = getContext().getResources().getColor(R.color.white);
        setBorderColor(color);
        paintBorder.setAntiAlias(true);
        if (Build.VERSION.SDK_INT >= 14){
            this.setLayerType(LAYER_TYPE_SOFTWARE, paintBorder);
        }

        paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, color);
    }

    public void setBorderWidth(int borderWidth)
    {
        this.borderWidth = borderWidth;
        this.invalidate();
    }

    public void setBorderColor(int borderColor)
    {
        if (paintBorder != null)
            paintBorder.setColor(borderColor);

        this.invalidate();
    }

    private void loadBitmap()
    {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();

        if (bitmapDrawable != null)
            image = bitmapDrawable.getBitmap();
    }


    private Bitmap createSquaredBitmap(Bitmap srcBmp,int dim) {

        Bitmap dstBmp = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888);


        Rect sourceRect = new Rect(0, 0, srcBmp.getWidth(), srcBmp.getHeight());
        Rect destinationRect = new Rect(0,(dstBmp.getHeight() - srcBmp.getHeight())/2,dstBmp.getWidth(),(dstBmp.getHeight() + srcBmp.getHeight())/2);

        Canvas canvas = new Canvas(dstBmp);
        canvas.drawColor(Color.WHITE);

        canvas.drawBitmap(srcBmp, sourceRect, destinationRect, null);

        return dstBmp;
    }
    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas)
    {

        // load the bitmap
        loadBitmap();

        // init shader
        if (image != null)
        {
            int radius = viewWidth;
            Bitmap bmp = image;

            float sw = 0,sh =0;
            if(bmp.getWidth()>bmp.getHeight()){
                //lanscape
                sw = (float)radius;
                sh = ((float)bmp.getHeight()/(float)bmp.getWidth())*(float)radius;
            }else{
                //portrait
                sh = (float)radius;
                sw = ((float)bmp.getWidth()/(float)bmp.getHeight())*(float)radius;
            }
            if(sh <=0) sh =(float)radius;
            if(sw <=0) sw =(float)radius;


            Bitmap output =   Bitmap.createScaledBitmap(bmp, (int) sw, (int) sh, false);

            output = createSquaredBitmap(output, viewWidth);

            shader = new BitmapShader(output, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            int circleCenter = viewWidth / 2;


            canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter + borderWidth - 4.0f, paintBorder);


            canvas.drawCircle(circleCenter + borderWidth , circleCenter + borderWidth , circleCenter - 4.0f, paint);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec, widthMeasureSpec);

        viewWidth = width - (borderWidth * 2);
        viewHeight = height - (borderWidth * 2);

        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec)
    {

        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY)
        {
            // We were told how big to be
            result = specSize;
        }
        else
        {
            // Measure the text
            result = viewWidth;
        }

        return result;
    }

    private int measureHeight(int measureSpecHeight, int measureSpecWidth)
    {

        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY)
        {
            // We were told how big to be
            result = specSize;
        }
        else
        {
            // Measure the text (beware: ascent is a negative number)
            result = viewHeight;
        }

        return (result + 2);
    }
}