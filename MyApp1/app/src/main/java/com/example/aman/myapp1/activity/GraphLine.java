package com.example.aman.myapp1.activity;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.util.AnimatedPathView;

public class GraphLine extends ActionBarActivity {

    private String TAG = "GraphLine";
    private float[] scores;
    private int numberOfScores;
    private  float maxScore;
    private Paint paint;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_line);

        final AnimatedPathView view = (AnimatedPathView) findViewById(R.id.animated_path);

        ViewTreeObserver observer = view.getViewTreeObserver();
        if (observer != null) {
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    float[][] points = new float[][]{
                            {0, 0},
                            {view.getWidth(), 0},
                            {view.getWidth(), view.getHeight()},
                            {0, view.getHeight()},
                            {0, 0},
                            {view.getWidth(), view.getHeight()},
                            {view.getWidth(), 0},
                            {0, view.getHeight()}
                    };
                    view.setPath(points);
                }
            });

        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator anim = ObjectAnimator.ofFloat(view, "percentage", 0.0f, 1.0f);
                anim.setDuration(2000);
                anim.setInterpolator(new LinearInterpolator());
                anim.start();
            }
        });
    }



   /* public void setData(float[] scorePoints, float max, int totalScores){

        Log.d(TAG, "Get stuff from activity");

        scores = scorePoints;

        numberOfScores = totalScores;
        Log.d(TAG, "Number of scores = " + numberOfScores);

        maxScore = max;
        Log.d(TAG, "Max score = " + maxScore);

        segmentToDraw = (float) 10;

        //get the width of the area to draw
        width = Math.abs(getWidth() - getPaddingLeft() - getPaddingRight());

        //get the height of the area to draw
        height = getHeight() - getPaddingTop() - getPaddingBottom();
        Log.d(TAG, "Drawable area in view = " + width + " x " + height);


        points.add(getXPos(scores[0]));
        points.add(getYPos(scores[0]));

        points.add((float) 0);
        points.add((float) 0);

        x = points.get(0);
        y = points.get(1);

        startPoint = scores[0];
        endPoint = scores[1];

        for(int i=0; i<scores.length-1; i++){
            String thePoints = "";

            if(i>0){

                startPoint = scores[i];
                endPoint = scores[i+1];

                x = points.get(i*4);
                y = points.get((i*4) + 1);

            }

            startPointX = getXPos(startPoint);
            startPointY = getYPos(startPoint);

            endPointX = getXPos(endPoint);
            endPointY = getYPos(endPoint);

            distanceOfLine = (float) Math.sqrt(Math.pow((endPointX - startPointX), 2) + Math.pow((endPointY - startPointY), 2));
            Log.d(TAG, "Distance of line = " + distanceOfLine);

            //get number of segments in line
            numberOfSegmentsInLine = (int) (distanceOfLine/segmentToDraw);
            Log.d(TAG, "Number of segments in line = " + numberOfSegmentsInLine);

            //get distance to move in Y direction
            yDirection = (float) ((endPointY - startPointY)/ (float) numberOfSegmentsInLine);
            Log.d(TAG, "Move " + yDirection + " in Y direction");

            //get distance to move in X direction
            xDirection = (float) ((endPointX - startPointX)/ (float) numberOfSegmentsInLine);
            Log.d(TAG, "Move " + xDirection + " in X direction");


            //loop for each segment
            for(int j=0; j<numberOfSegmentsInLine; j++){
                x += xDirection;
                y += yDirection;

                points.set(points.size()-2, Float.valueOf(x));
                points.set(points.size()-1, Float.valueOf(y));

                Log.d(TAG, "Line : " + (i+1) + " Segment : " + j);
                Log.d(TAG, "X = "+ (x+xDirection) + " Y = " + (y+yDirection));

                Log.d(TAG, "Call invalidate now!");
                //invalidate();
                //postInvalidateDelayed(delayMilliseconds);
            }

            //draw final line
            points.set(points.size()-2, endPointX);
            points.set(points.size()-1, endPointY);

            invalidate();
            //postInvalidateDelayed(delayMilliseconds);

            if(i<scores.length-2){

                points.add(endPointX);
                points.add(endPointY);

                points.add((float) 0);
                points.add((float) 0);
            }

            for(int k =0; k<points.size(); k++){
                thePoints = thePoints + " : " + points.get(k);
            }
            Log.d(TAG, "Points array = " + thePoints);
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        //setWillNotDraw(true);

        Log.d(TAG, "DRAW DAMNIT!!!");
        Log.d(TAG, "Width = " + (int) width + " Height = " + (int)height);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(Color.RED);
        //paint.setAntiAlias(true);
        //paint.setShadowLayer(4, 2, 2, 0x81000000);

        Bitmap bitmap = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);

        String drawPointsGo = "";
        float[] drawPoints = new float[points.size()];
        for(int i=0; i<points.size(); i++){
            Float f = points.get(i);
            drawPoints[i] = (float) (f != null ? f : 0.0);
            drawPointsGo = drawPointsGo + " : " + drawPoints[i];
        }
        Log.d(TAG, "Draw Points: " + drawPointsGo);

        canvas.drawLines(drawPoints, paint);
    }*/
}
