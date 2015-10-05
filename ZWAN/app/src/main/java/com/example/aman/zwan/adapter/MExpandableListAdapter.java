package com.example.aman.zwan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aman.zwan.R;
import com.example.aman.zwan.models.DrawerElement;
import com.example.aman.zwan.util.AppConstants;

import java.util.ArrayList;

/**
 * Created by aman on 24/9/15.
 */
public class MExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    ArrayList<DrawerElement> element;
    LayoutInflater inflater;
    ExpandableListView drawerExpdListView;

    public MExpandableListAdapter(Context context, ArrayList<DrawerElement> element, ExpandableListView drawerExpdListView){

        this.context = context;
        this.element = element;
        this.drawerExpdListView = drawerExpdListView;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        if (element != null){
        return element.size();}else return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        if(element.get(i).getIsSubElement() != null && element.get(i).getIsSubElement().length() > 0){
        return element.get(i).getSubElements().size();}else return 0;
    }

    @Override
    public Object getGroup(int i) {
       return new Integer(i);

    }

    @Override
    public Object getChild(int i, int i1) {
          return new Integer(i1);

    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return (i*100)+i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.drawer_expdlist_item,null);

            holder.drawer_expdlist_item_rl = (RelativeLayout) view.findViewById(R.id.drawer_expdlist_item_rl);
            holder.drawer_expdlist_icon_tv = (TextView) view.findViewById(R.id.drawer_expdlist_icon_tv);
            holder.drawer_expdlist_tittle_tv = (TextView) view.findViewById(R.id.drawer_expdlist_tittle_tv);
            holder.drawer_expdlist_expend_tv = (TextView) view.findViewById(R.id.drawer_expdlist_expend_tv);
            holder.drawer_expdlist_colps_tv = (TextView) view.findViewById(R.id.drawer_expdlist_colps_tv);

            view.setTag(holder);
        }else {

            holder = (ViewHolder) view.getTag();
        }

        if (element != null && element.size() > 0 && element.get(i) != null ){

            String tittle = element.get(i).getTittle();
            String icon = element.get(i).getIcon();
            String isBigBackground = element.get(i).getIsBigBackground();
            String isSubElements = element.get(i).getIsSubElement();

            holder.drawer_expdlist_icon_tv.setText(icon);
            holder.drawer_expdlist_tittle_tv.setText(tittle);

            if (isBigBackground.equals(AppConstants.BACKGROUND_KEY)){
                holder.drawer_expdlist_item_rl.setBackground(context.getResources().getDrawable(R.drawable.drawer_items_breaker_drawable));
            }else if(isBigBackground.equals(AppConstants.NO_KEY)){
                holder.drawer_expdlist_item_rl.setBackground(context.getResources().getDrawable(R.drawable.drawer_items_background_drawable));
            }

            if(!drawerExpdListView.isGroupExpanded(i)) {
                if (isSubElements.equals(AppConstants.YES_KEY)){
                holder.drawer_expdlist_expend_tv.setVisibility(View.VISIBLE);
                holder.drawer_expdlist_colps_tv.setVisibility(View.GONE);}

            }else {  holder.drawer_expdlist_colps_tv.setVisibility(View.VISIBLE);
                holder.drawer_expdlist_expend_tv.setVisibility(View.GONE);}

            holder.drawer_expdlist_expend_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerExpdListView.expandGroup(i,true);
                }
            });

            holder.drawer_expdlist_colps_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerExpdListView.collapseGroup(i);
                }
            });
        }

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View childView, ViewGroup viewGroup) {
        ChildViewHolder childHolder;

        if (childView == null){
            childHolder = new ChildViewHolder();
            childView = inflater.inflate(R.layout.drawer_expdlist_child_item,null);
            childHolder.drawer_expdlist_childitem_rl = (RelativeLayout) childView.findViewById(R.id.drawer_expdlist_childitem_rl);
            childHolder.drawer_expdlist_child_tittle_tv = (TextView) childView.findViewById(R.id.drawer_expdlist_child_tittle_tv);
            childView.setTag(childHolder);

        }else {

            childHolder = (ChildViewHolder) childView.getTag();
        }

        if (element != null && element.get(i).getIsSubElement().equals(AppConstants.YES_KEY) ){

            String tittle = element.get(i).getSubElements().get(i1).getTittle();

            childHolder.drawer_expdlist_child_tittle_tv.setText(tittle);
            childHolder.drawer_expdlist_childitem_rl.setBackground(context.getResources().getDrawable(R.drawable.drawer_child_items_background_drawable));

        }

        return childView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    private class ViewHolder {

        RelativeLayout drawer_expdlist_item_rl;
        TextView drawer_expdlist_icon_tv;
        TextView drawer_expdlist_tittle_tv;
        TextView drawer_expdlist_expend_tv;
        TextView drawer_expdlist_colps_tv;

    }

    private class ChildViewHolder {
        RelativeLayout drawer_expdlist_childitem_rl;
        TextView drawer_expdlist_child_tittle_tv;
    }
}
