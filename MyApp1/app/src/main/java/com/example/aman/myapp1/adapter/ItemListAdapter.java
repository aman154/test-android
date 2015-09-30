package com.example.aman.myapp1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.aman.myapp1.R;
import com.example.aman.myapp1.model.ItemDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by aman on 18/9/15.
 */
public class ItemListAdapter extends BaseAdapter {


    private static final int SIMPLELIST = 0;
    private static final int TABLIST = 1;
    private static final int GRIDLIST = 2;
    Context context;
    ArrayList<ItemDetails> detail;
    LayoutInflater inflater;
    int viewType;
    ItemDetails details = null;

    public ItemListAdapter(Context context,ArrayList<ItemDetails> detail,int viewType){

        this.context  = context;
        this.detail = detail;
        this.viewType = viewType;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return detail.size();
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

        ViewHolder holder = null;
        details = detail.get(position);


        if(convertView == null){

            holder = new ViewHolder();
            convertView = getCustomView(inflater,parent,viewType);
            convertView.setTag(holder);

            holder.iPic = (ImageView) convertView.findViewById(R.id.list_image);
            holder.iDetail = (TextView) convertView.findViewById(R.id.idetail_tv);
            holder.dlPrice = (TextView) convertView.findViewById(R.id.dl_tv);
            holder.mrpPrice = (TextView) convertView.findViewById(R.id.mrp_tv);
            holder.percentOff = (TextView) convertView.findViewById(R.id.percent_off_tv);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.item_ratingBar);
        }else{

            holder = (ViewHolder) convertView.getTag();
        }

        if(details != null){
        holder.iDetail.setText(details.getiDetail());

        holder.dlPrice.setText("Rs. "+details.getDlPrice());
        holder.mrpPrice.setText("Rs. "+details.getMrPrice());

        int percent_num = (details.getDlPrice()*100)/details.getMrPrice();
        int percent = 100-percent_num;

        Log.i("ItemListAdapter", "percent" +percent);
        if (parent!=null)
        holder.percentOff.setText(percent+"% Off");

        double rating = details.getRating();
            if (rating != 0)
        holder.ratingBar.setRating((float)rating);

        String url = details.getiPic();
            if (url != null && url.length() > 0) {
                Picasso.with(context)
                        .load(url)
                        .placeholder(R.drawable.title_about_default)
                        .error(R.drawable.title_about_alt)
                        .into(holder.iPic);

            }

        }

        return convertView;
    }


    class ViewHolder{

        ImageView iPic;
        TextView iDetail,mrpPrice,dlPrice,percentOff;
        RatingBar ratingBar;

    }

    public View getCustomView(LayoutInflater inflater,ViewGroup parent,int viewType){

        switch (viewType){

            case SIMPLELIST: return inflater.inflate(R.layout.item_list_view,parent,false);
            case TABLIST: return inflater.inflate(R.layout.item_list_tab_view,parent,false);
            case GRIDLIST: return inflater.inflate(R.layout.item_list_grid_view,parent,false);
        }
        return null;
    }
}
