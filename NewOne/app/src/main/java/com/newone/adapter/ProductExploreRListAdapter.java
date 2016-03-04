package com.newone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newone.R;

import java.util.List;

/**
 * Created by aman on 1/12/15.
 */
public class ProductExploreRListAdapter extends BaseAdapter {

    private Context context;
    private List<String> proTypes;
    private LayoutInflater inflater;

    public ProductExploreRListAdapter(Context context, List<String> proTypes){
        this.context = context;
        this.proTypes = proTypes;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return proTypes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if(view == null){

            holder = new ViewHolder();
            view = inflater.inflate(R.layout.product_explore_list_item_view,viewGroup,false);
            view.setTag(holder);

            holder.tittle = (TextView) view.findViewById(R.id.explore_list_tv);
        }else{

            holder = (ViewHolder) view.getTag();
        }

        holder.tittle.setText(proTypes.get(i));

        return view;
    }

    class ViewHolder {
        TextView tittle;
    }
}
