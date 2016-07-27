package com.ibusl.android.register.views;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ibusl.android.register.utilities.LayoutHelper;
import com.ibusl.android.register.utilities.AndroidUtil;

/**
 * Created by aman on 11/4/16.
 */
public class ItemsItemView extends RelativeLayout {
    TextView itemNameText;
    TextView iconTextView;

    public ItemsItemView(Context context) {
        super(context);

        itemNameText = new TextView(context);
        itemNameText.setTextColor(0xff444444);
        itemNameText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        itemNameText.setTextAppearance(context, android.R.style.TextAppearance_Large);
        itemNameText.setLines(1);
        itemNameText.setMaxLines(1);
        itemNameText.setSingleLine(true);
        itemNameText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        itemNameText.setCompoundDrawablePadding(AndroidUtil.dp(34));
        addView(itemNameText, LayoutHelper.createRelative(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, 16, 0, 16, 0, RelativeLayout.ALIGN_PARENT_LEFT));

        iconTextView = new TextView(context);
        iconTextView.setTextColor(0xff444444);
        iconTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        itemNameText.setTextAppearance(context, android.R.style.TextAppearance_Large);
        iconTextView.setLines(1);
        iconTextView.setMaxLines(1);
        iconTextView.setSingleLine(true);
        iconTextView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        iconTextView.setCompoundDrawablePadding(AndroidUtil.dp(34));
        addView(iconTextView, LayoutHelper.createRelative(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, 16, 0, 16, 0,RelativeLayout.ALIGN_PARENT_RIGHT));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtil.dp(50), MeasureSpec.EXACTLY));
    }

    /**
     *
     * @param nameText
     * @param iconText
     * @param resId
     */
    public void setItemTextAndIcon(String nameText, String iconText, int resId) {
        if (resId != 0) {
            try {
                itemNameText.setText(nameText);
                itemNameText.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
            } catch (Throwable e) {
            }
        } else {
            itemNameText.setText(nameText);
            iconTextView.setText(iconText);
        }
    }

}
