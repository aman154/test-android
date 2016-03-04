package com.newone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newone.R;
import com.newone.model.MainResultsRoot;
import com.newone.model.ProductResults;
import com.newone.util.AppConstants;
import com.newone.util.OnItemClickListener;
import com.newone.util.UserProductUtil;

/**
 * Created by aman on 29/10/15.
 */
public class ProductRListAdapter extends RecyclerView.Adapter<ProductRListAdapter.CustomViewHolder> {

    private static final String LOG_TAG = "PRODUCT_LIST";

    Context context;
    MainResultsRoot mainResultsRoot;
    OnItemClickListener onItemClickListener;
    RecyclerView recyclerView;


    public static class CustomViewHolder extends RecyclerView.ViewHolder  {

        public View convertView;
        public int view_position;
        public TextView m_detail,product_detail_tv,product_mrp_tv;
        public TextView hld_qnt_tv;
        public LinearLayout m_add_ll;
        public LinearLayout m_incr_ll;
        public LinearLayout m_incr;
        public LinearLayout m_decr;

        public CustomViewHolder(Context context,View view, final OnItemClickListener onItemClickListener) {
            super(view);
            this.convertView = view;
            product_detail_tv = (TextView) view.findViewById(R.id.product_detail_tv);
            product_mrp_tv = (TextView) view.findViewById(R.id.product_mrp_tv);
            m_detail = (TextView) view.findViewById(R.id.hp_detail_item_list_tv);
            setHld_qnt_tv((TextView) view.findViewById(R.id.hld_qnt_tv));
            setM_incr((LinearLayout) view.findViewById(R.id.incr_text));
            setM_decr((LinearLayout) view.findViewById(R.id.decr_text));
            setM_add_ll((LinearLayout) view.findViewById(R.id.detail_item_add_ll));
            setM_incr_ll((LinearLayout) view.findViewById(R.id.detail_item_increase_ll));

            setListeners(context,onItemClickListener);
        }

        private void setListeners(final Context context,final OnItemClickListener onItemClickListener) {
            getM_add_ll().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(AppConstants.PRO_ADD, getM_add_ll(), getM_incr_ll(), getHld_qnt_tv(),getView_position(), CustomViewHolder.this);
                }
            });

            getM_incr().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   onItemClickListener.onItemClick(AppConstants.PRO_INCR,convertView,null,getHld_qnt_tv(),getView_position(),CustomViewHolder.this);
                }
            });
            getM_decr().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClickListener.onItemClick(AppConstants.PRO_DECR,getM_add_ll(),getM_incr_ll(),getHld_qnt_tv(),getView_position(),CustomViewHolder.this);
                }
            });
        }

        public LinearLayout getM_add_ll() {
            return m_add_ll;
        }

        public void setM_add_ll(LinearLayout m_add_ll) {
            this.m_add_ll = m_add_ll;
        }

        public LinearLayout getM_incr_ll() {
            return m_incr_ll;
        }

        public void setM_incr_ll(LinearLayout m_incr_ll) {
            this.m_incr_ll = m_incr_ll;
        }

        public LinearLayout getM_incr() {
            return m_incr;
        }

        public void setM_incr(LinearLayout m_incr) {
            this.m_incr = m_incr;
        }

        public LinearLayout getM_decr() {
            return m_decr;
        }

        public void setM_decr(LinearLayout m_decr) {
            this.m_decr = m_decr;
        }

        public int getView_position() {
            return view_position;
        }

        public void setView_position(int view_position) {
            this.view_position = view_position;
        }

        public TextView getHld_qnt_tv() {
            return hld_qnt_tv;
        }

        public void setHld_qnt_tv(TextView hld_qnt_tv) {
            this.hld_qnt_tv = hld_qnt_tv;
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item_view, parent, false);
        CustomViewHolder mvh = new CustomViewHolder(context,v,onItemClickListener);

        return mvh;

    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        ProductResults productResult;
        productResult = mainResultsRoot.getProductResults().get(position);
        holder.setView_position(position);

        holder.m_detail.setText(productResult.getPt());
        holder.product_detail_tv.setText(productResult.getPd());
        holder.product_mrp_tv.setText("Rs. "+productResult.getPmrp());

        if(UserProductUtil.isProductBought(context,productResult.getPc())){
            Log.i(LOG_TAG,"bought product details - product-id"+productResult.getPc()+","+"product-qunt"+productResult.getQnt());
            String qunt = String.valueOf(UserProductUtil.getProductQunt(context,productResult.getPc()));
            holder.getM_add_ll().setVisibility(View.GONE);
            holder.getM_incr_ll().setVisibility(View.VISIBLE);
            holder.getHld_qnt_tv().setText(qunt);
        }
        else{
            holder.getM_add_ll().setVisibility(View.VISIBLE);
            holder.getM_incr_ll().setVisibility(View.GONE);
        }
    }

    public ProductRListAdapter(Context context, MainResultsRoot mainResultsRoot, OnItemClickListener onItemClickListener){

        this.context = context;
        this.mainResultsRoot = mainResultsRoot;
        this.onItemClickListener = onItemClickListener;
    }



    @Override
    public int getItemCount() {
        return mainResultsRoot.getProductResults().size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /* public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }*/
}
