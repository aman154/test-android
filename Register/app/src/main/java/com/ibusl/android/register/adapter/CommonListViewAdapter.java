package com.ibusl.android.register.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ibusl.android.register.R;
import com.ibusl.android.register.model.Discount;
import com.ibusl.android.register.utilities.AndroidUtil;
import com.ibusl.android.register.views.CommonListViewItem;

import java.util.List;

/**
 * Created by aman on 20/4/16.
 */
public class CommonListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Object> items;

    public CommonListViewAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    public void notifyDataSetChanged(List<Object> items) {
        this.items = items;
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (items != null && items.size() > 0) {
            return items.size();
        } else {
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Discount discount;
        String content;

        if (convertView == null) {
            convertView = new CommonListViewItem(context);
        }
        discount = (Discount) items.get(position);
        AndroidUtil.setDrawableColor(context, "#FFFFFF", R.drawable.ic_local_offer_black_24dp);
        if (discount != null) {
            if (discount.getDiscountQuantity() != null && discount.getDiscountQuantity().length() > 0) {
                content = discount.getDiscountQuantity();
            } else {
                content = "Variable";
            }

            if(discount.getDiscountType().equals("0")){
                content = content+"%";
            }else {
                content = "$"+content;
            }
            ((CommonListViewItem) convertView).setCommonData(R.drawable.ic_local_offer_black_24dp, "", discount.getDiscountName(), content);
        }

        return convertView;
    }
}
