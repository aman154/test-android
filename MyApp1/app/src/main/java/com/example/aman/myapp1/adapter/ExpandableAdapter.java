package com.example.aman.myapp1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.model.HomeElement;

import java.util.HashMap;
import java.util.List;

/**
 * Created by aman on 3/7/15.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<HomeElement> headerList;
    private HashMap<HomeElement, List<String>> childList;

    public ExpandableAdapter(Context context, List<HomeElement> headerList, HashMap<HomeElement, List<String>> childList){

        this.context = context;
        this.headerList = headerList;
        this.childList = childList;
    }

    public int getGroupTypeCount() {
        return headerList.size();
    }


    @Override
    public int getGroupCount() {
        return headerList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(headerList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headerList.get(groupPosition).getTitle();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(headerList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final String headerText = headerList.get(groupPosition).getTitle();
        final int headerImage =  headerList.get(groupPosition).getImage();

        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exp_list_header, null);
        }

        TextView textHeader = (TextView) convertView.findViewById(R.id.header_text);
        ImageView imageHeader = (ImageView) convertView.findViewById(R.id.header_image);

        textHeader.setText(headerText);
        imageHeader.setImageResource(headerImage);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exp_child_list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.list_header_text);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
