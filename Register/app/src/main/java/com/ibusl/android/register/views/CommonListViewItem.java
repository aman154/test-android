package com.ibusl.android.register.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ibusl.android.register.R;
import com.ibusl.android.register.utilities.AndroidUtil;
import com.ibusl.android.register.utilities.LayoutHelper;

/**
 * Created by aman on 20/4/16.
 */
public class CommonListViewItem extends RelativeLayout {
    private static final String LOG_TAG = "CommonListViewItem";
    private ImageView itemImageView;
    private TextView titleTextView;
    private TextView headingTextView;
    private TextView contentTextView;
    private Paint paint;

    public CommonListViewItem(Context context) {
        super(context);

        if (paint == null) {
            paint = new Paint();
            paint.setColor(0xff000000);
            paint.setStrokeWidth(1);
        }

        setPadding(1,1,1,1);

        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(0xff757575);

        itemImageView = new ImageView(context);
        itemImageView.setMaxHeight(AndroidUtil.dp(50));
        itemImageView.setMaxWidth(AndroidUtil.dp(50));
        itemImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        itemImageView.setAdjustViewBounds(true);
        itemImageView.setPadding(10,10,10,10);
        frameLayout.addView(itemImageView);

        titleTextView = new TextView(context);
        titleTextView.setTextColor(0xff444444);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        titleTextView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        titleTextView.setLines(1);
        titleTextView.setMaxLines(1);
        titleTextView.setSingleLine(true);
        titleTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        frameLayout.addView(titleTextView);

        addView(frameLayout, LayoutHelper.createRelative(70, 50));

        headingTextView = new TextView(context);
        headingTextView.setTextColor(0xff444444);
        headingTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        headingTextView.setTextAppearance(context, R.style.MediumBold);
        headingTextView.setLines(1);
        headingTextView.setMaxLines(1);
        headingTextView.setSingleLine(true);
        headingTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        addView(headingTextView, LayoutHelper.createRelative(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, 80, 0, 16, 0,RelativeLayout.ALIGN_PARENT_LEFT));

        contentTextView = new TextView(context);
        contentTextView.setTextColor(0xff444444);
        contentTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        contentTextView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        contentTextView.setLines(1);
        contentTextView.setMaxLines(1);
        contentTextView.setSingleLine(true);
        contentTextView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        addView(contentTextView, LayoutHelper.createRelative(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, 16, 0, 16, 0,RelativeLayout.ALIGN_PARENT_RIGHT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtil.dp(50), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setCommonData(int resId, String title, String heading, String content){
        itemImageView.setImageResource(resId);
        titleTextView.setText(title);
        headingTextView.setText(heading);
        contentTextView.setText(content);
    }
}
