package com.example.aman.myapp1.adapter;

import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.aman.myapp1.model.MoviesDetail;
import com.example.aman.myapp1.R;

import java.util.List;

/**
 * Created by aman on 1/7/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MoviesViewHolder> {

    private List<MoviesDetail> details;
    private OnItemClickListener mItemClickListener;

    public static class MoviesViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView m_name,m_detail;
        public ImageView m_image;
        //public RatingBar m_rating;
        public MoviesViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            m_image = (ImageView) view.findViewById(R.id.card_image_movie);
            m_name = (TextView) view.findViewById(R.id.card_name_movie);
            m_detail = (TextView) view.findViewById(R.id.card_detail_movie);
            //m_rating = (RatingBar) view.findViewById(R.id.card_rating_movie);

        }
    }

   /* public int getItemViewType(int position){

        return position%2*2;
    }*/


    public RecyclerAdapter(List<MoviesDetail> details) {
        this.details = details;
    }



    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_list, parent, false);
        MoviesViewHolder mvh = new MoviesViewHolder(v);
        return mvh;
    }



    @Override
    public void onBindViewHolder(MoviesViewHolder holder, final int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view, position);
                }
            }
        });

        //holder.m_image.setTransitionName(details.get(position).detail);

        holder.m_image.setImageResource(details.get(position).image);
        holder.m_name.setText(details.get(position).name);
        holder.m_detail.setText(details.get(position).detail);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return details.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}