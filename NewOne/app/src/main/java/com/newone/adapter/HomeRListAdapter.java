package com.newone.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newone.R;
import com.newone.model.MainResultsRoot;
import com.newone.util.AppConstants;
import com.newone.util.OnItemClickListener;
import com.squareup.picasso.Picasso;

/**
 * Created by aman on 14/10/15.
 */
public class HomeRListAdapter extends RecyclerView.Adapter<HomeRListAdapter.CustomViewHolder> {

    MainResultsRoot mainResultsRoot;
    Context context;
    OnItemClickListener onItemClickListener;


    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView m_detail;
        public ImageView m_image;

        public CustomViewHolder(View view) {
            super(view);
            m_detail = (TextView) view.findViewById(R.id.hp_item_list_tv);
            m_image = (ImageView) view.findViewById(R.id.hp_item_list_iv);

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_item_view, parent, false);
        CustomViewHolder mvh = new CustomViewHolder(v);

        return mvh;

    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        holder.m_detail.setText(mainResultsRoot.getHomeResults().get(position).getTt());

        String url = mainResultsRoot.getHomeResults().get(position).getMpic();

        Log.i("HomeRListAdapter", "url-" + url);

        if (url != null && url.length() > 0) {
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.home_list_bg)
                    .error(R.drawable.home_list_bg)
                    .into(holder.m_image);
        }

        holder.m_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(AppConstants.HOME_LIST, holder.m_detail, null, null, position, holder);
            }
        });
    }

    public HomeRListAdapter(Context context, MainResultsRoot mainResultsRoot, OnItemClickListener onItemClickListener) {

        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.mainResultsRoot = mainResultsRoot;
    }


    @Override
    public int getItemCount() {
        return mainResultsRoot.getHomeResults().size();
    }

}
