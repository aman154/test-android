package com.example.aman.myapp1.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.custom_view.ExpandablePanel;

import java.lang.reflect.Method;

public class ExpandableViewActivity extends AppCompatActivity implements ExpandablePanel.OnExpandListener {
    private static final  String TAG = "ExpandableView";

    private ExpandablePanel expandablePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_view);

        expandablePanel = (ExpandablePanel) findViewById(R.id.expandable_panel);

        if (expandablePanel != null) {
            expandablePanel.setOnExpandListener(this);

            expandablePanel.setAnimationDuration(500);
        }
    }

    @Override
    public void onExpand(View handle, View content) {
        Log.i(TAG, "onExpand ");
    }

    @Override
    public void onCollapse(View handle, View content) {
        Log.i(TAG, "onCollapse ");
    }


    public static Animation expand(final View v, final boolean expand) {
        try {
            Method m = v.getClass().getDeclaredMethod("onMeasure", int.class, int.class);
            m.setAccessible(true);
            m.invoke(
                    v,
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(((View)v.getParent()).getMeasuredWidth(), View.MeasureSpec.AT_MOST)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        final int initialHeight = v.getMeasuredHeight();

        if (expand) {
            v.getLayoutParams().height = 0;
        }
        else {
            v.getLayoutParams().height = initialHeight;
        }
        v.setVisibility(View.VISIBLE);

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int newHeight = 0;
                if (expand) {
                    newHeight = (int) (initialHeight * interpolatedTime);
                } else {
                    newHeight = (int) (initialHeight * (1 - interpolatedTime));
                }
                v.getLayoutParams().height = newHeight;
                v.requestLayout();

                if (interpolatedTime == 1 && !expand)
                    v.setVisibility(View.GONE);
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration(200);
        return a;
    }
}
