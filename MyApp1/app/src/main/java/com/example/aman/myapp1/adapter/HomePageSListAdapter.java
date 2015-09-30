package com.example.aman.myapp1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aman.myapp1.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by aman on 1/7/15.
 */
public class HomePageSListAdapter extends RecyclerView.Adapter<HomePageSListAdapter.ListViewHolder> implements View.OnClickListener {

    Context context;
    private JSONObject hp_jsonObject;
    private JSONArray sc_jsonArray;
    private JSONObject sc_jsonObject;
    private OnItemClickListener mItemClickListener;
    private static final String HOME_PAGE__SC_MPIC_KEY = "mpic";
    RecyclerView recyclerView;

    String page_type;


    @Override
    public void onClick(View view) {

        int itemPosition = recyclerView.getChildPosition(view);
        Toast.makeText(context,"recyclerViewPosition"+itemPosition,Toast.LENGTH_SHORT).show();
    }


    public static class ListViewHolder extends RecyclerView.ViewHolder {

        public ImageView m_image;

        public ListViewHolder(View view) {
            super(view);
            m_image = (ImageView) view.findViewById(R.id.home_slist_image);
        }


    }

    public HomePageSListAdapter(Context context,JSONArray sc_jsonArray,RecyclerView recyclerView) {

        this.context = context;
        this.sc_jsonArray = sc_jsonArray;
        this.recyclerView = recyclerView;
    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_slist_item, parent, false);
        ListViewHolder mvh = new ListViewHolder(v);
        v.setOnClickListener(this);
        return mvh;
    }



    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {

        String url = null;
        try{
            sc_jsonObject = sc_jsonArray.getJSONObject(position);
            url = sc_jsonObject.getString(HOME_PAGE__SC_MPIC_KEY);

        }catch (Exception e){e.printStackTrace();}

        if(url != null && url.length() > 0) {
            Picasso.with(context)
                    .load(url)
                    .into(holder.m_image);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return sc_jsonArray.length();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}