package com.newone.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newone.R;
import com.newone.activity.ProductListActivity;
import com.newone.adapter.ProductRListAdapter;
import com.newone.model.MainResultsRoot;
import com.newone.model.ProductResults;
import com.newone.util.AppConstants;
import com.newone.util.OnItemClickListener;
import com.newone.util.UserProductUtil;


public class ProductViewPagerFragment extends Fragment implements OnItemClickListener {

    private View rootView;
    private RecyclerView hpDetailList;
    private LinearLayoutManager mLayoutManager;
    private ProductRListAdapter adapter;
    private String type;
    private MainResultsRoot mainResultsRoot;
    private static int notifCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_list, container, false);

        hpDetailList = (RecyclerView) rootView.findViewById(R.id.hdlvp_detail_lv);

        notifCount = UserProductUtil.getLocalProductCount(this.getActivity());

        hpDetailList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        hpDetailList.setLayoutManager(mLayoutManager);
        adapter = new ProductRListAdapter(this.getActivity(), getMainResultsRoot(), this);
        hpDetailList.setAdapter(adapter);

        return rootView;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MainResultsRoot getMainResultsRoot() {
        return mainResultsRoot;
    }

    public void setMainResultsRoot(MainResultsRoot mainResultsRoot) {
        this.mainResultsRoot = mainResultsRoot;
    }

    @Override
    public void onItemClick(int id, View view, View view1, TextView textView, int position, RecyclerView.ViewHolder viewHolder) {

        ProductResults productResult;
        productResult = mainResultsRoot.getProductResults().get(position);
        String proCode = productResult.getPc();
        if (id == AppConstants.PRO_ADD) {
            productResult.setQnt(AppConstants.PRO_ADD);
            view.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(AppConstants.PRO_ADD));
            UserProductUtil.addProductToCart(this.getActivity(), productResult);
            ((ProductListActivity) getActivity()).setNotifCount(++notifCount);
            Snackbar.make(view, " Item added to your cart ", Snackbar.LENGTH_SHORT).show();
        } else if (id == AppConstants.PRO_INCR) {
            if (UserProductUtil.isProductBought(this.getActivity(), proCode)) {
                int qunt = UserProductUtil.getProductQunt(this.getActivity(), proCode);
                if (qunt >= AppConstants.MIN_ORDER_NUMBER && qunt < AppConstants.MAX_ORDER_NUMBER) {
                    textView.setText(String.valueOf(++qunt));
                    UserProductUtil.incrProductQunt(this.getActivity(), productResult.getPc());
                }
            }
        } else if (id == AppConstants.PRO_DECR) {
            Log.i("home_detail_list", "decr on click listener");
            if (UserProductUtil.isProductBought(this.getActivity(), proCode)) {
                int qunt = UserProductUtil.getProductQunt(this.getActivity(), proCode);
                if (qunt > AppConstants.MIN_ORDER_NUMBER && qunt <= AppConstants.MAX_ORDER_NUMBER) {
                    Log.i("home_detail_list", "decr product details - product-id" + productResult.getPc() + "," + "product-qunt" + qunt);
                    textView.setText(String.valueOf(--qunt));
                    UserProductUtil.decrProductQunt(this.getActivity(), productResult.getPc());
                } else if (qunt == AppConstants.MIN_ORDER_NUMBER) {
                    Log.i("home_detail_list", "removing product details - product-id" + productResult.getPc() + "," + "product-qunt" + qunt);
                    UserProductUtil.removeProductFromCart(this.getActivity(), productResult.getPc());
                    view.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.GONE);
                    ((ProductListActivity) getActivity()).setNotifCount(--notifCount);

                }
            }
        }

    }
}
