package com.ibusl.android.register.views;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ibusl.android.register.utilities.AndroidUtil;
import com.ibusl.android.register.utilities.LayoutHelper;

/**
 * Created by aman on 6/4/16.
 */
public class NavDrawerItemWithTextIcon extends FrameLayout {
    private TextView itemTextView;
    private TextView iconTextView;

    public NavDrawerItemWithTextIcon(Context context) {
        super(context);

        iconTextView = new TextView(context);
        iconTextView.setTextColor(0xff444444);
        iconTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        iconTextView.setLines(1);
        iconTextView.setMaxLines(1);
        iconTextView.setSingleLine(true);
        iconTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        iconTextView.setCompoundDrawablePadding(AndroidUtil.dp(34));
        addView(iconTextView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.LEFT | Gravity.TOP, 16, 0, 16, 0));

        itemTextView = new TextView(context);
        itemTextView.setTextColor(0xff444444);
        itemTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        itemTextView.setLines(1);
        itemTextView.setMaxLines(1);
        itemTextView.setSingleLine(true);
        itemTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        itemTextView.setCompoundDrawablePadding(AndroidUtil.dp(34));
        addView(itemTextView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.LEFT | Gravity.TOP, 16, 0, 16, 0));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtil.dp(48), MeasureSpec.EXACTLY));
    }

    public void setItemTextAndIcon(String textView, String iconText) {
        itemTextView.setText(textView);
        if (iconText != null && iconText.length() > 1) {
            iconTextView.setText(iconText);
        } else {
            iconTextView.setVisibility(INVISIBLE);
        }
    }
}
