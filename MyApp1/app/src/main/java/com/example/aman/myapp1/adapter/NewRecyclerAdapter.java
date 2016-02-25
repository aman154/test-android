package com.example.aman.myapp1.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aman.myapp1.R;

import java.util.ArrayList;

/**
 * Created by aman on 14/10/15.
 */
public class NewRecyclerAdapter extends RecyclerView.Adapter<NewRecyclerAdapter.CustomViewHolder>  {


    ArrayList<String> list;


    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView m_detail;
        public CustomViewHolder(View view) {
            super(view);
            m_detail = (TextView) view.findViewById(R.id.list_tv);

        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_view_list, parent, false);
        CustomViewHolder mvh = new CustomViewHolder(v);

        return mvh;

    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        holder.m_detail.setText(list.get(position));

    }


    public NewRecyclerAdapter(ArrayList<String> list){

        this.list = list;
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
