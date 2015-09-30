package com.example.aman.myapp1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aman.myapp1.model.Detail;
import com.example.aman.myapp1.R;

import java.util.ArrayList;

/**
 * Created by aman on m11/m6/m15.
 */
public class ListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Detail> details;
    Detail detail;
   // Detail detail[];
    int layoutResourceId;


    public ListAdapter(Context context,  ArrayList<Detail> details){

        this.context = context;
        this.details = details;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){return details.size();}

    @Override
    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position){return details.size(); }

    public Object getItem(Object item){return null;}


    public View getView(int position, View convertView, ViewGroup viewGroup){

        detail = details.get(position);

        if(convertView==null){

            convertView = layoutInflater.inflate(R.layout.row_coupon_list,null); }

           /* ImageView imageView = (ImageView) convertView.findViewById(R.id.listimage);
            TextView textView1 = (TextView) convertView.findViewById(R.id.listt1);
           // TextView textView2 = (TextView) v.findViewById(R.id.listt2);

          //  Detail detail1 = detail[position];

            imageView.setImageResource(detail1.icons);
            textView1.setText(detail1.name);
           // textView2.setText(detail1.location);*/

        CheckBox promo_code_selected_cb = (CheckBox) convertView.findViewById(R.id.promo_code_selected_cb);
        TextView promo_code_value_tv = (TextView) convertView.findViewById(R.id.promo_code_value_tv);
        TextView promo_code_type_tv = (TextView) convertView.findViewById(R.id.promo_code_type_tv);
        TextView promo_code_description_tv = (TextView) convertView.findViewById(R.id.promo_code_description_tv);
        TextView promo_code_validity_tv = (TextView) convertView.findViewById(R.id.promo_code_validity_tv);


        promo_code_value_tv.setText(detail.getRate());
        promo_code_type_tv.setText(detail.getRateType());
        promo_code_description_tv.setText(detail.getRateDetail());
        promo_code_validity_tv.setText(detail.getValidity());


      //  promo_code_selected_cb.OnCheckedChangeListener(new )


        return convertView;
    }

}
