package com.newone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.newone.R;
import com.newone.adapter.ProductExploreRListAdapter;
import com.newone.model.MainResultsRoot;

import java.util.ArrayList;
import java.util.List;

public class ProductExploreViewPagerFragment extends Fragment {

    private View rootView;
    private ListView hdelListView;
    private ProductExploreRListAdapter productExploreRListAdapter;
    private ViewPager viewPager;
    private String type;
    private MainResultsRoot mainResultsRoot;
    private List<String> productList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_explore, container, false);

        hdelListView = (ListView) rootView.findViewById(R.id.hdelvp_lv);
        if(mainResultsRoot != null && mainResultsRoot.getProductType().size() >0) {
            productList = new ArrayList<>();
            int listSize = getMainResultsRoot().getProductType().size();
            productList = getMainResultsRoot().getProductType().subList(1,listSize);
            productExploreRListAdapter = new ProductExploreRListAdapter(this.getActivity(), productList);
            hdelListView.setAdapter(productExploreRListAdapter);


            hdelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    getViewPager().setCurrentItem(i+1);
                }
            });
        }
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

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
