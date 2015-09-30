package com.example.aman.myapp1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aman.myapp1.model.HomeElement;
import com.example.aman.myapp1.R;

import java.util.List;

/**
 * Created by aman on m1/m7/m15.
 */
public class HomeListAdapter extends ArrayAdapter<HomeElement> {

    LayoutInflater inflater;
    List<HomeElement> elements;
    Context context;
    int layoutResourceId;

    public HomeListAdapter(Context context, int layoutResourceId, List<HomeElement> elements){
       super(context,layoutResourceId,elements);

        this.elements = elements;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
        return elements.size();
    }

    public View getView(int position, View view, ViewGroup viewGroup){

        View rootView = view;

        if(view == null){

            rootView = inflater.inflate(R.layout.homelistview,viewGroup,false);}

            ImageView imageView = (ImageView) rootView.findViewById(R.id.homelist_image);
            TextView textView = (TextView) rootView.findViewById(R.id.homelist_text);

            imageView.setImageResource(elements.get(position).getImage());
            textView.setText(elements.get(position).getTitle());

        return rootView;
    }


}
