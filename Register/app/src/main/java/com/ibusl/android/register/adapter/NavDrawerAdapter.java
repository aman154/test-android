package com.ibusl.android.register.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ibusl.android.register.views.NavDrawerDivider;
import com.ibusl.android.register.views.NavDrawerHeaderView;
import com.ibusl.android.register.views.NavDrawerItemWithDefaultIcon;

/**
 * Created by aman on 6/4/16.
 */
public class NavDrawerAdapter extends BaseAdapter {
    private Context context;

    public NavDrawerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 8;
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
        int viewType = getItemViewType(position);

        if (viewType == 0 && position == 0) {
            if (convertView == null) {
                convertView = new NavDrawerHeaderView(context);
            }
            ((NavDrawerHeaderView) convertView).setNavDrawerHeaderView();
        } else if (viewType == 1) {
            if (convertView == null) {
                convertView = new NavDrawerDivider(context);
            }
        } else if (viewType == 2) {
            if (convertView == null) {
                convertView = new NavDrawerItemWithDefaultIcon(context);
            }
            if (position == 1) {
                ((NavDrawerItemWithDefaultIcon) convertView).setItemTextAndIcon("Register", 0);
            } else if (position == 2) {
                ((NavDrawerItemWithDefaultIcon) convertView).setItemTextAndIcon("Transactions", 0);
            } else if (position == 3) {
                ((NavDrawerItemWithDefaultIcon) convertView).setItemTextAndIcon("Reports", 0);
            } else if (position == 4) {
                ((NavDrawerItemWithDefaultIcon) convertView).setItemTextAndIcon("Items", 0);
            } else if (position == 6) {
                ((NavDrawerItemWithDefaultIcon) convertView).setItemTextAndIcon("Settings", 0);
            } else if (position == 7) {
                ((NavDrawerItemWithDefaultIcon) convertView).setItemTextAndIcon("About", 0);
            }

        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 5) {
            return 1;
        } else {
            return 2;
        }
    }

}
