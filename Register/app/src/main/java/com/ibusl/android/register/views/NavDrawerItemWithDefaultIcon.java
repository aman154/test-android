package com.ibusl.android.register.views;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ibusl.android.register.utilities.AndroidUtil;
import com.ibusl.android.register.utilities.LayoutHelper;

/**
 * Created by aman on 5/4/16.
 */
public class NavDrawerItemWithDefaultIcon extends FrameLayout {
    private TextView itemTextView;

    public NavDrawerItemWithDefaultIcon(Context context) {
        super(context);

        itemTextView = new TextView(context);
        itemTextView.setTextColor(0xff444444);
        itemTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);
        itemTextView.setLines(1);
        itemTextView.setMaxLines(1);
        itemTextView.setSingleLine(true);
        itemTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        itemTextView.setCompoundDrawablePadding(AndroidUtil.dp(34));
        addView(itemTextView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.LEFT | Gravity.TOP, 18, 0, 18, 0));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtil.dp(50), MeasureSpec.EXACTLY));
    }

    /**
     * @param textView
     * @param resId
     */
    public void setItemTextAndIcon(String textView, int resId) {
        if (resId != 0) {
            try {
                itemTextView.setText(textView);
                itemTextView.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
            } catch (Throwable e) {
            }
        } else {
            itemTextView.setText(textView);
        }
    }
}
