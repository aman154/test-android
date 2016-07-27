package com.ibusl.android.register.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibusl.android.register.R;
import com.ibusl.android.register.utilities.AndroidUtil;
import com.ibusl.android.register.utilities.LayoutHelper;
import com.ibusl.android.register.views.ApplicationLoader;

/**
 * Created by aman on 5/4/16.
 */
public class NavDrawerHeaderView extends FrameLayout {
    private ImageView backgroundImageView;
    private ImageView avatarImageView;
    private TextView nameTextView;
    private TextView phoneNumberTextView;
    private Paint paint = new Paint();
    private Rect srcRect = new Rect();
    private Rect destRect = new Rect();

    public NavDrawerHeaderView(Context context) {
        super(context);
        setBackgroundColor(0xff34a2f7);

        backgroundImageView = new ImageView(context);
        backgroundImageView.setVisibility(INVISIBLE);
        backgroundImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        backgroundImageView.setImageResource(R.drawable.bottom_shadow);
        addView(backgroundImageView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 70, Gravity.LEFT | Gravity.BOTTOM));

        avatarImageView = new ImageView(context);
        addView(avatarImageView, LayoutHelper.createFrame(60, 60, Gravity.LEFT | Gravity.BOTTOM, 16, 0, 0, 60));

        nameTextView = new TextView(context);
        nameTextView.setTextColor(0xffffffff);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        nameTextView.setLines(1);
        nameTextView.setMaxLines(1);
        nameTextView.setSingleLine(true);
        nameTextView.setGravity(Gravity.LEFT);
        nameTextView.setEllipsize(TextUtils.TruncateAt.END);
        addView(nameTextView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.BOTTOM, 16, 10, 0, 30));

        phoneNumberTextView = new TextView(context);
        phoneNumberTextView.setTextColor(0xffffffff);
        phoneNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        phoneNumberTextView.setLines(1);
        phoneNumberTextView.setMaxLines(1);
        phoneNumberTextView.setSingleLine(true);
        phoneNumberTextView.setGravity(Gravity.LEFT);
        addView(phoneNumberTextView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.BOTTOM, 16, 0, 0, 13));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtil.dp(148) + AndroidUtil.statusBarHeight, MeasureSpec.EXACTLY));
        } else {
            try {
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtil.dp(148), MeasureSpec.EXACTLY));
            } catch (Exception e) {
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //will take some background drawable for  drawer header view
        Drawable backgroundDrawable = null;
        if (backgroundDrawable != null) {
            phoneNumberTextView.setTextColor(0xffffffff);
            backgroundImageView.setVisibility(VISIBLE);
            if (backgroundDrawable instanceof ColorDrawable) {
                backgroundDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                backgroundDrawable.draw(canvas);
            } else if (backgroundDrawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) backgroundDrawable).getBitmap();
                float scaleX = (float) getMeasuredWidth() / (float) bitmap.getWidth();
                float scaleY = (float) getMeasuredHeight() / (float) bitmap.getHeight();
                float scale = scaleX < scaleY ? scaleY : scaleX;
                int width = (int) (getMeasuredWidth() / scale);
                int height = (int) (getMeasuredHeight() / scale);
                int x = (bitmap.getWidth() - width) / 2;
                int y = (bitmap.getHeight() - height) / 2;
                srcRect.set(x, y, x + width, y + height);
                destRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
                canvas.drawBitmap(bitmap, srcRect, destRect, paint);
            }
        } else {
            backgroundImageView.setVisibility(INVISIBLE);
            phoneNumberTextView.setTextColor(0xffffffff);
            super.onDraw(canvas);
        }
    }

    /**
     * This method use to set data on NavDrawerHeaderView using some model or
     * anything else will take as parameter of this method.
     */
    public void setNavDrawerHeaderView() {
        nameTextView.setText("Your Name");
        phoneNumberTextView.setText("Your Number");
        avatarImageView.setImageDrawable(ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.default_avatar));
    }
}
