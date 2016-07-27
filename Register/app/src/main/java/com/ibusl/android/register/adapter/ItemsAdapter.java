package com.ibusl.android.register.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ibusl.android.register.views.ItemsItemView;
import com.ibusl.android.register.R;

/**
 * Created by aman on 6/4/16.
 */
public class ItemsAdapter extends BaseAdapter {
    private Context context;

    public ItemsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
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
            if (convertView == null) {
                convertView = new ItemsItemView(context);
            }
            if (position == 0) {
                ((ItemsItemView) convertView).setItemTextAndIcon("All Items",">", R.drawable.ic_action_next_item);
            } else if (position == 1) {
                ((ItemsItemView) convertView).setItemTextAndIcon("Categories",">", R.drawable.ic_action_next_item);
            } else if (position == 2) {
                ((ItemsItemView) convertView).setItemTextAndIcon("Discounts", ">",R.drawable.ic_action_next_item);
            } else if (position == 3) {
                ((ItemsItemView) convertView).setItemTextAndIcon("Modules",">", R.drawable.ic_action_next_item);
            }

        return convertView;
    }

}
