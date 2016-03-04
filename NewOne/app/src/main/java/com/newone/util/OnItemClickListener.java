package com.newone.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by aman on 2/11/15.
 */
public interface OnItemClickListener {

    void onItemClick(int viewId,View view, View view1,TextView textView, int position, RecyclerView.ViewHolder viewHolder);
}
